package com.deveek.cilicili.web.common.media.constant;

/**
 * 媒体 Redis 常量类
 *
 * @author Shooter
 */
public final class MediaRedisConstant {

    /**
     * 断点续传缓存 Key
     */
    public static final String MEDIA_USER_KEY = "deveek-cilicili_media:user:%s:%s";

    /**
     * 文件删除延迟队列
     */
    public static final String FILE_BLOCKING_DEQUE = "FILE_DELETE_QUEUE";

}
