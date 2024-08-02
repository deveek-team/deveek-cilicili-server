package com.deveek.cilicili.web.media.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.media.constant.CosProperties;
import com.deveek.cilicili.web.common.media.constant.MediaRedisConstant;
import com.deveek.cilicili.web.common.media.constant.MediaResult;
import com.deveek.cilicili.web.media.service.CosService;
import com.deveek.common.exception.ClientException;
import com.deveek.security.common.service.UserContextHolder;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.deveek.cilicili.web.common.media.constant.MediaRedisConstant.FILE_BLOCKING_DEQUE;

/**
 * @author harvey
 */
@Service
@Slf4j
public class CosServiceImpl implements CosService {
    @Resource
    private COSClient cosClient;

    @Resource
    private CosProperties cosProperties;

    @Resource
    private UserContextHolder userContextHolder;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String uploadFile(MultipartFile file, String dirname) {
        if (file.isEmpty()) {
            throw new ClientException(MediaResult.FiLE_IS_EMPTY);
        }

        if (StrUtil.isBlank(dirname)) {
            throw new ClientException(MediaResult.DIRNAME_IS_BLANK);
        }

        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            throw new ClientException(MediaResult.FILENAME_IS_BLANK);
        }
        
        String baseFileName = getBaseFileName(originalFilename);
        String fileExtension = getFileExtension(originalFilename);
        UUID uuidSuffix = UUID.randomUUID();

        String fileUri = String.format("/%s/%s_%s.%s", dirname, baseFileName, uuidSuffix, fileExtension);

        File localFile;
        try {
            localFile = File.createTempFile("temp", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), fileUri, localFile);

        PutObjectResult putObjectResult;
        try {
            putObjectResult = cosClient.putObject(putObjectRequest);
        } catch (Exception e) {
            localFile.delete();
            throw new ClientException(MediaResult.UPLOAD_FAILURE);
        }

        String eTag = putObjectResult.getETag();
        localFile.delete();
        if (eTag == null || eTag.isEmpty()) {
            throw new ClientException(MediaResult.UPLOAD_FAILURE);
        }

