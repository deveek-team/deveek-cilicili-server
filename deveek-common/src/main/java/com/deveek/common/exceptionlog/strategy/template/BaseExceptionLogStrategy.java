package com.deveek.common.exceptionlog.strategy.template;

import com.deveek.common.exceptionlog.strategy.ExceptionDetails;
import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import com.deveek.common.exceptionlog.support.ExceptionLogStrategyContext;
import com.deveek.common.support.HttpContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author banne
 */

public abstract class BaseExceptionLogStrategy implements ExceptionLogStrategy {
    @Override
    public void logException(ExceptionLogStrategyContext exceptionLogStrategyContext) {
        Logger logger = exceptionLogStrategyContext.getLogger();
        Exception exception = exceptionLogStrategyContext.getException();
        HttpServletRequest request = HttpContextHolder.getHttpServletRequest();

        String className = exception.getClass().getSimpleName().toLowerCase().replaceAll("(?i)(?=exception)", " ");
        String requestIp = request.getRemoteAddr();
        int requestPort = request.getServerPort();
        String requestUri = request.getRequestURI();
        String requestType = request.getMethod();

        String logMessage = formatLogMessage(className, requestIp, requestPort, requestUri, requestType, exception);
        logger.error("{}", logMessage);
    }

    protected abstract String formatLogMessage(String className, String requestIp, int requestPort, String requestUri, String requestType, Exception e);

    protected String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            throwable.printStackTrace(printWriter);
        }
        return stringWriter.toString();
    }

    protected String getSuppressedStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
            Throwable[] suppressed = throwable.getSuppressed();
            if (suppressed.length > 0) {
                for (Throwable t : suppressed) {
                    t.printStackTrace(printWriter);
                }
            }
        }
        return stringWriter.toString();
    }


    protected ExceptionDetails handleExceptionInformation(Throwable exception) {
        String message = exception.getMessage();
        String stackTrace = getStackTrace(exception);
        String suppressed = getSuppressedStackTrace(exception);
        return new ExceptionDetails(message, stackTrace, suppressed);
    }

}
