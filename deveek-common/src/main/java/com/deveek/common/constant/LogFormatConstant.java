package com.deveek.common.constant;

/**
 * @author banne
 */

public class LogFormatConstant {
    private LogFormatConstant(){}

    public static final String DEVER_LOG_FORMAT = "RequestUrl: [%s:%d%s], RequestType: [%s], RequestMethod: [%s]";

    public static final String SYSTEM_EXCEPTION_LOG_FORMAT = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Message: [%s]";

    public static final String CILICILI_CUSTOM_EXCEPTION_LOG_FORMAT = "Catch %s, RequestUrl: [%s:%d%s], RequestType: [%s], Exception Class: [%s], Exception Code: [%d], Exception Message: [%s]";
}


