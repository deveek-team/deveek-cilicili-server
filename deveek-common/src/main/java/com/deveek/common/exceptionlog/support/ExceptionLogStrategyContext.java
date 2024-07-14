package com.deveek.common.exceptionlog.support;

import com.deveek.common.exceptionlog.strategy.ExceptionLogStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;

/**
 * @author banne
 */
@Data
@AllArgsConstructor
public class ExceptionLogStrategyContext {
    private Logger logger;

    private Exception exception;

    private ExceptionLogStrategy exceptionLogStrategy;
}
