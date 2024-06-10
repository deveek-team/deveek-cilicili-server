package com.deveek.cilicili.web.common.user.model.vo;

import lombok.Data;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-24
 */
@Data
public class LoginVo {
    Long userId;
    
    String username;
    
    String accessToken;
    
    String refreshToken;
}
