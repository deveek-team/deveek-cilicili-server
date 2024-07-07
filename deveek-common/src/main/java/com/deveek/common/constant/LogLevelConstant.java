package com.deveek.common.constant;

/**
 * @author banne
 */

public class LogLevelConstant {
    // 日志级别常量 避免直接实例化此类
    private LogLevelConstant() {
    }
    public static final String OFF_TYPE = "OFF";

    public static final String FATAL_TYPE = "FATAL";

    public static final String ERROR_TYPE = "ERROR";

    public static final String WARN_TYPE = "WARN";

    public static final String INFO_TYPE = "INFO";

    public static final String DEBUG_TYPE = "DEBUG";

    public static final String TRACE_TYPE = "TRACE";
}
