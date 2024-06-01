package com.deveek.cilicili.web.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.user.entity.domain.UserAuthDo;
import com.deveek.cilicili.web.user.mapper.UserAuthMapper;
import com.deveek.cilicili.web.user.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-28
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuthDo> implements UserAuthService {
}