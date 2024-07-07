package com.deveek.common.exceptionlog.support;

import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;

/**
 * @author banne
 */
public class ExceptionLogStrategyContext {
    private ExceptionLogStrategy strategy;

    // 设置策略
    public void setExceptionStrategy(ExceptionLogStrategy strategy){
        this.strategy = strategy;
    }

    // 获取策略
    public ExceptionLogStrategy getExceptionStrategy(){
        return strategy;
    }
}
