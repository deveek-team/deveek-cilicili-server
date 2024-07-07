package com.deveek.cilicili.web.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.constant.UserCacheKey;
import com.deveek.cilicili.web.common.user.constant.UserResult;
import com.deveek.cilicili.web.common.user.model.po.UserPo;
import com.deveek.cilicili.web.common.user.model.vo.UserVo;
import com.deveek.cilicili.web.user.mapper.UserMapper;
import com.deveek.cilicili.web.user.service.MailService;
import com.deveek.cilicili.web.user.service.UserService;
import com.deveek.common.constant.Constant;
import com.deveek.common.exception.ClientException;
import com.deveek.common.support.Validator;
import com.deveek.security.common.constant.SecurityCacheKey;
import com.deveek.security.common.constant.SecurityConstant;
import com.deveek.security.common.constant.SecurityResult;
import com.deveek.security.common.model.dto.RegisterDto;
import com.deveek.security.support.AuthenticationTokenUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author harvey 
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserService {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MailService mailService;
    
    @Resource(name = "userBloomFilter")
    private RBloomFilter userBloomFilter;
    
    @Resource
    private PasswordEncoder passwordEncoder;
    
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
    public void sendEmailVerifyCode(String username, String email) {
        if (Validator.isNotEmail(email)) {
            throw new ClientException(SecurityResult.EMAIL_INVALID);
        }

        boolean isUserExist = isUserExists(username, email);
        if (isUserExist) {
            throw new ClientException(SecurityResult.USER_EXISTS);
        }

        String verifyCode = RandomUtil.randomNumbers(6);
        mailService.sendVerifyCode(verifyCode, email);

        String verifyCodeKey = UserCacheKey.EMAIL_VERIFY_CODE.getKey(email, verifyCode);
        redisTemplate.opsForValue().set(verifyCodeKey, verifyCode, UserCacheKey.EMAIL_VERIFY_CODE.timeout, UserCacheKey.EMAIL_VERIFY_CODE.unit);
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String email = registerDto.getEmail();
        String emailVerifyCode = registerDto.getVerifyCode();
        
        boolean isUserExist = isUserExists(username, email);
        if (isUserExist) {
            throw new ClientException(SecurityResult.USER_EXISTS);
        }

        String emailVerifyCodeKey = UserCacheKey.EMAIL_VERIFY_CODE.getKey(email, emailVerifyCode);
        Boolean isDeleted = redisTemplate.delete(emailVerifyCodeKey);
        if (Boolean.FALSE.equals(isDeleted)) {
            throw new ClientException(SecurityResult.VERIFY_CODE_INVALID);
        }
        
        UserPo userPo = BeanUtil.copyProperties(registerDto, UserPo.class);
        userPo.setPassword(passwordEncoder.encode(password));
        userPo.setAccountExpiredFlag(SecurityConstant.ACCOUNT_NOT_EXPIRED);
        userPo.setCredentialsExpiredFlag(SecurityConstant.CREDENTIALS_NOT_EXPIRED);
        userPo.setAccountLockedFlag(SecurityConstant.ACCOUNT_NOT_LOCKED);
        userPo.setUpdateBy(SecurityConstant.ADMIN_ID);
        userPo.setCreateBy(SecurityConstant.ADMIN_ID);
        
        saveOrUpdate(userPo);
        
        userBloomFilter.add(userPo.getId());
    }
}