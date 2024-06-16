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
public class RegisterDto implements Serializable {
    private final String username;
    
    private final String password;
    
    private final String email;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public RegisterDto(String username, String password, String email) {
        if (StrUtil.isBlank(username)) {
            throw new ClientException(SecurityResult.USERNAME_INVALID);
        }
        if (StrUtil.isBlank(password)) {
            throw new ClientException(SecurityResult.PASSWORD_INVALID);
        }
        if (StrUtil.isBlank(email)) {
            throw new ClientException(SecurityResult.EMAIL_INVALID);
        }
        
        this.username = username;
        this.password = password;
        this.email = email;
    }
}