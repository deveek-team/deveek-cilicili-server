package com.deveek.cilicili.web.common.video.model.po;

import java.io.Serial;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author harvey
 */
@Data
@TableName("t_tag")
public class TagPo implements Serializable {
    private Long id;

    private String name;

    @Serial
    private static final long serialVersionUID = 1L;
}