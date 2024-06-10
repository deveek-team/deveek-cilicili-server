package com.deveek.security.common.service;

import com.deveek.security.common.model.vo.UserContext;

public interface UserContextHolder {
    void setUserContext(UserContext userContext);
    
    UserContext getUserContext();
    
    String getUsername();
    
    Long getUserId();
    
    void clear();
}