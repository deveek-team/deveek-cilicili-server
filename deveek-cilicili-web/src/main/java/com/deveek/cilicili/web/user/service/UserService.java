package com.deveek.cilicili.web.user.service;

import com.deveek.cilicili.web.user.entity.domain.UserDo;
import com.deveek.cilicili.web.user.entity.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
public interface UserService extends IService<UserDo> {
    UserDo getUserDo(String username);
    
    String buildAccessTokenCache(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities);
    
    String buildRefreshTokenCache(Long userId, String username);
    
    UserVo getUserVo(String username);
    
    boolean isUserExists(String username, String email);
    
    boolean isUsernameExists(String username);
    
    boolean isEmailExists(String email);
}