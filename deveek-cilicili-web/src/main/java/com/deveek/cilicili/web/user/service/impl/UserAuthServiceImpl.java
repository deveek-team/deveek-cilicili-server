package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.user.model.po.UserAuthPo;
import com.deveek.cilicili.web.user.mapper.UserAuthMapper;
import com.deveek.cilicili.web.user.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @author harvey 
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuthPo> implements UserAuthService {
}