package com.deveek.cilicili.web.common.video.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey
 */
@Data
public class TopVideoVo implements Serializable {
    private Long id;
    
    private String title;
    
    private String description;
    
    private String thumbnailUrl;
    
    private String videoUrl;
    
    private Long viewCount;
    
    private Long likeCount;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
