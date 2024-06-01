package com.deveek.cilicili.web.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deveek.cilicili.web.user.entity.domain.AuthDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-28
 */
@Mapper
public interface AuthMapper extends BaseMapper<AuthDo> {
}