package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.user.entity.domain.RoleDo;
import com.deveek.cilicili.web.user.mapper.RoleMapper;
import com.deveek.cilicili.web.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDo> implements RoleService {
}