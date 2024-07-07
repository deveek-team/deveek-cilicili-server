package com.deveek.cilicili.web.user.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.deveek.cilicili.web.common.user.model.dto.UserSendEmailVerifyCodeDto;
import com.deveek.cilicili.web.user.service.UserService;
import com.deveek.common.constant.Result;
import com.deveek.common.exception.ClientException;
import com.deveek.security.common.constant.SecurityCacheKey;
import com.deveek.security.common.constant.SecurityConstant;
import com.deveek.security.common.constant.SecurityHttpUri;
import com.deveek.security.common.model.dto.RegisterDto;
import com.deveek.security.common.model.vo.RefreshTokenVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author harvey 
 */
@RestController
public class SecurityController {
    @Resource
    private UserService userService;
    
    @Resource
    private UserDetailsService userDetailsService;
    
    @Resource
    private HttpServletRequest request;
    
    @Resource
    private RedisTemplate redisTemplate;
    
    @PostMapping(path = SecurityHttpUri.REGISTER, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<Void> register(@ModelAttribute RegisterDto registerDto) {
        userService.register(registerDto);
        
        return Result.success();
    }
    
    @PostMapping(path = SecurityHttpUri.SEND_EMAIL_VERIFY_CODE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<Void> sendEmailVerifyCode(@ModelAttribute UserSendEmailVerifyCodeDto userSendEmailVerifyCodeDto) {
        String username = userSendEmailVerifyCodeDto.getUsername();
        String email = userSendEmailVerifyCodeDto.getEmail();
        userService.sendEmailVerifyCode(username, email);
        
        return Result.success();
    }
    
    @PostMapping(path = SecurityHttpUri.REFRESH_TOKEN, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<RefreshTokenVo> refreshToken(@RequestParam String refreshToken) {
        // If the request header does not carry Login Token, deny access directly.
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(accessToken)) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        // If the access_token is incorrect, deny access directly.
        JWT accessTokenJwt = JWT.of(accessToken).setKey(SecurityConstant.ACCESS_TOKEN_KEY);
        if (!accessTokenJwt.verify()) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        // If the refresh_token is blank, deny access directly.
        if (StrUtil.isBlank(refreshToken)) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        // If the refresh_token is incorrect, deny access directly.
        JWT refreshTokenJwt = JWT.of(refreshToken).setKey(SecurityConstant.REFRESH_TOKEN_KEY);
        if (!refreshTokenJwt.verify()) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        String username = refreshTokenJwt.getPayload(SecurityConstant.USERNAME).toString();
        Long userId = Long.valueOf(refreshTokenJwt.getPayload(SecurityConstant.USER_ID).toString());
        
        // If the access_token is inconsistent with the access_token stored in Redis, deny access directly.
        String accessTokenCacheKey = SecurityCacheKey.ACCESS_TOKEN.getKey(userId);
        String accessTokenCache = (String) redisTemplate.opsForValue().get(accessTokenCacheKey);
        if (!accessToken.equals(accessTokenCache)) {
            throw new ClientException(Result.UNAUTHORIZED);
        }
        
        // Get the latest password and permission information from the database.
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String password = userDetails.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        
        accessToken = userService.buildAccessTokenCache(userId, username, password, authorities);
        
        RefreshTokenVo refreshTokenVo = new RefreshTokenVo();
        refreshTokenVo.setAccessToken(accessToken);
        
        return Result.success(refreshTokenVo);
    }
}
