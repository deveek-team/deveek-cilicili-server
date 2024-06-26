package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.model.po.AuthPo;
import com.deveek.cilicili.web.user.mapper.AuthMapper;
import com.deveek.cilicili.web.user.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * @author harvey 
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthPo> implements AuthService {
}