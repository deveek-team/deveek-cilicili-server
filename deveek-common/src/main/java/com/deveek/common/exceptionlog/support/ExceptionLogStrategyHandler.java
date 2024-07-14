package com.deveek.common.exceptionlog.support;

import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import org.slf4j.Logger;

/**
 * @author banne
 */
public class ExceptionLogStrategyHandler {
    private final ExceptionLogStrategyContext context;

    public ExceptionLogStrategyHandler(ExceptionLogStrategyContext context) {
        this.context = context;
    }

    public void handleStrategyException() {
        ExceptionLogStrategy strategy = context.getExceptionLogStrategy();

        if (strategy != null) {
            strategy.logException(context);
        } else {
            throw new IllegalStateException("ExceptionStrategy not set");
        }
    }
}
