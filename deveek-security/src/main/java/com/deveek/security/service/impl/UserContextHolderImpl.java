package com.deveek.security.service.impl;

import com.deveek.security.common.model.vo.UserContext;
import com.deveek.security.common.service.UserContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author harvey 
 */
@Component
public class UserContextHolderImpl implements UserContextHolder {
    private static final ThreadLocal<UserContext> USER_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();
    
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
