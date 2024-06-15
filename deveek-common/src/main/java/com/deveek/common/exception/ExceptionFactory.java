package com.deveek.common.exception;

import cn.hutool.core.util.ObjUtil;
import com.deveek.common.constant.Result;

/**
 * @author harvey 
 */
public class ExceptionFactory {
    public static <T> BaseException getException(ExceptionType exceptionType, Result<T> result) {
        if (ObjUtil.equal(ExceptionType.CLIENT, exceptionType)) {
            return new ClientException(result);
        }
        
        if (ObjUtil.equal(ExceptionType.SERVER, exceptionType)) {
            return new ServerException(result);
        }
        
        if (ObjUtil.equal(ExceptionType.REMOTE, exceptionType)) {
            return new RemoteException(result);
        }
        
        return new BaseException(result);
    }
}
