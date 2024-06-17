package com.deveek.cilicili.web.common.media.model.dto;

import com.deveek.cilicili.web.common.media.constant.MediaResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey
 */
@Data
public class VideoGetDto implements Serializable {
    private Long id;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public VideoGetDto(Long id) {
        if (id == null) {
            throw new ClientException(MediaResult.VIDEO_ID_INVALID);
        }
        
        this.id = id;
    }
}
