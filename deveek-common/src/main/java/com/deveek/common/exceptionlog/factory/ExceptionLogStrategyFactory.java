package com.deveek.common.exceptionlog.factory;

import com.deveek.common.exception.BaseException;
import com.deveek.common.exception.ClientException;
import com.deveek.common.exception.RemoteException;
import com.deveek.common.exception.ServerException;
import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import com.deveek.common.exceptionlog.strategy.imp.CiliCiliExceptionLogStrategy;
import com.deveek.common.exceptionlog.strategy.imp.SystemExceptionLogStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author banne
 */
public class ExceptionLogStrategyFactory {
    private static final Map<Class<?>, ExceptionLogStrategy> EXCEPTION_LOG_STRATEGY_MAP = new HashMap<>();

    static {
        CiliCiliExceptionLogStrategy ciliCiliExceptionLogStrategy = new CiliCiliExceptionLogStrategy();
        EXCEPTION_LOG_STRATEGY_MAP.put(BaseException.class, ciliCiliExceptionLogStrategy);
        EXCEPTION_LOG_STRATEGY_MAP.put(ClientException.class, ciliCiliExceptionLogStrategy);
        EXCEPTION_LOG_STRATEGY_MAP.put(ServerException.class, ciliCiliExceptionLogStrategy);
        EXCEPTION_LOG_STRATEGY_MAP.put(RemoteException.class, ciliCiliExceptionLogStrategy);

        SystemExceptionLogStrategy systemExceptionLogStrategy = new SystemExceptionLogStrategy();
        EXCEPTION_LOG_STRATEGY_MAP.put(Exception.class, systemExceptionLogStrategy);
    }

    public static ExceptionLogStrategy getStrategy(Exception e) {
        Class<?> exceptionClass = e.getClass();
        ExceptionLogStrategy strategy = EXCEPTION_LOG_STRATEGY_MAP.get(exceptionClass);

        if (strategy == null) {
            strategy = EXCEPTION_LOG_STRATEGY_MAP.get(Exception.class);
        }

        return strategy;
    }
}
