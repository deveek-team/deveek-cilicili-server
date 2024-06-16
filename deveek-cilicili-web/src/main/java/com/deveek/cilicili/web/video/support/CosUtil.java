package com.deveek.cilicili.web.video.support;


import cn.hutool.core.lang.UUID;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static com.deveek.cilicili.web.common.video.constant.CosConstant.*;

/**
 * @author Shooter
 */
@Data
@AllArgsConstructor
@Slf4j
public class CosUtil {

    private String secretId;
    private String secretKey;
    private String region;
    private String url;
    private String bucketName;
    private COSClient cosClient;

    /**
     * 上传本地文件至 COS
     */
    public void uploadLocalVideoFile(String localPath,String type) {
        try {
            File localFile = new File(localPath);
            // 指定要上传到 COS 上对象键
            String fileType = localPath.substring(localPath.lastIndexOf("."));
            String key = String.join("", UUID.randomUUID().toString(), fileType);
            String keyWithPrefix;
            if ("video".equals(type)){
                keyWithPrefix = UPLOAD_VIDEO_PATH + key;
            }else {
                keyWithPrefix = UPLOAD_IMAGE_PATH + key;
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyWithPrefix, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info("key: {},requestId: {},url: {}", key, putObjectResult.getRequestId(), url + keyWithPrefix);
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 流式上传文件至 COS
     */
    public void uploadFileStream(byte[] bytes, String originalFileName,String type) {
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
        String key = String.join("", UUID.randomUUID().toString(), fileType);
        String keyWithPrefix;
        if ("video".equals(type)){
            keyWithPrefix = UPLOAD_VIDEO_PATH + key;
        }else {
            keyWithPrefix = UPLOAD_IMAGE_PATH + key;
        }
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyWithPrefix, inputStream, objectMetadata);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info("key: {},requestId: {},url: {}", key, putObjectResult.getRequestId(), url + keyWithPrefix);
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 video url
     *
     * @param key 对象名称
     * @return video url
     */
    public String getVideoUrl(String key) {
        return cosClient.getObjectUrl(bucketName, UPLOAD_VIDEO_PATH + key).toString();
    }

    /**
     * 获取 image url
     *
     * @param key 对象名称
     * @return image url
     */
    public String getImageUrl(String key) {
        return cosClient.getObjectUrl(bucketName, UPLOAD_IMAGE_PATH + key).toString();
    }

    /**
     * 获取 video urls
     */
    public void listVideo() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(UPLOAD_VIDEO_PATH);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(1000);
        // 保存列出的结果
        ObjectListing objectListing = null;
        boolean isFirst = true;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosClientException e) {
                e.printStackTrace();
            }
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 跳过的是目录的名称
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                String key = cosObjectSummary.getKey();
                log.info("url: {}", url + "/" +  key);
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);

        } while (objectListing.isTruncated());
    }

    /**
     * 获取 image urls
     */
    public void listImage() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(UPLOAD_IMAGE_PATH);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(1000);
        // 保存列出的结果
        ObjectListing objectListing = null;
        boolean isFirst = true;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosClientException e) {
                e.printStackTrace();
            }
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 跳过的是目录的名称
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                String key = cosObjectSummary.getKey();
                log.info("url: {}", url + "/" +  key);
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);

        } while (objectListing.isTruncated());
    }
}
