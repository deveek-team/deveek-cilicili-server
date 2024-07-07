package com.deveek.common.exceptionlog.strategy.imp;

import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.exception.BaseException;
import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import com.deveek.common.exceptionlog.support.ExceptionLogUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

/**
 * @author banne
 */
public class CiliCiliExceptionLogStrategy implements ExceptionLogStrategy {
    @Override
    public void logException(Exception e) {
        // 获取调用者的日志器
        Logger logger = ExceptionLogUtil.getCallerLogger();
        HttpServletRequest request = ExceptionLogUtil.getRequest();

        BaseException customException = (BaseException) e;

        String className = customException.getClass().getSimpleName().toLowerCase().replaceAll("(?i)(?=exception)", " ");

        String requestIp = request.getRemoteAddr();
        int requestPort = request.getServerPort();
        String requestUri = request.getRequestURI();
        String requestType = request.getMethod();


        String logMessage = String.format(LogFormatConstant.CILICILI_CUSTOM_EXCEPTION_LOG_FORMAT, className, requestIp, requestPort, requestUri, requestType,
                customException.getClass(), customException.getCode(), customException.getMessage());

        logger.error("{}", logMessage);
    }
}
