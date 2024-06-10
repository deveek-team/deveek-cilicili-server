package com.deveek.database.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.deveek.security.common.service.UserContextHolder;
import jakarta.annotation.Resource;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-27
 */
public class AuditMetaObjectHandler implements MetaObjectHandler {
    @Resource
    private UserContextHolder userContextHolder;
    
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = userContextHolder.getUserId();
        
        strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, "createBy", Long.class, userId);
        strictInsertFill(metaObject, "updateBy", Long.class, userId);
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = userContextHolder.getUserId();
        
        strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        strictUpdateFill(metaObject, "updateBy", Long.class, userId);
    }
}
