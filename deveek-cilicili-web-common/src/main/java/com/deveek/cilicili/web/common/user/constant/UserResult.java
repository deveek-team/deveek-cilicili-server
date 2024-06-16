package com.deveek.cilicili.web.common.user.constant;

import com.deveek.common.constant.Result;

/**
 * @author harvey
 */
public class UserResult {
    public static final Result USER_NOT_FOUND = new Result(2201, "user not found");
    
    public static final Result USERNAME_INVALID = new Result(2202, "username is invalid");
    
    public static final Result PASSWORD_INVALID = new Result(2203, "password is invalid");
    
    public static final Result EMAIL_INVALID = new Result(2204, "email is invalid");

    public static final Result CODE_INVALID = new Result(2205, "code is invalid");
    public static final Result USER_EXISTS = new Result(2206, "user already exist");
}
