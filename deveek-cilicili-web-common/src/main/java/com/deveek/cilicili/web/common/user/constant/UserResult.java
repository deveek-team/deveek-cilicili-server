package com.deveek.cilicili.web.common.user.constant;

import com.deveek.common.constant.Result;

/**
 * @author harvey 
 */
public class UserResult extends Result {
    public static final Result LOGIN_FAILURE = new Result(1001, "login failure");
    
    public static final Result LOGIN_TOKEN_EXPIRED = new Result(1002, "login token expired");
    
    public static final Result LOGOUT_FAILURE = new Result(1101, "logout failure");
    
    public static final Result USER_NOT_FOUND = new Result(1201, "user not found");
    
    public static final Result USERNAME_INVALID = new Result(1202, "username is invalid");
    
    public static final Result PASSWORD_INVALID = new Result(1203, "password is invalid");
    
    public static final Result EMAIL_INVALID = new Result(1204, "username is invalid");
    
    public static final Result USER_EXISTS = new Result(1205, "user already exist");
}