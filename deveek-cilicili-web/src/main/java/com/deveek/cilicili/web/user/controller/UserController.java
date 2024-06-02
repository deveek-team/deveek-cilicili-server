package com.deveek.cilicili.web.user.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWT;
import com.deveek.cilicili.web.common.user.UserCacheKey;
import com.deveek.cilicili.web.common.user.UserConstant;
import com.deveek.cilicili.web.common.user.UserResult;
import com.deveek.common.exception.ClientException;
import com.deveek.common.result.Result;
import com.deveek.cilicili.web.user.entity.domain.UserDo;
import com.deveek.cilicili.web.user.entity.dto.UserRegisterDto;
import com.deveek.cilicili.web.user.entity.vo.UserRefreshTokenVo;
import com.deveek.cilicili.web.user.entity.vo.UserVo;
import com.deveek.cilicili.web.user.service.UserService;
import com.deveek.security.support.AuthenticationTokenUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
@RestController
@PreAuthorize("hasAuthority('USER_R')")
@EnableMethodSecurity
public class UserController {
    @Resource
    private UserService userService;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
    @Resource(name = "userBloomFilter")
    private RBloomFilter userBloomFilter;
    
    @Resource
    private RedisTemplate redisTemplate;
    
    @Transactional
    @PostMapping(path = "/api/user/v1/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<Void> register(@ModelAttribute UserRegisterDto userRegisterDto) {
        boolean isUserExist = userService.isUserExists(userRegisterDto.getUsername(), userRegisterDto.getEmail());
        if (isUserExist) {
            throw new ClientException(UserResult.USER_NOT_FOUND);
        }
        
        UserDo userDo = BeanUtil.copyProperties(userRegisterDto, UserDo.class);
        
        String password = userDo.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        userDo.setPassword(encodedPassword);
        
        userService.saveOrUpdate(userDo);
        
        userBloomFilter.add(userDo.getId());
        
        return Result.success();
    }
    
    @PostMapping(path = "/api/user/v1/login/refresh", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result<UserRefreshTokenVo> refreshToken(@RequestParam String refreshToken) {
        JWT refreshTokenJwt = JWT.of(refreshToken).setKey(UserConstant.REFRESH_TOKEN_KEY);
        
        if (!refreshTokenJwt.verify()) {
            throw new ClientException(Result.FORBIDDEN);
        }
        
        String username = refreshTokenJwt.getPayload(UserConstant.USERNAME).toString();
        
        UserDo userDo = userService.getUserDo(username);
        Long userId = userDo.getId();
        String password = userDo.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDo.getAuthorities();
        
        String accessToken = userService.buildAccessTokenCache(userId, username, password, authorities);
        
        UserRefreshTokenVo userRefreshTokenVo = new UserRefreshTokenVo();
        userRefreshTokenVo.setAccessToken(accessToken);
        
        return Result.success(userRefreshTokenVo);
    }

    @GetMapping("/api/user/v1")
    public Result<UserVo> getUser(@RequestParam String username) {
        UserVo userVo = userService.getUserVo(username);
        
        return Result.success(userVo);
    }
    
    @GetMapping("/api/user/v1/mask")
    public Result<UserVo> getUserMask(@RequestParam String username) {
        UserVo userVo = userService.getUserVo(username);
        
        userVo.mask();
        
        return Result.success(userVo);
    }
}
