package com.deveek.common.support;

import com.deveek.common.constant.Result;
import com.deveek.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author harvey
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public Result handleClientException(ClientException e, HttpServletRequest req) {
        LogUtil.error(e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ServerException.class)
    public Result handleServerException(ServerException e, HttpServletRequest req) {
        LogUtil.error(e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RemoteException.class)
    public Result handleRemoteException(RemoteException e, HttpServletRequest req) {
        LogUtil.error(e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException e, HttpServletRequest req) {
        LogUtil.error(e);
        return Result.failure(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(BeanInstantiationException.class)
    public Result handleBeanInstantiationException(BeanInstantiationException e, HttpServletRequest req) {
        Throwable cause = e.getCause();
        if (cause instanceof ClientException) {
            return handleClientException((ClientException) cause, req);
        } else if (cause instanceof ServerException) {
            return handleServerException((ServerException) cause, req);
        } else if (cause instanceof RemoteException) {
            return handleRemoteException((RemoteException) cause, req);
        } else if (cause instanceof BaseException) {
            return handleBaseException((BaseException) cause, req);
        }

        LogUtil.error(e);
        return Result.FAILURE;
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e, HttpServletRequest req) {
        LogUtil.error(e);
        return Result.FAILURE;
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest req) {
        LogUtil.error(e);
        return Result.FAILURE;
    }
}
