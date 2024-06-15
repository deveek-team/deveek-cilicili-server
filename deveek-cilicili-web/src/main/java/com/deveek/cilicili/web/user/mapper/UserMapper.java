package com.deveek.cilicili.web.user.mapper;

import com.deveek.cilicili.web.common.user.model.po.UserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author harvey 
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPo> {
}