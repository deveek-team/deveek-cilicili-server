package com.deveek.cilicili.web.user.entity.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.user.UserResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-31
 */
@Data
@NoArgsConstructor
public class UserLoginDto implements Serializable {
    private String username;
    
    private String password;
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    public UserLoginDto(String username, String password) {
        if (StrUtil.isBlank(username)) {
            throw new ClientException(UserResult.USERNAME_INVALID);
        }
        if (StrUtil.isBlank(password)) {
            throw new ClientException(UserResult.PASSWORD_INVALID);
        }
        
        this.username = username;
        this.password = password;
    }
}
