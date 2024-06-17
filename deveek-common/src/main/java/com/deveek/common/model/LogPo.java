package com.deveek.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author banne
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @TableName("t_log")
public class LogPo implements Serializable {
    /**
     * id
     */
    // @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日志级别
     */
    private String type;

    /**
     * 状态码
     */
    private String code;

    /**
     * 请求方法名
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestArgs;

    /**
     * 打印信息( 各种级别下 )
     */
    private String message;

    /**
     * 创建人员（操作人员） createdBy
     */
    private String createUser;

    /**
     * 全类名
     */
    private String fullClassName;

    /**
     * ip 地址
     */
    private String requestIp;

    /**
     * 请求路径
     */
    private String requestUri;

    /**
     * 请求端口号
     */
    private Long requestPort;

    /**
     * 请求类型 (post/get/...)
     */
    private String requestType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    // @TableLogic(value = "0", delval = "1")
    private int deleteFlag;
}
