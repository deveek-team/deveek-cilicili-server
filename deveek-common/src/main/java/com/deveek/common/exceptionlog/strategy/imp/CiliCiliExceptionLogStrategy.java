package com.deveek.common.exceptionlog.strategy.imp;

import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.exception.BaseException;
import com.deveek.common.exceptionlog.strategy.ExceptionDetails;
import com.deveek.common.exceptionlog.strategy.template.BaseExceptionLogStrategy;

/**
 * @author banne
 */
public class CiliCiliExceptionLogStrategy extends BaseExceptionLogStrategy {
    @Override
    protected String formatLogMessage(String className, String requestIp, int requestPort, String requestUri, String requestType, Exception e) {
        BaseException customException = (BaseException) e;
        
        int exceptionCode = customException.getCode();
        ExceptionDetails exceptionDetails = getExceptionDetails(customException);
        
        ExceptionDetails causeExceptionDetails = getExceptionDetails(customException.getCause());
        
        return String.format(
            LogFormatConstant.CILICILI_CUSTOM_EXCEPTION_LOG,
            className,
            requestIp,
            requestPort,
            requestUri,
            requestType,
            customException.getClass(),
            exceptionCode,
            exceptionDetails.getMessage(),
            causeExceptionDetails.getMessage(),
            exceptionDetails.getStackTrace(),
            causeExceptionDetails.getStackTrace(),
            causeExceptionDetails.getSuppressed(),
            exceptionDetails.getSuppressed()
        );
    }
}
