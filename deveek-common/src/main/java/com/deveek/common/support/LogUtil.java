package com.deveek.common.support;

import com.deveek.common.constant.LogFormatConstant;
import com.deveek.common.constant.LogLevelConstant;
import com.deveek.common.exceptionlog.factory.ExceptionLogStrategyFactory;
import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import com.deveek.common.exceptionlog.support.ExceptionLogStrategyContext;
import com.deveek.common.exceptionlog.support.ExceptionLogStrategyContextFactory;
import com.deveek.common.exceptionlog.support.ExceptionLogStrategyHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author banne
 */
@Component
public class LogUtil {
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = HttpContextHolder.getHttpServletRequest();
        
        return request;
    }
    
    public static void info() {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.INFO_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.info("{}", logMessage);
    }
    
    public static void info(String message, Object... args) {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.INFO_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.info("{}, Message: {}", logMessage, formatMessage(message, args));
    }
    
    public static void warn() {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.WARN_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.warn("{}", logMessage);
    }
    
    public static void warn(String message, Object... args) {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.WARN_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.warn("{}, Message: {}", logMessage, formatMessage(message, args));
    }
    
    public static void debug() {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.DEBUG_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.debug("{}", logMessage);
    }
    
    public static void debug(String message, Object... args) {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.DEBUG_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.debug("{}, Message: {}", logMessage, formatMessage(message, args));
    }
    
    public static void error() {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.ERROR_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.error("{}", logMessage);
    }
    
    public static void error(String message, Object... args) {
        Logger logger = getCallerLogger();
        
        String logMessage = formatLog(getRequest(), LogLevelConstant.ERROR_TYPE, LogFormatConstant.DEVELOPER_LOG);
        
        logger.error("{}, Message: {}", logMessage, formatMessage(message, args));
    }
    
    
    public static void error(Exception exception) {
        Logger logger = getCallerLogger();
        ExceptionLogStrategy exceptionLogStrategy = ExceptionLogStrategyFactory.getStrategy(exception);
        
        ExceptionLogStrategyContext exceptionLogStrategyContext = ExceptionLogStrategyContextFactory.createContext(logger, exception, exceptionLogStrategy);
        ExceptionLogStrategyHandler handler = new ExceptionLogStrategyHandler(exceptionLogStrategyContext);
        handler.handleStrategyException();
    }
    
    // 打印在控制台的格式
    private static String formatLog(HttpServletRequest request, String type, String format) {
        String requestIp = request.getRemoteAddr();
        int requestPort = request.getServerPort();
        String requestUri = request.getRequestURI();
        String requestType = request.getMethod();
        String requestMethod = getMethodName();
        
        // 格式化日志信息
        String logMessage = String.format(format, requestIp, requestPort, requestUri, requestType, requestMethod);
        
        return logMessage;
    }
    
    // 将传递参数进行格式化处理打印
    private static String formatMessage(String message, Object... args) {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                message = message.replaceFirst("\\{}", "" + args[i] + "");
            }
        }
        message = "[" + message + "]";
        
        return message;
    }
    
    private static String getMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        
        StackTraceElement caller = stackTraceElements[4];
        
        return caller.getMethodName();
    }
    
    private static String getClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        
        StackTraceElement caller = stackTraceElements[4];
        
        return caller.getClassName();
    }
    
    // 日志记录器
    public static Logger getCallerLogger() {
        String className = getClassName();
        
        Class<?> callerClass;
        try {
            callerClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }
        
        Logger logger = LoggerFactory.getLogger(callerClass);
        
        return logger;
    }
    
}
