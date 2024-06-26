package com.deveek.cilicili.web.media.service.impl;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.media.constant.CosProperties;
import com.deveek.cilicili.web.common.media.constant.MediaResult;
import com.deveek.cilicili.web.media.service.CosService;
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
    
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
    
    private String getBaseFileName(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? filename : filename.substring(0, dotIndex);
    }
}
