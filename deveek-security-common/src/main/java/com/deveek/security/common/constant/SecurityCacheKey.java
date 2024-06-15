package com.deveek.security.common.constant;

import com.deveek.common.constant.CacheKey;

import java.util.concurrent.TimeUnit;

/**
 * @author harvey 
 */
public class SecurityCacheKey extends CacheKey {
    public static final CacheKey ACCESS_TOKEN = new CacheKey("security:token:access:%s", 60 * 24 * 7L, TimeUnit.MINUTES);
    
    public static final CacheKey REFRESH_TOKEN = new CacheKey("security:token:refresh:%s", null, null);
}