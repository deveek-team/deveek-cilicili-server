package com.deveek.cilicili.web.user.support;

import com.deveek.common.constant.Result;
import com.deveek.common.support.ResponseUtil;
import com.deveek.common.support.ExceptionLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author harvey 
 */
@Component
public class GlobalAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Triggers when an authenticated user attempts to access a resource they are not authorized for.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionLogger.error(accessDeniedException, request);
        ResponseUtil.write(response, Result.FORBIDDEN);
    }
}
