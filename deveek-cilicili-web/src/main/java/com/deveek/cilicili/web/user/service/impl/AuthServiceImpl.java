package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.user.entity.domain.AuthDo;
import com.deveek.cilicili.web.user.mapper.AuthMapper;
import com.deveek.cilicili.web.user.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-28
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthDo> implements AuthService {
}