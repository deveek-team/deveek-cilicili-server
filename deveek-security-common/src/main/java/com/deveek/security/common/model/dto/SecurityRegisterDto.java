package com.deveek.security.common.model.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.common.exception.ClientException;
import com.deveek.security.common.constant.SecurityResult;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey 
 */
@Data
public class SecurityRegisterDto implements Serializable {
    private String username;
    
    private String password;
    
    private String email;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public void setUsername(String username) {
        if (StrUtil.isBlank(username)) {
            throw new ClientException(SecurityResult.USERNAME_INVALID);
        }
        this.username = username;
    }
    
    public void setPassword(String password) {
        if (StrUtil.isBlank(password)) {
            throw new ClientException(SecurityResult.PASSWORD_INVALID);
        }
        this.password = password;
    }
    
    public void setEmail(String email) {
        if (StrUtil.isBlank(email)) {
            throw new ClientException(SecurityResult.EMAIL_INVALID);
        }
        this.email = email;
    }
}