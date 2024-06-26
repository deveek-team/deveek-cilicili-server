package com.deveek.common.exception;

import com.deveek.common.constant.Result;

/**
 * @author harvey 
 */
public class RemoteException extends BaseException {
    public RemoteException(int code, String message) {
        super(code, message);
    }
    
    public RemoteException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
    
    public <T> RemoteException(Result<T> result) {
        super(result);
    }
    
    public <T> RemoteException(Result<T> result, Throwable cause) {
        super(result, cause);
    }
}
