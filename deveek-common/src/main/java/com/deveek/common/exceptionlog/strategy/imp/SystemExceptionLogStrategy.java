package com.deveek.common.exceptionlog.strategy.imp;

import cn.hutool.core.util.ObjectUtil;
import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.exceptionlog.strategy.ExceptionDetails;
import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import com.deveek.common.exceptionlog.strategy.template.BaseExceptionLogStrategy;
import com.deveek.common.support.HttpContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author banne
 */
public class SystemExceptionLogStrategy extends BaseExceptionLogStrategy {
    @Override
    protected String formatLogMessage(String className, String requestIp, int requestPort, String requestUri, String requestType, Exception e) {
        ExceptionDetails exceptionDetails = handleExceptionInformation(e);

        Throwable exceptionCause = e.getCause();
        if (ObjectUtil.isNotEmpty(exceptionCause)) {
            ExceptionDetails causeExceptionDetails = handleExceptionInformation(exceptionCause);

            return String.format(LogFormatConstant.SYSTEM_EXCEPTION_LOG_EXIST_CAUSE_FORMAT, className, requestIp, requestPort, requestUri, requestType,
                    e.getClass(),
                    exceptionDetails.getMessage(), causeExceptionDetails.getMessage(),
                    exceptionDetails.getStackTrace(), causeExceptionDetails.getStackTrace(),
                    causeExceptionDetails.getSuppressed(), exceptionDetails.getSuppressed()
            );
        }

        return String.format(LogFormatConstant.SYSTEM_EXCEPTION_LOG_FORMAT, className, requestIp, requestPort, requestUri, requestType,
                e.getClass(), exceptionDetails.getMessage(), exceptionDetails.getStackTrace(), exceptionDetails.getSuppressed());
    }
}
