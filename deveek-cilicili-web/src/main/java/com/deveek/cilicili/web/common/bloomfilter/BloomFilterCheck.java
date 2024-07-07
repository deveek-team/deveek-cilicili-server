package com.deveek.cilicili.web.common.bloomfilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Shooter
 * 布隆过滤器查询注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BloomFilterCheck {

    /**
     * springEl 带查询参数
     */
    String value();

    /**
     * Result 枚举装饰
     */
    BloomResultWrapperEnum result();
}
