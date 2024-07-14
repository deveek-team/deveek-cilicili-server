package com.deveek.common.exceptionlog.strategy.imp;

import cn.hutool.core.util.ObjectUtil;
import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.exception.BaseException;
import com.deveek.common.exceptionlog.strategy.ExceptionDetails;
import com.deveek.common.exceptionlog.strategy.template.BaseExceptionLogStrategy;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author banne
 */
public class CiliCiliExceptionLogStrategy extends BaseExceptionLogStrategy {

    @Override
    protected String formatLogMessage(String className, String requestIp, int requestPort, String requestUri, String requestType, Exception e) {
        BaseException customException = (BaseException) e;

        int exceptionCode = customException.getCode();
        ExceptionDetails exceptionDetails = handleExceptionInformation(customException);

        Throwable exceptionCause = customException.getCause();
        if (ObjectUtil.isNotEmpty(exceptionCause)) {
            ExceptionDetails causeExceptionDetails = handleExceptionInformation(exceptionCause);

            return String.format(LogFormatConstant.CILICILI_CUSTOM_EXCEPTION_LOG_EXIST_CAUSE_FORMAT, className, requestIp, requestPort, requestUri, requestType,
                    customException.getClass(), exceptionCode,
                    exceptionDetails.getMessage(), causeExceptionDetails.getMessage(),
                    exceptionDetails.getStackTrace(), causeExceptionDetails.getStackTrace(),
                    causeExceptionDetails.getSuppressed(), exceptionDetails.getSuppressed()
            );
        }

        return String.format(LogFormatConstant.CILICILI_CUSTOM_EXCEPTION_LOG_FORMAT, className, requestIp, requestPort, requestUri, requestType,
                customException.getClass(), exceptionCode,
                exceptionDetails.getMessage(), exceptionDetails.getStackTrace(), exceptionDetails.getSuppressed()
        );
    }
}
