package com.deveek.cilicili.web.common.video.model.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.video.constant.VideoResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey
 */
@Data
public class ImageUploadDto implements Serializable {
    private MultipartFile file;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public ImageUploadDto(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ClientException(VideoResult.FiLE_IS_EMPTY);
        }
        
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            throw new ClientException(VideoResult.FILENAME_IS_BLANK);
        }
        
        String mimeType = file.getContentType();
        if (mimeType == null || !mimeType.startsWith("image")) {
            throw new ClientException(VideoResult.FILE_IS_NOT_IMAGE);
        }
        
        this.file = file;
    }
}
