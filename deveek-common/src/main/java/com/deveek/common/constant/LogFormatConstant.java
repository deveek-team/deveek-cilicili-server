package com.deveek.common.constant;

/**
 * @author banne
 */

public class LogFormatConstant {
    private LogFormatConstant(){}

    public static final String DEVER_LOG_FORMAT = "RequestUrl: [%s:%d%s], RequestType: [%s], RequestMethod: [%s]";

    public static final String SYSTEM_EXCEPTION_LOG_FORMAT = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Message: [%s], " + System.lineSeparator() +
            "StackTrace: [%s], " + System.lineSeparator() + "Suppressed: [%s]";

    public static final String SYSTEM_EXCEPTION_LOG_EXIST_CAUSE_FORMAT = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Message: [%s], " + System.lineSeparator() +
            "Exception Casue: [%s]" + "StackTrace: [%s], " + "Cause StackTrace:[%s], " +" Cause Suppressed:[%s], " + System.lineSeparator() + "Suppressed: [%s]";

    public static final String CILICILI_CUSTOM_EXCEPTION_LOG_FORMAT = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Code: [%d], Exception Message: [%s], " + System.lineSeparator() +
            "StackTrace: [%s], " + System.lineSeparator() + "Suppressed: [%s]";

    public static final String CILICILI_CUSTOM_EXCEPTION_LOG_EXIST_CAUSE_FORMAT = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Code: [%d], Exception Message: [%s], " + System.lineSeparator() +
            "Exception Casue: [%s]" + "StackTrace: [%s], " + "Cause StackTrace:[%s], " +" Cause Suppressed:[%s], " + System.lineSeparator() + "Suppressed: [%s]";
}


