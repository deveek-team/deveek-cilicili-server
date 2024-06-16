package com.deveek.cilicili.web.common.video.model.dto;

import cn.hutool.core.util.ObjUtil;
import com.deveek.common.constant.BaseResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey
 */
@Data
public class TopVideoPageDto implements Serializable {
    private final Integer pageNo;
    
    private final Integer pageSize;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public TopVideoPageDto(Integer pageNo, Integer pageSize) {
        if (pageNo <= 0) {
            throw new ClientException(BaseResult.PAGE_NO_INVALID);
        }
        if (pageSize <= 0) {
            throw new ClientException(BaseResult.PAGE_SIZE_INVALID);
        }
        
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
