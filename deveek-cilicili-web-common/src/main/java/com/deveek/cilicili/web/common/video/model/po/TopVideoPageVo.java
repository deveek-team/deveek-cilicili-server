package com.deveek.cilicili.web.common.video.model.po;

import com.deveek.cilicili.web.common.video.model.vo.TopVideoVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author harvey
 */
@Data
public class TopVideoPageVo implements Serializable {
    private List<TopVideoVo> topVideoVoList;
    
    private Long totalSize;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
