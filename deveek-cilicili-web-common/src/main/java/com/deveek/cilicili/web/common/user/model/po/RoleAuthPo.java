package com.deveek.cilicili.web.common.user.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey 
 */
@Data
@TableName("t_role_auth")
public class RoleAuthPo implements Serializable {
    private Long id;
    
    private Long roleId;
    
    private Long authId;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
