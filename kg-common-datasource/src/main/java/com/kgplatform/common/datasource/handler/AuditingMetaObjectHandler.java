package com.kgplatform.common.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 审计字段填充
 * <p>
 * AuditingMetaObjectHandler业务类
 *
 * @author kg_chen
 * @since 2026-04-22 18:50:54
 */
@Component
public class AuditingMetaObjectHandler implements MetaObjectHandler {

    private static final String DEFAULT_OPERATOR = "system";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "creator", String.class, DEFAULT_OPERATOR);
        this.strictInsertFill(metaObject, "updater", String.class, DEFAULT_OPERATOR);
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updater", String.class, DEFAULT_OPERATOR);
    }
}