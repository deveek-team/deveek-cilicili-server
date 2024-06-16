package com.deveek.cilicili.web.common.video.model.po;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author harvey
 */
@Data
@TableName("t_video")
public class VideoPo implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private String description;

    private String thumbnailUrl;

    private String videoUrl;

    private Long viewCount;

    private Long likeCount;
    
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @Serial
    private static final long serialVersionUID = 1L;
}