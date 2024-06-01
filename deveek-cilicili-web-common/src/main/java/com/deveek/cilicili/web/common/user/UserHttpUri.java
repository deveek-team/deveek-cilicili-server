package com.deveek.cilicili.web.common.user;

import com.deveek.common.constant.HttpUri;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-24
 */
public class UserHttpUri extends HttpUri {
    public static final String LOGIN = "/api/user/v1/login";
    
    public static final String LOGOUT = "/api/user/v1/logout";
    
    public static final String REFRESH = "/api/user/v1/login/refresh";
    
    public static final String REGISTER = "/api/user/v1/register";
}