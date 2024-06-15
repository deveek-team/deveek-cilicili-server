package com.deveek.security.common.constant;

import com.deveek.common.constant.HttpUri;

/**
 * @author harvey 
 */
public class SecurityHttpUri extends HttpUri {
    public static final String LOGIN = "/api/v1/security/login";
    
    public static final String LOGOUT = "/api/v1/security/logout";
    
    public static final String REFRESH = "/api/v1/security/login/refresh";
    
    public static final String REGISTER = "/api/v1/security/register";
}