package com.deveek.security.service.impl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.deveek.security.common.model.vo.UserContext;
import com.deveek.security.common.service.UserContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author harvey 
 */
@Component
public class UserContextHolderImpl implements UserContextHolder {
    private static final TransmittableThreadLocal<UserContext> USER_CONTEXT_THREAD_LOCAL = new TransmittableThreadLocal<>();
    
    public void setUserContext(UserContext userContext) {
        USER_CONTEXT_THREAD_LOCAL.set(userContext);
    }
    
    public UserContext getUserContext() {
        return USER_CONTEXT_THREAD_LOCAL.get();
    }
    
    public String getUsername() {
        return getUserContext().getUsername();
    }
    
    public Long getUserId() {
        return getUserContext().getUserId();
    }
    
    public void clear() {
        USER_CONTEXT_THREAD_LOCAL.remove();
    }
}
