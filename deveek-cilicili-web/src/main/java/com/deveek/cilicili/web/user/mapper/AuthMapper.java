package com.deveek.cilicili.web.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deveek.cilicili.web.common.user.model.po.AuthPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author harvey 
 */
@Mapper
public interface AuthMapper extends BaseMapper<AuthPo> {
}