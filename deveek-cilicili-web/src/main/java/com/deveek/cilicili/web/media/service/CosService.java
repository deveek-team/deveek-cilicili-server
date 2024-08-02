package com.deveek.cilicili.web.media.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author harvey
 */
public interface CosService {
    String uploadFile(MultipartFile file, String dir);

    String getFileUrl(String fileName);

    /**
     * 断点续传视频
     */
    void continueUploadVideo(MultipartFile file, String video);
}
