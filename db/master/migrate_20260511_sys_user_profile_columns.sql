SET NAMES utf8mb4;
USE kg_platform;

SET @sys_user_nickname_exists := (
  SELECT COUNT(*)
  FROM information_schema.columns
  WHERE table_schema = DATABASE()
    AND table_name = 'sys_user'
    AND column_name = 'nickname'
);

SET @add_sys_user_nickname_sql := IF(
  @sys_user_nickname_exists = 0,
  'ALTER TABLE sys_user ADD COLUMN nickname VARCHAR(100) DEFAULT NULL COMMENT ''Display nickname'' AFTER username',
  'SELECT 1'
);

PREPARE add_sys_user_nickname_stmt FROM @add_sys_user_nickname_sql;
EXECUTE add_sys_user_nickname_stmt;
DEALLOCATE PREPARE add_sys_user_nickname_stmt;

SET @sys_user_phone_exists := (
  SELECT COUNT(*)
  FROM information_schema.columns
  WHERE table_schema = DATABASE()
    AND table_name = 'sys_user'
    AND column_name = 'phone'
);

SET @add_sys_user_phone_sql := IF(
  @sys_user_phone_exists = 0,
  'ALTER TABLE sys_user ADD COLUMN phone VARCHAR(20) DEFAULT NULL COMMENT ''Phone number'' AFTER nickname',
  'SELECT 1'
);

PREPARE add_sys_user_phone_stmt FROM @add_sys_user_phone_sql;
EXECUTE add_sys_user_phone_stmt;
DEALLOCATE PREPARE add_sys_user_phone_stmt;
