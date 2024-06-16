package com.deveek.cilicili.web.media.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author harvey
 */
public interface CosService {
    String uploadFile(MultipartFile file, String dir);

    String genFileUrl(String fileName);
}
