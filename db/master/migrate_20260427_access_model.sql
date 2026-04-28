SET NAMES utf8mb4;
USE kg_platform;

CREATE TABLE IF NOT EXISTS user_role (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  user_id BIGINT(20) NOT NULL COMMENT 'User id',
  role_id BIGINT(20) NOT NULL COMMENT 'Role id',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User role relation';

SET @old_user_tenant_role_exists := (
  SELECT COUNT(*)
  FROM information_schema.tables
  WHERE table_schema = DATABASE()
    AND table_name = 'user_tenant_role'
);

SET @migrate_user_role_sql := IF(
  @old_user_tenant_role_exists > 0,
  '
  INSERT INTO user_role (
    id, user_id, role_id, status, delete_status,
    create_by, create_time, last_modified_by, last_modified_time
  )
  SELECT
    utr.id,
    sut.user_id,
    utr.role_id,
    utr.status,
    utr.delete_status,
    utr.create_by,
    utr.create_time,
    utr.last_modified_by,
    utr.last_modified_time
  FROM user_tenant_role utr
  JOIN sys_user_tenant sut ON sut.id = utr.user_tenant_id
  ON DUPLICATE KEY UPDATE
    status = VALUES(status),
    delete_status = VALUES(delete_status),
    last_modified_by = VALUES(last_modified_by),
    last_modified_time = VALUES(last_modified_time)
  ',
  'SELECT 1'
);

PREPARE migrate_user_role_stmt FROM @migrate_user_role_sql;
EXECUTE migrate_user_role_stmt;
DEALLOCATE PREPARE migrate_user_role_stmt;

DROP TABLE IF EXISTS role_permission_point;
DROP TABLE IF EXISTS sys_permission_point;
DROP TABLE IF EXISTS user_tenant_role;
