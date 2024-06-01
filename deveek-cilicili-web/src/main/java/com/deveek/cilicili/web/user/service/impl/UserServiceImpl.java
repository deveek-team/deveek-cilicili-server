package com.deveek.cilicili.web.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.UserResult;
import com.deveek.cilicili.web.user.entity.domain.*;
import com.deveek.cilicili.web.user.entity.vo.UserVo;
import com.deveek.cilicili.web.user.mapper.UserMapper;
import com.deveek.cilicili.web.user.service.*;
import com.deveek.common.constant.Constant;
import com.deveek.common.exception.ClientException;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService, UserDetailsService {
    @Resource
    private AuthService authService;
    
    @Resource
    private UserRoleService userRoleService;
    
    @Resource
    private UserAuthService userAuthService;
    
    @Resource
    private RoleAuthService roleAuthService;
    
    @Override
    public UserDo getUserDo(String username) {
        return (UserDo) loadUserByUsername(username);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDo userDo = lambdaQuery()
            .eq(UserDo::getUsername, username)
            .one();
        if (userDo == null) {
            throw new ClientException(UserResult.USER_NOT_FOUND);
        }
        
        Long userId = userDo.getId();
        
        Set<String> authNameSet = getAuthNameSet(userId);
        
        // Convert AuthName to SimpleGrantedAuthority, Convert AuthNameSet to Authorities.
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authNameSet);
        userDo.setAuthorities(authorities);
        
        return userDo;
    }
    
    /**
     * Get AuthNameSet by UserId.
     */
    private Set<String> getAuthNameSet(Long userId) {
        Set<Long> authIdSetFromAuthTable = getAuthIdSetFromAuthTable(userId);
        
        Set<Long> authIdSetFromRoleTable = getAuthIdSetFromRoleTable(userId);
        
        Set<Long> authIdSet = new HashSet<>();
        authIdSet.addAll(authIdSetFromAuthTable);
        authIdSet.addAll(authIdSetFromRoleTable);
        
        List<AuthDo> authDolist = authService.lambdaQuery()
            .in(AuthDo::getId, authIdSet)
            .list();
        
        Set<String> authNameSet = authDolist.stream()
            .map(AuthDo::getName)
            .collect(Collectors.toSet());
        
        return authNameSet;
    }
    
    /**
     * Get AuthIdSet from t_user_auth by UserId.
     */
    private Set<Long> getAuthIdSetFromAuthTable(Long userId) {
        List<UserAuthDo> userAuthDoList = userAuthService.lambdaQuery()
            .eq(UserAuthDo::getUserId, userId)
            .select(UserAuthDo::getAuthId)
            .list();
        
        Set<Long> authIdSet = userAuthDoList.stream()
            .map(UserAuthDo::getAuthId)
            .collect(Collectors.toSet());
        
        return authIdSet;
    }
    
    /**
     * Get RoleIdSet from t_user_role by UserId, then get AuthIdSet from t_role_auth by RoleIdSet.
     */
    private Set<Long> getAuthIdSetFromRoleTable(Long userId) {
        List<UserRoleDo> userRoleDoList = userRoleService.lambdaQuery()
            .eq(UserRoleDo::getUserId, userId)
            .select(UserRoleDo::getRoleId)
            .list();
        
        Set<Long> roleIdSet = userRoleDoList.stream()
            .map(UserRoleDo::getRoleId)
            .collect(Collectors.toSet());
        
        List<RoleAuthDo> roleAuthDoList = roleAuthService.lambdaQuery()
            .in(RoleAuthDo::getRoleId, roleIdSet)
            .list();
        
        Set<Long> authIdSet = roleAuthDoList.stream()
            .map(RoleAuthDo::getAuthId)
            .collect(Collectors.toSet());
        
        return authIdSet;
    }
    
    @Override
    public UserVo getUserVo(String username) {
        UserDo userDo = lambdaQuery()
            .select(UserDo::getUsername, UserDo::getEmail)
            .eq(UserDo::getUsername, username)
            .eq(UserDo::getDeletedFlag, Constant.NOT_DELETED)
            .one();
        if (ObjUtil.isNull(userDo)) {
            throw new ClientException(UserResult.USER_NOT_FOUND);
        }

        UserVo userVo = BeanUtil.copyProperties(userDo, UserVo.class);

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
            .eq(UserDo::getUsername, username)
            .exists();
        
        return isExists;
    }
    
    @Override
    public boolean isEmailExists(String email) {
        boolean isExists = lambdaQuery()
            .eq(UserDo::getEmail, email)
            .exists();
        
        return isExists;
    }
}