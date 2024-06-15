package com.deveek.log.anno;

import com.deveek.log.constant.ApiLogConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author harvey 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {
    String message() default ApiLogConstant.DEFAULT_MESSAGE;
}
