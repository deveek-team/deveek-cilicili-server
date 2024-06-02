package com.deveek.cilicili.web.user.support;

import com.deveek.cilicili.web.common.user.UserCacheKey;
import com.deveek.cilicili.web.user.entity.domain.UserDo;
import com.deveek.cilicili.web.user.entity.vo.LoginVo;
import com.deveek.cilicili.web.user.service.UserService;
import com.deveek.common.result.Result;
import com.deveek.common.support.ResponseUtil;
import com.deveek.security.support.AuthenticationTokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-01
 */
@Component
public class GlobalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private UserService userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDo userDo = (UserDo) authentication.getPrincipal();
        Long userId = userDo.getId();
        String username = userDo.getUsername();
        String password = userDo.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDo.getAuthorities();
        
        String accessToken = userService.buildAccessTokenCache(userId, username, password, authorities);
        
        String refreshToken = userService.buildRefreshTokenCache(userId, username);
        
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(userId);
        loginVo.setUsername(username);
        loginVo.setAccessToken(accessToken);
        loginVo.setRefreshToken(refreshToken);
        Result<LoginVo> loginVoResult = Result.success(loginVo);
        
        ResponseUtil.write(response, loginVoResult);
    }
}
