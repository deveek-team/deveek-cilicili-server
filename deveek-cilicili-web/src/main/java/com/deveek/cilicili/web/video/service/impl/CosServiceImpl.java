package com.deveek.cilicili.web.video.service.impl;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.video.constant.CosProperties;
import com.deveek.cilicili.web.common.video.constant.VideoResult;
import com.deveek.cilicili.web.video.service.CosService;
import com.deveek.common.exception.ClientException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author harvey
 */
@Service
public class CosServiceImpl implements CosService {
    @Resource
    private COSClient cosClient;
    
    @Resource
    private CosProperties cosProperties;
    
    @Override
    public String uploadFile(MultipartFile file, String dirname) {
        if (file.isEmpty()) {
            throw new ClientException(VideoResult.FiLE_IS_EMPTY);
        }
        
        if (StrUtil.isBlank(dirname)) {
            throw new ClientException(VideoResult.DIRNAME_IS_BLANK);
        }
        
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            throw new ClientException(VideoResult.FILENAME_IS_BLANK);
        }
        
        String baseFileName = getBaseFileName(originalFilename);
        String fileExtension = getFileExtension(originalFilename);
        UUID uuidSuffix = UUID.randomUUID();
        
        String fileName = String.format("/%s/%s_%s.%s", dirname, baseFileName, uuidSuffix, fileExtension);
        
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
        
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), fileName, localFile);
        
        PutObjectResult putObjectResult;
        try {
            putObjectResult = cosClient.putObject(putObjectRequest);
        } catch (Exception e) {
            localFile.delete();
            throw new ClientException(VideoResult.UPLOAD_FAILURE);
        }
        
        String eTag = putObjectResult.getETag();
        localFile.delete();
        if (eTag == null || eTag.isEmpty()) {
            throw new ClientException(VideoResult.UPLOAD_FAILURE);
        }
        
        return genFileUrl(fileName);
    }
    
    @Override
    public String genFileUrl(String fileName) {
        return String.format("https://%s.cos.%s.myqcloud.com/%s", cosProperties.getBucketName(), cosProperties.getRegion(), fileName);
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
