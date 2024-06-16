package com.deveek.cilicili.web.common.video.model.po;

import java.io.Serial;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author harvey
 */
@Data
@TableName("t_video_tag")
public class VideoTagPo implements Serializable {
    private Long id;

    private Long videoId;

    private Long tagId;

    @Serial
    private static final long serialVersionUID = 1L;
}