package com.deveek.cilicili.web.user.support;

import com.deveek.common.constant.Result;
import com.deveek.common.support.ResponseUtil;
import com.deveek.common.support.ExceptionLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author harvey 
 */
@Component
public class GlobalAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ExceptionLogger.error(exception, request);
        ResponseUtil.write(response, Result.FAILURE);
    }
}
