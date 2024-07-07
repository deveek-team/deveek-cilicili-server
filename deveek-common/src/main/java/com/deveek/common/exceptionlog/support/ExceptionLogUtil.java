package com.deveek.common.exceptionlog.support;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author banne
 */

public class ExceptionLogUtil {
    private static String getClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTraceElements[6];

        return caller.getClassName();
    }

    public static Logger getCallerLogger() {
        String className = getClassName();

        Class<?> callerClass;
        try {
            callerClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }

        Logger logger = LoggerFactory.getLogger(callerClass);

        return logger;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        return request;
    }
}
