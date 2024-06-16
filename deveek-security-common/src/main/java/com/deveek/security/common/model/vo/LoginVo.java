package com.deveek.security.common.model.vo;

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
