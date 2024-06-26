package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.model.po.RoleAuthPo;
import com.deveek.cilicili.web.user.mapper.RoleAuthMapper;
import com.deveek.cilicili.web.user.service.RoleAuthService;
import org.springframework.stereotype.Service;

/**
 * @author harvey 
 */
@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuthPo> implements RoleAuthService {
}