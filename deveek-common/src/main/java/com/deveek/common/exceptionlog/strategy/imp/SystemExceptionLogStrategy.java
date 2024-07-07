package com.deveek.common.exceptionlog.strategy.imp;

import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import com.deveek.common.exceptionlog.support.ExceptionLogUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

/**
 * @author banne
 */
public class SystemExceptionLogStrategy implements ExceptionLogStrategy {
    @Override
    public void logException(Exception e) {
        Logger logger = ExceptionLogUtil.getCallerLogger();
        HttpServletRequest request = ExceptionLogUtil.getRequest();

        String className = e.getClass().getSimpleName().toLowerCase().replaceAll("(?i)(?=exception)", " ");

        String requestIp = request.getRemoteAddr();
        int requestPort = request.getServerPort();
        String requestUri = request.getRequestURI();
        String requestType = request.getMethod();

        String logMessage = String.format(LogFormatConstant.SYSTEM_EXCEPTION_LOG_FORMAT, className, requestIp, requestPort, requestUri, requestType,
                e.getClass(), e.getMessage());

        logger.error("{}", logMessage);
    }


}
