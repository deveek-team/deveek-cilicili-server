package com.deveek.database.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.deveek.security.common.service.UserContextHolder;
import jakarta.annotation.Resource;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author harvey 
 */
public class AuditMetaObjectHandler implements MetaObjectHandler {
    @Resource
    private UserContextHolder userContextHolder;
    
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.getValue("createTime") == null) {
            strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        }
        
        if (metaObject.getValue("updateTime") == null) {
            strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        }
        
        if (metaObject.getValue("createBy") == null) {
            strictInsertFill(metaObject, "createBy", Long.class, userContextHolder.getUserId());
        }
        
        if (metaObject.getValue("updateBy") == null) {
            strictInsertFill(metaObject, "createBy", Long.class, userContextHolder.getUserId());
        }
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = userContextHolder.getUserId();
        
        strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        strictUpdateFill(metaObject, "updateBy", Long.class, userId);
    }
}
