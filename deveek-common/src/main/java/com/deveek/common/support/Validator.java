package com.deveek.common.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.deveek.common.exception.ExceptionFactory;
import com.deveek.common.exception.ExceptionType;
import com.deveek.common.constant.Result;

import java.util.Collection;

/**
 * @author harvey 
 */
public class Validator {
    public static void check(boolean condition, ExceptionType exceptionType) {
        checkIsTrue(condition, exceptionType);
    }
    
    public static void check(boolean condition, ExceptionType exceptionType, Result result) {
        checkIsTrue(condition, exceptionType, result);
    }
    
    public static void checkIsTrue(boolean value, ExceptionType exceptionType) {
        checkIsTrue(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkIsTrue(boolean value, ExceptionType exceptionType, Result result) {
        if (value) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static void checkIsFalse(boolean value, ExceptionType exceptionType) {
        checkIsFalse(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkIsFalse(boolean value, ExceptionType exceptionType, Result result) {
        if (!value) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static void checkNotNull(Object value, ExceptionType exceptionType) {
        checkNotNull(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkNotNull(Object value, ExceptionType exceptionType, Result result) {
        if (ObjUtil.isNotNull(value)) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static void checkIsBlank(String value, ExceptionType exceptionType) {
        checkIsBlank(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkIsBlank(String value, ExceptionType exceptionType, Result result) {
        if (StrUtil.isBlank(value)) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static void checkIsNotBlank(String value, ExceptionType exceptionType) {
        checkIsNotBlank(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkIsNotBlank(String value, ExceptionType exceptionType, Result result) {
        if (StrUtil.isNotBlank(value)) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static void checkIsEmpty(Collection value, ExceptionType exceptionType) {
        checkIsEmpty(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkIsEmpty(Collection value, ExceptionType exceptionType, Result result) {
        if (CollUtil.isEmpty(value)) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static void checkIsNotEmpty(Collection value, ExceptionType exceptionType) {
        checkIsNotEmpty(value, exceptionType, Result.FAILURE);
    }
    
    public static void checkIsNotEmpty(Collection value, ExceptionType exceptionType, Result result) {
        if (CollUtil.isNotEmpty(value)) {
            return;
        }
        
        throw ExceptionFactory.getException(exceptionType, result);
    }
    
    public static boolean isEmail(String mail) {
        return cn.hutool.core.lang.Validator.isEmail(mail);
    }
    
    public static boolean isNotEmail(String mail) {
        return !isEmail(mail);
    }
}
