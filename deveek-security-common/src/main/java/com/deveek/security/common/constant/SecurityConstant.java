package com.deveek.security.common.constant;

import cn.hutool.core.date.DateField;

/**
 * @author harvey 
 */
public class SecurityConstant {
    public static final Long ADMIN_ID = 1L;
    
    public static final int ACCESS_TOKEN_TIMEOUT = 1;
    
    public static final DateField ACCESS_TOKEN_TIMEOUT_UNIT = DateField.MONTH;
    
    public static final byte[] ACCESS_TOKEN_KEY = "access_token_key".getBytes();
    
    public static final byte[] REFRESH_TOKEN_KEY = "refresh_token_key".getBytes();
    
    public static final String USER_ID = "userId";
    
    public static final String USERNAME = "username";
    
    public static final String PASSWORD = "password";
    
    public static final String AUTHORITIES = "authorities";
    
    public static final Byte ACCOUNT_NOT_EXPIRED = 0;
    
    public static final Byte ACCOUNT_EXPIRED = 1;
    
    public static final Byte CREDENTIALS_NOT_EXPIRED = 0;
    
    public static final Byte CREDENTIALS_EXPIRED = 1;
    
    public static final Byte ACCOUNT_NOT_LOCKED = 0;
    
    public static final Byte ACCOUNT_LOCKED = 1;
}