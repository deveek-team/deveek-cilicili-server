package com.deveek.common.exceptionlog.strategy;

/**
 * @author banne
 */

public interface ExceptionLogStrategy {
    void logException(Exception e);
}
