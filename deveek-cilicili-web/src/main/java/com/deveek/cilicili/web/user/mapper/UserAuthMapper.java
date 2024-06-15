package com.deveek.cilicili.web.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deveek.cilicili.web.common.user.model.po.UserAuthPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author harvey 
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuthPo> {
}
