package com.deveek.cilicili.web.common.user.model.dto;

import cn.hutool.core.util.StrUtil;
import com.deveek.cilicili.web.common.user.constant.UserResult;
import com.deveek.common.exception.ClientException;
import com.deveek.common.support.Validator;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author vocmi
 */
@Data
public class UserSendEmailVerifyCodeDto implements Serializable {
    private String username;

    private String email;

    @Serial
    private static final long serialVersionUID = 1L;

    public UserSendEmailVerifyCodeDto(String username, String email) {
        if (StrUtil.isBlank(username)) {
            throw new ClientException(UserResult.USERNAME_INVALID);
        }
        if (Validator.isNotEmail(email)) {
            throw new ClientException(UserResult.EMAIL_INVALID);
        }

        this.username = username;
        this.email = email;
    }
}
