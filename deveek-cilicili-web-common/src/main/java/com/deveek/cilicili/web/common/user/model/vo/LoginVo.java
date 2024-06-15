package com.deveek.cilicili.web.common.user.model.vo;

import lombok.Data;

/**
 * @author harvey 
 */
@Data
public class LoginVo {
    Long userId;
    
    String username;
    
    String accessToken;
    
    String refreshToken;
}
