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
    // 通过 HashMap 存储 key - value 通过get获取

    private static final Map<Class<?>, ExceptionLogStrategy> strategyMap = new HashMap<>();

    static {
        // 自定义异常策略
        strategyMap.put(BaseException.class, new CiliCiliExceptionLogStrategy());
        strategyMap.put(ClientException.class, new CiliCiliExceptionLogStrategy());
        strategyMap.put(ServerException.class, new CiliCiliExceptionLogStrategy());
        strategyMap.put(RemoteException.class, new CiliCiliExceptionLogStrategy());

        // 系统异常策略
        strategyMap.put(Exception.class, new SystemExceptionLogStrategy());
    }

    public static ExceptionLogStrategy getStrategy(Exception e) {
        Class<?> exceptionClass = e.getClass();
        ExceptionLogStrategy strategy = strategyMap.get(exceptionClass);

        if (strategy == null) {
            strategy = strategyMap.get(Exception.class);
        }

        return strategy;
    }
}
