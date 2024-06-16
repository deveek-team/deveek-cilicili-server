package com.deveek.cilicili.web.common.media.model.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.media.constant.MediaResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey
 */
@Data
public class VideoAddDto implements Serializable {
    private String title;
    
    private String description;
    
    private String thumbnailUrl;
    
    private String videoUrl;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public VideoAddDto(String title, String description, String thumbnailUrl, String videoUrl) {
        if (StrUtil.isBlank(title)) {
            throw new ClientException(MediaResult.TITLE_INVALID);
        }
        if (StrUtil.isBlank(thumbnailUrl)) {
            throw new ClientException(MediaResult.THUMBNAIL_URL_INVALID);
        }
        if (StrUtil.isBlank(videoUrl)) {
            throw new ClientException(MediaResult.VIDEO_URL_INVALID);
        }
        
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
    }
}
