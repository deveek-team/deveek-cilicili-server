package com.deveek.common.exception;

import com.deveek.common.result.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private final int code;
    
    private final String message;

    public BaseException(int code, String message) {
        super(message, null);
        this.code = code;
        this.message = message;
    }
    
    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    public <T> BaseException(Result<T> result) {
        this(result.getCode(), result.getMessage());
    }
    
    public <T> BaseException(Result<T> result, Throwable cause) {
        this(result.getCode(), result.getMessage(), cause);
    }
}