        return getFileUrl(fileUri);
    }

    @Override
    public String getFileUrl(String fileUri) {
        return String.format("https://%s.cos.%s.myqcloud.com/%s", cosProperties.getBucketName(), cosProperties.getRegion(), fileUri);
    }

    @Override
    @SneakyThrows
    public void continueUploadVideo(MultipartFile file, String dirname) {
        if (file.isEmpty()) {
            throw new ClientException(MediaResult.FiLE_IS_EMPTY);
        }

        if (StrUtil.isBlank(dirname)) {
            throw new ClientException(MediaResult.DIRNAME_IS_BLANK);
        }

        String originalFilename = file.getOriginalFilename(); // 1.mp4
        if (StrUtil.isBlank(originalFilename)) {
            throw new ClientException(MediaResult.FILENAME_IS_BLANK);
        }

        String key = getKey(originalFilename);

        String uploadId = null;

        // key 作为分块上传以及合并的唯一标识，需要保证唯一性。uploadId 也要关联起来，即使 key 不变，执行初始化 initiateMultipartUpload 依然会变
        String mediaUserCacheKey = String.format(MediaRedisConstant.MEDIA_USER_KEY,
                userContextHolder.getUserContext().getUserId(),
                originalFilename);
        if (BooleanUtil.isFalse(stringRedisTemplate.hasKey(mediaUserCacheKey))) {
            uploadId = initiateMultipartUpload(key);
            Map<String, String> map = new HashMap<>();
            map.put("key", key);
            map.put("uploadId", uploadId);
            stringRedisTemplate.opsForHash().putAll(mediaUserCacheKey, map); // 说明用户第一次来传。
        } else {
            key = (String) stringRedisTemplate.opsForHash().get(mediaUserCacheKey, "key"); // 说明用户中断了一次，又来从中间传了。
            uploadId = (String) stringRedisTemplate.opsForHash().get(mediaUserCacheKey, "uploadId");
        }

        if (StrUtil.isEmpty(uploadId)) {
            throw new ClientException(MediaResult.INITIATE_FAILURE);
        }

        File localFile = File.createTempFile("temp", null);
        file.transferTo(localFile);
        long totalSize = localFile.length();
        byte data[] = new byte[10 * 1024 * 1024]; // 每个分块 10 MB
        int batchSize = data.length;

        long batch = totalSize / batchSize + (totalSize % batchSize > 0 ? 1 : 0);

        List<String> part = splitBySize(localFile, batchSize);
        Thread.sleep(2000);

        // 查询已上传的分块，实现断点续传
        PartListing partListing = null;
        try {
            ListPartsRequest listPartsRequest = new ListPartsRequest(cosProperties.getBucketName(), key, uploadId);
            partListing = cosClient.listParts(listPartsRequest);
        } catch (Throwable e) {
            throw new ClientException(MediaResult.LISTPARTS_FAILURE);
        }

        List<PartETag> partETags = new ArrayList<>();
        List<Integer> completePiece = new ArrayList<>();
        for (PartSummary partSummary : partListing.getParts()) {
            completePiece.add(partSummary.getPartNumber());
            partETags.add(new PartETag(partSummary.getPartNumber(), partSummary.getETag()));
        }

        for (int i = 1; i <= batch; i++) {
            if (!completePiece.contains(i)) {
                log.info("一共 {} 块,正在进行第 {} 块", batch, i);

                long partSize = batchSize;
                if (i == batch) { // 如果是最后一个分块，则重新计算分块大小，防止合并出错
                    partSize = totalSize - (i - 1) * batchSize;
                    PartETag partETag = batchUpload(uploadId, part.get(i - 1), partSize, i, key, true);
                    partETags.add(partETag);
                } else {
                    PartETag partETag = batchUpload(uploadId, part.get(i - 1), partSize, i, key, false);
                    partETags.add(partETag);
                }
            }
        }

        // 合并
        try {
            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    new CompleteMultipartUploadRequest(cosProperties.getBucketName(), key, uploadId, partETags);
            cosClient.completeMultipartUpload(completeMultipartUploadRequest);
        } catch (Throwable e) {
            throw new ClientException(MediaResult.COMPLETE_FAILURE);
        }

        stringRedisTemplate.delete(mediaUserCacheKey);
    }

    private PartETag batchUpload(String uploadId, String path, Long partSize, Integer partNumber, String key, Boolean isLastPart) {
        File file = null;
        try {
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(cosProperties.getBucketName());
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(uploadId);

            file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            uploadPartRequest.setInputStream(fileInputStream);

            // 设置分块的长度
            uploadPartRequest.setPartSize(file.length());
            uploadPartRequest.setPartNumber(partNumber);
            uploadPartRequest.setLastPart(isLastPart);

            UploadPartResult uploadPartResult = cosClient.uploadPart(uploadPartRequest);
            PartETag partETag = uploadPartResult.getPartETag();

            fileInputStream.close(); // 这里一定要把流关闭，否则删除文件会失败
            file.delete();
            return partETag;
        } catch (Throwable e) {
            file.delete();
            stringRedisTemplate.delete(MediaRedisConstant.MEDIA_USER_KEY); // 上传视频失败，取消断点传送
            throw new ClientException(MediaResult.UPLOAD_FAILURE);
        }
    }

    private List<String> splitBySize(File localFile, int byteSize)
            throws IOException {
        List<String> parts = new ArrayList<String>();
        int count = (int) Math.ceil(localFile.length() / (double) byteSize);
        int countLen = (count + "").length();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                count,
                count * 3,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = localFile.getName() + "." + leftPad((i + 1) + "", countLen, '0') + ".part";
            threadPool.execute(new SplitRunnable(byteSize, i * byteSize, partFileName, localFile));
            parts.add(partFileName);
        }

        localFile.delete();
        return parts;
    }

    private class SplitRunnable implements Runnable {
        int byteSize;
        String partFileName;
        File originFile;
        int startPos;

        public SplitRunnable(int byteSize, int startPos, String partFileName, File originFile) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
        }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                byte[] b = new byte[byteSize];
                rFile.seek(startPos);
                int s = rFile.read(b);
                os = new FileOutputStream(partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();

                // 确保文件一定删除，防止因为宕机或者程序报错没有执行删除文件
                RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(FILE_BLOCKING_DEQUE);
                RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                delayedQueue.offer(partFileName, 3, TimeUnit.MINUTES);
            } catch (IOException ignored) {
            }
        }
    }

    private String initiateMultipartUpload(String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(cosProperties.getBucketName(), key);
        InitiateMultipartUploadResult initResult = cosClient.initiateMultipartUpload(request);
        return initResult.getUploadId();
    }

    private String getKey(String originalFilename) {
        String baseFileName = getBaseFileName(originalFilename);
        String fileExtension = getFileExtension(originalFilename);
        UUID uuidSuffix = UUID.randomUUID();
        return String.format("/%s/%s_%s.%s", "video", baseFileName, uuidSuffix, fileExtension);
    }

    public static String leftPad(String input, int totalWidth, char paddingChar) {
        int paddingWidth = totalWidth - input.length();
        if (paddingWidth <= 0) {
            return input;
        }

        StringBuilder sb = new StringBuilder(totalWidth);
        for (int i = 0; i < paddingWidth; i++) {
            sb.append(paddingChar);
        }
        sb.append(input);

        return sb.toString();
    }

    @Component
    class FileDeleteDelayQueueRunner implements CommandLineRunner {

        @Resource
        private RedissonClient redissonClient;
        private static final Logger LOG = LoggerFactory.getLogger(FileDeleteDelayQueueRunner.class);

        @Override
        public void run(String... args) {
            Executors.newSingleThreadExecutor(
                            runnable -> {
                                Thread thread = new Thread(runnable);
                                thread.setName("delay_file-delete_consumer");
                                thread.setDaemon(Boolean.TRUE);
                                return thread;
                            })
                    .execute(() -> {
                        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(FILE_BLOCKING_DEQUE);
                        for (; ; ) {
                            try {
                                String fileName = blockingDeque.take();
                                LOG.info("检查文件：{} 是否被删除", fileName);
                                File file = new File(fileName);
                                if (file.delete()) {
                                    LOG.info("文件：{} 删除成功", fileName);
                                }
                            } catch (Throwable ignored) { // 说明文件已经被删除了
                            }
                        }
                    });
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    private String getBaseFileName(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? filename : filename.substring(0, dotIndex);
    }
}
