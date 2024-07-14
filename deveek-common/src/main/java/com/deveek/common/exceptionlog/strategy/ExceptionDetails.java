package com.deveek.common.exceptionlog.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author banne
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {
    private String message;
    private String stackTrace;
    private String suppressed;
}
