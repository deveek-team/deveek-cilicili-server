package com.deveek.cilicili.web.common.user.model.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.user.constant.UserResult;
import com.deveek.common.exception.ClientException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author vocmi
 */
@Data
@NoArgsConstructor
public class UserRegisterDto implements Serializable {
    private String username;

    private String password;

    private String email;

    private String verifyCode;

    @Serial
    private static final long serialVersionUID = 1L;

    public UserRegisterDto(String username, String password, String email, String verifyCode) {
        if (StrUtil.isBlank(username)) {
            throw new ClientException(UserResult.USERNAME_INVALID);
        }
        if (StrUtil.isBlank(password)) {
            throw new ClientException(UserResult.PASSWORD_INVALID);
        }
        if (StrUtil.isBlank(email)) {
            throw new ClientException(UserResult.EMAIL_INVALID);
        }
        if (StrUtil.isBlank(verifyCode)) {
            throw new ClientException(UserResult.CODE_INVALID);
        }

        this.username = username;
        this.password = password;
        this.email = email;
        this.verifyCode = verifyCode;
    }
}