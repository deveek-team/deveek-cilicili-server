package com.deveek.cilicili.web.common.user.model.vo;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey 
 */
@Data
public class UserVo implements Serializable {
    private String username;
    
    private String email;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public void mask() {
        maskEmail();
    }
    
    public void maskEmail() {
        String[] splits = email.split("@");
        if (splits.length != 2) {
            return;
        }
        
        String s0 = splits[0];
        String s1 = splits[1];
        if (StrUtil.isBlank(s0) || StrUtil.isBlank(s1)) {
            return;
        }
        
        email = s0.charAt(0) + "*****" + "@" + s1;
    }
}