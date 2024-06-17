package com.deveek.cilicili.web.common.media.model.po;

import com.deveek.cilicili.web.common.media.model.vo.VideoVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author harvey
 */
@Data
public class VideoPageVo implements Serializable {
    private List<VideoVo> videoVoList;
    
    private Long totalSize;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
