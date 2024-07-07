package com.deveek.cilicili.web.common.user.constant;

import com.deveek.common.constant.CacheKey;

import java.util.concurrent.TimeUnit;

/**
 * @author harvey
 */
public class UserCacheKey extends CacheKey {
    public static final CacheKey LIST = new CacheKey("user:list", 60 * 24 * 7L, TimeUnit.MINUTES);
    
    public static final CacheKey PAGE = new CacheKey("user:page:%s:%s", 60 * 24 * 7L, TimeUnit.MINUTES);

    public static final CacheKey EMAIL_CODE = new CacheKey("user:email:%s:%s", 5L, TimeUnit.MINUTES);
}
