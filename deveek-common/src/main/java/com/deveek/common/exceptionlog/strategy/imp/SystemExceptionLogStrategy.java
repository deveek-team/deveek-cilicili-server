package com.deveek.common.exceptionlog.strategy.imp;

import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.exceptionlog.strategy.ExceptionDetails;
import com.deveek.common.exceptionlog.strategy.template.BaseExceptionLogStrategy;

/**
 * @author banne
 */
public class SystemExceptionLogStrategy extends BaseExceptionLogStrategy {
    @Override
    protected String formatLogMessage(String className, String requestIp, int requestPort, String requestUri, String requestType, Exception e) {
        ExceptionDetails exceptionDetails = getExceptionDetails(e);
        
        ExceptionDetails causeExceptionDetails = getExceptionDetails(e.getCause());
        
        return String.format(
            LogFormatConstant.SYSTEM_EXCEPTION_LOG,
            className,
            requestIp,
            requestPort,
            requestUri,
            requestType,
            e.getClass(),
            exceptionDetails.getMessage(),
            causeExceptionDetails.getMessage(),
            exceptionDetails.getStackTrace(),
            causeExceptionDetails.getStackTrace(),
            causeExceptionDetails.getSuppressed(),
            exceptionDetails.getSuppressed()
        );
    }
}
