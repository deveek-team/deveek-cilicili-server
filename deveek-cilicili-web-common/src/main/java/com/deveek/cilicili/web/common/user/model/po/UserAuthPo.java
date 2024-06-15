package com.deveek.cilicili.web.common.user.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author harvey 
 */
@Data
@TableName("t_user_auth")
public class UserAuthPo {
    private Long id;
    
    private Long userId;
    
    private Long authId;
}
