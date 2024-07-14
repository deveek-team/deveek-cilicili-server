package com.deveek.common.exceptionlog.strategy;

import com.deveek.common.exceptionlog.support.ExceptionLogStrategyContext;

/**
 * @author banne
 */

public interface ExceptionLogStrategy {

    void logException(ExceptionLogStrategyContext context);
}
