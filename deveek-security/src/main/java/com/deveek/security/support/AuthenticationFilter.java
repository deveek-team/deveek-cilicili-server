package com.deveek.security.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.deveek.common.constant.Result;
import com.deveek.common.support.ResponseUtil;
import com.deveek.security.common.constant.SecurityCacheKey;
import com.deveek.security.common.constant.SecurityConstant;
import com.deveek.security.common.constant.SecurityHttpUri;
import com.deveek.security.common.model.vo.UserContext;
import com.deveek.security.common.service.UserContextHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

/**
 * @author harvey 
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private UserContextHolder userContextHolder;
    
    @Resource
    private RedisTemplate redisTemplate;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // If the request is login, then skip the filter.
        String requestUri = request.getRequestURI();
        if (
            StrUtil.equals(requestUri, SecurityHttpUri.LOGIN) ||
            StrUtil.equals(requestUri, SecurityHttpUri.REFRESH_TOKEN) ||
            StrUtil.equals(requestUri, SecurityHttpUri.REGISTER) ||
            StrUtil.equals(requestUri, SecurityHttpUri.SEND_EMAIL_VERIFY_CODE)
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // If the request header does not carry Login Token.
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(accessToken)) {
            ResponseUtil.write(response, Result.UNAUTHORIZED);
            return;
        }
        
        // If the access_token is incorrect, deny access directly.
        JWT accessTokenJwt = JWT.of(accessToken).setKey(SecurityConstant.ACCESS_TOKEN_KEY);
        if (!accessTokenJwt.verify()) {
            ResponseUtil.write(response, Result.UNAUTHORIZED);
            return;
        }
        
        // If the access_token expires, notify the client to obtain a new access_token.
        if (!accessTokenJwt.validate(0)) {
            ResponseUtil.write(response, Result.TOKEN_EXPIRED);
            return;
        }
        
        // Get user info from token.
        Long userId = Long.valueOf(accessTokenJwt.getPayload(SecurityConstant.USER_ID).toString());
        String username = accessTokenJwt.getPayload(SecurityConstant.USERNAME).toString();
        String password = accessTokenJwt.getPayload(SecurityConstant.PASSWORD).toString();
        String authoritiesJson = accessTokenJwt.getPayload(SecurityConstant.AUTHORITIES).toString();
        Collection<? extends GrantedAuthority> authorities = JSON.parseObject(authoritiesJson, new TypeReference<>() {});
        
        // Set user info to UserContext.
        UserContext userContext = new UserContext(userId, username);
        userContextHolder.setUserContext(userContext);
        
        // If the access_token is inconsistent with the access_token stored in Redis,
        // notify the client to obtain a new access_token.
        String accessTokenKey = SecurityCacheKey.ACCESS_TOKEN.getKey(userId);
        String accessTokenCache = (String) redisTemplate.opsForValue().get(accessTokenKey);
        if (!accessToken.equals(accessTokenCache)) {
            ResponseUtil.write(response, Result.TOKEN_EXPIRED);
            return;
        }
        
        // If the user is not authenticated, then authenticate the user.
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(username, password, authorities));
        }
        
        filterChain.doFilter(request, response);
    }
}