package com.deveek.cilicili.web.common.user.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author harvey 
 */
@Data
@TableName("t_user_role")
public class UserRolePo implements Serializable {
    private Long id;
    
    private Long userId;
    
    private Long roleId;
    
    @Serial
    private static final long serialVersionUID = 1L;
}