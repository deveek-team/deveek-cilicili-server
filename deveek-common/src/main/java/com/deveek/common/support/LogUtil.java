package com.deveek.common.support;

import com.deveek.common.constant.LogLevelConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author banne
 */
@Component
public class LogUtil {
    // 开发者打印日志格式
    public static final String DEVER_LOG_FORMAT = "RequestUrl: [%s:%d%s], RequestType: [%s], RequestMethod: [%s]";

    private static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    public static void info() {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.INFO_TYPE,DEVER_LOG_FORMAT);

        logger.info("{}", logMessage);

    }

    public static void info(String message, Object... args) {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.INFO_TYPE, DEVER_LOG_FORMAT);

        logger.info("{}, Message: {}", logMessage, formatMessage(message, args));

    }

    public static void warn() {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.WARN_TYPE, DEVER_LOG_FORMAT);

        logger.warn("{}", logMessage);
    }

    public static void warn(String message,Object... args) {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(),LogLevelConstant.WARN_TYPE, DEVER_LOG_FORMAT);

        logger.warn("{}, Message: {}", logMessage, formatMessage(message, args));

    }

    public static void debug() {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.DEBUG_TYPE, DEVER_LOG_FORMAT);

        logger.debug("{}", logMessage);
    }

    public static void debug(String message,Object... args) {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.DEBUG_TYPE, DEVER_LOG_FORMAT);

        logger.debug("{}, Message: {}", logMessage, formatMessage(message, args));

    }

    public static void error() {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.ERROR_TYPE, DEVER_LOG_FORMAT);

        logger.error("{}", logMessage);
    }

    public static void error(String message,Object... args) {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.ERROR_TYPE, DEVER_LOG_FORMAT);

        logger.error("{}, Message: {}", logMessage, formatMessage(message, args));
    }

    public static void error(Exception e) {
        Logger logger = getCallerLogger();

        String logMessage = formatLog(getRequest(), LogLevelConstant.ERROR_TYPE, DEVER_LOG_FORMAT);

        logger.error("{}, Exception Class:{} Exception Message: {}",logMessage,e.getClass(),e.getMessage());
    }

    // 系统异常处理日志
    public static void error(Exception e, Class<? extends Throwable> exceptionType) {
        Logger logger = getCallerLogger();

        try{
            Throwable instance = exceptionType.getDeclaredConstructor().newInstance();
            Class<? extends Throwable> aClass = instance.getClass();
            String exceptionName = aClass.getName().toLowerCase();
            String[] split = exceptionName.split("\\.");

            HttpServletRequest request = getRequest();

            if(split.length > 0){
                String className = split[split.length - 1];
                String modifiedClassName = className.replaceAll("(?i)(?=exception)", " ");

                String format = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Message: [%s]";

                String requestIp = request.getRemoteAddr();
                int requestPort = request.getRemotePort();
                String requestUri = request.getRequestURI();
                String requestType = request.getMethod();

                // 格式化日志信息
                String logMessage = String.format(format,
                        modifiedClassName, requestIp,requestPort,requestUri, requestType, e.getClass(),e.getMessage());

                logger.error("{}",logMessage);
            }
        }catch(Exception exception){
            exception.getStackTrace();
            System.out.println(exception.getMessage());
        }
    }

    // 自定义异常处理日志
    public static void error(int code, Exception e, Class<? extends Throwable> exceptionType) {
        Logger logger = getCallerLogger();

        try{
            Throwable instance = exceptionType.getDeclaredConstructor(int.class,String.class).newInstance(code,e.getMessage());
            Class<? extends Throwable> aClass = instance.getClass();
            String exceptionName = aClass.getName().toLowerCase();
            String[] split = exceptionName.split("\\.");

            HttpServletRequest request = getRequest();

            if(split.length > 0){
                String className = split[split.length - 1];
                String modifiedClassName = className.replaceAll("(?i)(?=exception)", " ");

                String format = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Code: [%d], Exception Message: [%s]";

                String requestIp = request.getRemoteAddr();
                int requestPort = request.getRemotePort();
                String requestUri = request.getRequestURI();
                String requestType = request.getMethod();

                // 格式化日志信息
                String logMessage = String.format(format,
                        modifiedClassName, requestIp,requestPort,requestUri, requestType, e.getClass(),code,e.getMessage());

                logger.error("{}",logMessage);
            }
        }catch(Exception exception){
            exception.getStackTrace();
        }
    }

    // 打印在控制台的格式
    public static String formatLog(HttpServletRequest request, String type, String format) {
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
    private static String formatMessage(String message, Object... args)  {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                message = message.replaceFirst("\\{}", "" + args[i] + "");
            }
        }
        message = "["+message+"]";
        return message;
    }


    private static String getMethodName(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTraceElements[4];

        return caller.getMethodName();
    }

    private static String getClassName(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTraceElements[4];

        return caller.getClassName();
    }

    // 日志记录器
    private static Logger getCallerLogger() {
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
