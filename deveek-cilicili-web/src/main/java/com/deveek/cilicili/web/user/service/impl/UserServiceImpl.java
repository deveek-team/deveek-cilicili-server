package com.deveek.cilicili.web.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.constant.UserCacheKey;
import com.deveek.cilicili.web.common.user.constant.UserResult;
import com.deveek.cilicili.web.common.user.model.po.UserPo;
import com.deveek.cilicili.web.common.user.model.vo.UserVo;
import com.deveek.cilicili.web.user.mapper.UserMapper;
import com.deveek.cilicili.web.user.service.*;
import com.deveek.common.constant.Constant;
import com.deveek.common.constant.EmailConstant;
import com.deveek.common.exception.ClientException;
import com.deveek.common.support.SendMailUtil;
import com.deveek.security.common.constant.SecurityCacheKey;
import com.deveek.security.support.AuthenticationTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author harvey 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserService {
    @Resource
    private AuthService authService;
    
    @Resource
    private UserRoleService userRoleService;
    
    @Resource
    private UserAuthService userAuthService;
    
    @Resource
    private RoleAuthService roleAuthService;
    
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SendMailUtil sendMailUtil;
    
    @Override
    public String buildAccessTokenCache(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        String accessToken = AuthenticationTokenUtil.genAccessToken(userId, username, password, authorities);
        
        redisTemplate.opsForValue().set(
            SecurityCacheKey.ACCESS_TOKEN.getKey(userId),
            accessToken,
            SecurityCacheKey.ACCESS_TOKEN.timeout,
            SecurityCacheKey.ACCESS_TOKEN.unit
        );
        
        return accessToken;
    }
    
    @Override
    public String buildRefreshTokenCache(Long userId, String username) {
        String refreshToken = AuthenticationTokenUtil.genRefreshToken(userId, username);
        
        redisTemplate.opsForValue().set(
            SecurityCacheKey.REFRESH_TOKEN.getKey(userId),
            refreshToken
        );
        
        return refreshToken;
    }
    
    @Override
    public UserVo getUserVo(String username) {
        UserPo userPo = lambdaQuery()
            .select(UserPo::getUsername, UserPo::getEmail)
            .eq(UserPo::getUsername, username)
            .eq(UserPo::getDeletedFlag, Constant.NOT_DELETED)
            .one();
        if (ObjUtil.isNull(userPo)) {
            throw new ClientException(UserResult.USER_NOT_FOUND);
        }

        UserVo userVo = BeanUtil.copyProperties(userPo, UserVo.class);

        return userVo;
    }
    
    @Override
    public boolean isUserExists(String username, String email) {
        boolean isExists = isUsernameExists(username) || isEmailExists(email);
        
        return isExists;
    }
    
    @Override
    public boolean isUsernameExists(String username) {
        boolean isExists = lambdaQuery()
            .eq(UserPo::getUsername, username)
            .exists();
        
        return isExists;
    }
    
    @Override
    public boolean isEmailExists(String email) {
        boolean isExists = lambdaQuery()
            .eq(UserPo::getEmail, email)
            .exists();
        
        return isExists;
    }

    @Override
    public void sendCode(String username, String email) {
        if (isNotEmail(email)) {
            throw new ClientException(UserResult.EMAIL_INVALID);
        }

        boolean isUserExist = isUserExists(username, email);
        if (isUserExist) {
            throw new ClientException(UserResult.USER_EXISTS);
        }

        String code = RandomUtil.randomNumbers(6);
        sendMailUtil.sendSimpleMail(EmailConstant.MESSAGE_HEADER, code, EmailConstant.SENDER, email);

        String key = UserCacheKey.EMAIL_CODE.getKey(email, code);
        stringRedisTemplate.opsForValue().set(key, code, UserCacheKey.EMAIL_CODE.timeout, UserCacheKey.EMAIL_CODE.unit);
    }

    @Override
    public void register(String username, String password, String email, String verifyCode) {
        if (isNotEmail(email)) {
            throw new ClientException(UserResult.EMAIL_INVALID);
        }

        boolean isUserExist = isUserExists(username, verifyCode);
        if (isUserExist) {
            throw new ClientException(UserResult.USER_EXISTS);
        }

        String key = UserCacheKey.EMAIL_CODE.getKey(email, verifyCode);
        if (isNotKeyExist(key)) {
            throw new ClientException(UserResult.CODE_INVALID);
        }
    }

    private Boolean isKeyExist(String key) {
        return stringRedisTemplate.delete(key);
    }

    private Boolean isNotKeyExist(String key) {
        return !isKeyExist(key);
    }

    private Boolean isNotEmail(String email) {
        return !Validator.isEmail(email);
    }
}