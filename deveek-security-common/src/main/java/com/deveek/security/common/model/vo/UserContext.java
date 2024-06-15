package com.deveek.security.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author harvey 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {
    private Long userId;
    
    private String username;
}
