package com.deveek.cilicili.web.common.user;

import com.deveek.common.constant.CacheKey;

import java.util.concurrent.TimeUnit;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-24
 */
public class UserCacheKey extends CacheKey {
    public static final CacheKey ACCESS_TOKEN = new CacheKey("user:token:access:%s", 60 * 24 * 7L, TimeUnit.MINUTES);
    
    public static final CacheKey REFRESH_TOKEN = new CacheKey("user:token:refresh:%s", null, null);
    
    public static final CacheKey LIST = new CacheKey("user:list", 60 * 24 * 7L, TimeUnit.MINUTES);
    
    public static final CacheKey PAGE = new CacheKey("user:page:%s:%s", 60 * 24 * 7L, TimeUnit.MINUTES);
}