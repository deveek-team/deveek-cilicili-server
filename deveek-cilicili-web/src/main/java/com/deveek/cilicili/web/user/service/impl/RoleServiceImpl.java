package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.model.po.RolePo;
import com.deveek.cilicili.web.user.mapper.RoleMapper;
import com.deveek.cilicili.web.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author harvey 
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePo> implements RoleService {
}