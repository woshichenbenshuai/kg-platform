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
VALUES (1, '20260512.1', 'Initial kindergarten tenant schema', NOW())
ON DUPLICATE KEY UPDATE
  version = VALUES(version),
  remarks = VALUES(remarks);

CREATE TABLE IF NOT EXISTS student (
  id BIGINT(20) NOT NULL COMMENT '主键',
  student_no VARCHAR(64) NOT NULL COMMENT '学号',
  student_name VARCHAR(100) NOT NULL COMMENT '学生姓名',
  gender VARCHAR(20) DEFAULT NULL COMMENT '性别',
  birthday DATE DEFAULT NULL COMMENT '出生日期',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_student_no (student_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';
