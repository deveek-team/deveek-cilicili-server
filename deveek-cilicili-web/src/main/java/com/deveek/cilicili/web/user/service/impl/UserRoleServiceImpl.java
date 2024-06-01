package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.user.entity.domain.UserRoleDo;
import com.deveek.cilicili.web.user.mapper.UserRoleMapper;
import com.deveek.cilicili.web.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-28
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDo> implements UserRoleService {
}