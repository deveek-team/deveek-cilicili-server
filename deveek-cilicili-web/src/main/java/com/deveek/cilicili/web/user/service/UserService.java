package com.deveek.cilicili.web.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deveek.cilicili.web.common.user.model.po.UserPo;
import com.deveek.cilicili.web.common.user.model.vo.UserVo;
import com.deveek.security.common.model.dto.RegisterDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author harvey 
 */
public interface UserService extends IService<UserPo> {
    /**
     * Build the access_token and cache it.
     */
    String buildAccessTokenCache(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities);
    
    /**
     * Build the refresh_token and cache it.
     */
    String buildRefreshTokenCache(Long userId, String username);
    
    UserVo getUserVo(String username);
    
    boolean isUserExists(String username, String email);
    
    boolean isUsernameExists(String username);
    
    boolean isEmailExists(String email);

    void sendEmailVerifyCode(String username, String email);

    void register(RegisterDto registerDto);
}