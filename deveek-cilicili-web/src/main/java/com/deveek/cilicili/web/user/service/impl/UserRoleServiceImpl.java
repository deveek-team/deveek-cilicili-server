package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.model.po.UserRolePo;
import com.deveek.cilicili.web.user.mapper.UserRoleMapper;
import com.deveek.cilicili.web.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author harvey 
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRolePo> implements UserRoleService {
}