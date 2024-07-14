package com.deveek.common.exceptionlog.support;

import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import org.slf4j.Logger;

/**
 * @author banne
 */

public class ExceptionLogStrategyContextFactory {
    public static ExceptionLogStrategyContext createContext(Logger logger, Exception exception, ExceptionLogStrategy strategy) {
            return new ExceptionLogStrategyContext(logger, exception, strategy);
    }
}
