package com.deveek.common.exceptionlog.support;

import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;

/**
 * @author banne
 */
public class ExceptionLogStrategyHandler {
    private final ExceptionLogStrategyContext context;

    public ExceptionLogStrategyHandler(ExceptionLogStrategyContext context) {
        this.context = context;
    }

    public void handleStrategyException(Exception e) {
        ExceptionLogStrategy strategy = context.getExceptionStrategy();

        if (strategy != null) {
            strategy.logException(e);
        } else {
            throw new IllegalStateException("ExceptionStrategy not set");
        }
    }
}
