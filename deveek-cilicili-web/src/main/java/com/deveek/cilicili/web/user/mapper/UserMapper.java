package com.deveek.cilicili.web.user.mapper;

import com.deveek.cilicili.web.common.user.model.po.UserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPo> {
}