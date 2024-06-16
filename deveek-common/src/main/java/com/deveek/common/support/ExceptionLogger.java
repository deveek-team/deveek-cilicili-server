package com.deveek.common.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author harvey 
 */
@Slf4j
public class ExceptionLogger {
    public static void error(Exception e, HttpServletRequest req) {
        log.error("RuntimeException, Request Url: {}, Request Method: {}, Exception Class: {}, Exception Message: {}", req.getRequestURL(), req.getMethod(), e.getClass(), e.getMessage());
    }
}
