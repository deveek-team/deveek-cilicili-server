package com.deveek.cilicili.web.user.support;

import com.deveek.cilicili.web.common.user.model.vo.LoginVo;
import com.deveek.common.constant.Result;
import com.deveek.common.support.ResponseUtil;
import com.deveek.security.common.constant.SecurityCacheKey;
import com.deveek.security.model.dto.UserDetailsDto;
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
 * @author harvey 
 */
@Component
public class GlobalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private RedisTemplate redisTemplate;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsDto userDetailsDto = (UserDetailsDto) authentication.getPrincipal();
        Long userId = userDetailsDto.getId();
        String username = userDetailsDto.getUsername();
        String password = userDetailsDto.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDetailsDto.getAuthorities();
        
        String accessToken = AuthenticationTokenUtil.genAccessToken(userId, username, password, authorities);
        
        redisTemplate.opsForValue().set(
            SecurityCacheKey.ACCESS_TOKEN.getKey(userId),
            accessToken,
            SecurityCacheKey.ACCESS_TOKEN.timeout,
            SecurityCacheKey.ACCESS_TOKEN.unit
        );
        
        String refreshToken = AuthenticationTokenUtil.genRefreshToken(userId, username);
        redisTemplate.opsForValue().set(
            SecurityCacheKey.REFRESH_TOKEN.getKey(userId),
            refreshToken
        );
        
        LoginVo loginVo = new LoginVo();
        loginVo.setUserId(userId);
        loginVo.setUsername(username);
        loginVo.setAccessToken(accessToken);
        loginVo.setRefreshToken(refreshToken);
        Result<LoginVo> loginVoResult = Result.success(loginVo);
        
        ResponseUtil.write(response, loginVoResult);
    }
}
