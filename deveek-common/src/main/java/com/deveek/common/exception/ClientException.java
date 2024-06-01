package com.deveek.common.exception;

import com.deveek.common.result.Result;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
public class ClientException extends BaseException {
    public ClientException(int code, String message) {
        super(code, message);
    }
    
    public ClientException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
    
    public <T> ClientException(Result<T> result) {
        super(result);
    }
    
    public <T> ClientException(Result<T> result, Throwable cause) {
        super(result, cause);
    }
}
