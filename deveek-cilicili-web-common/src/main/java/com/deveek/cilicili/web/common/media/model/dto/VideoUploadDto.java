package com.deveek.cilicili.web.common.media.model.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.media.constant.MediaResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey
 */
@Data
public class VideoUploadDto implements Serializable {
    private MultipartFile file;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public VideoUploadDto(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ClientException(MediaResult.FiLE_IS_EMPTY);
        }
        
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            throw new ClientException(MediaResult.FILENAME_IS_BLANK);
        }
        
        String mimeType = file.getContentType();
        if (mimeType == null || !mimeType.startsWith("video")) {
            throw new ClientException(MediaResult.FILE_IS_NOT_VIDEO);
        }
        
        this.file = file;
    }
}
