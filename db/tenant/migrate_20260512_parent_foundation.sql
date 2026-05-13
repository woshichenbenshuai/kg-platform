SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS schema_version (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  version VARCHAR(32) NOT NULL COMMENT 'Schema version',
  remarks VARCHAR(500) DEFAULT NULL COMMENT 'Remarks',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_version (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Schema version';

INSERT INTO schema_version (id, version, remarks, create_time)
VALUES (1, '20260512.2', 'Kindergarten parent portal foundation', NOW())
ON DUPLICATE KEY UPDATE
  version = VALUES(version),
  remarks = VALUES(remarks);

CREATE TABLE IF NOT EXISTS kindergarten_class (
  id BIGINT(20) NOT NULL COMMENT '主键',
  class_code VARCHAR(64) NOT NULL COMMENT '班级编码',
  class_name VARCHAR(100) NOT NULL COMMENT '班级名称',
  grade_name VARCHAR(100) DEFAULT NULL COMMENT '年级名称',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_class_code (class_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

SET @column_exists := (
  SELECT COUNT(1)
  FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'student'
    AND COLUMN_NAME = 'class_id'
);
SET @ddl := IF(@column_exists = 0,
  'ALTER TABLE student ADD COLUMN class_id BIGINT(20) DEFAULT NULL COMMENT ''班级ID'' AFTER id',
  'SELECT 1'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @index_exists := (
  SELECT COUNT(1)
  FROM information_schema.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'student'
    AND INDEX_NAME = 'idx_student_class'
);
SET @ddl := IF(@index_exists = 0,
  'ALTER TABLE student ADD KEY idx_student_class (class_id)',
  'SELECT 1'
);
PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

CREATE TABLE IF NOT EXISTS guardian (
  id BIGINT(20) NOT NULL COMMENT '主键',
  user_id BIGINT(20) DEFAULT NULL COMMENT '平台用户ID',
  guardian_name VARCHAR(100) NOT NULL COMMENT '家长姓名',
  phone VARCHAR(32) DEFAULT NULL COMMENT '手机号',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_guardian_user (user_id),
  KEY idx_guardian_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家长表';

CREATE TABLE IF NOT EXISTS student_guardian_relation (
  id BIGINT(20) NOT NULL COMMENT '主键',
  student_id BIGINT(20) NOT NULL COMMENT '学生ID',
  guardian_id BIGINT(20) NOT NULL COMMENT '家长ID',
  relation_type VARCHAR(30) NOT NULL COMMENT '关系类型',
  primary_contact TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否主联系人',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_student_guardian (student_id, guardian_id),
  KEY idx_relation_guardian (guardian_id),
  KEY idx_relation_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生家长关系表';
