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
VALUES (1, '20260513.1', 'Kindergarten parent portal core modules', NOW())
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

CREATE TABLE IF NOT EXISTS student (
  id BIGINT(20) NOT NULL COMMENT '主键',
  class_id BIGINT(20) DEFAULT NULL COMMENT '班级ID',
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
  UNIQUE KEY uk_student_no (student_no),
  KEY idx_student_class (class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

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

CREATE TABLE IF NOT EXISTS kindergarten_notice (
  id BIGINT(20) NOT NULL COMMENT '主键',
  title VARCHAR(150) NOT NULL COMMENT '标题',
  content TEXT NOT NULL COMMENT '内容',
  publish_time DATETIME DEFAULT NULL COMMENT '发布时间',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY idx_notice_publish_time (publish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='园所通知表';

CREATE TABLE IF NOT EXISTS daily_recipe (
  id BIGINT(20) NOT NULL COMMENT '主键',
  recipe_date DATE NOT NULL COMMENT '食谱日期',
  meal_type VARCHAR(30) NOT NULL COMMENT '餐次',
  content VARCHAR(500) NOT NULL COMMENT '食谱内容',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY idx_recipe_date (recipe_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日食谱表';

CREATE TABLE IF NOT EXISTS leave_request (
  id BIGINT(20) NOT NULL COMMENT '主键',
  student_id BIGINT(20) NOT NULL COMMENT '学生ID',
  guardian_id BIGINT(20) NOT NULL COMMENT '家长ID',
  start_date DATE NOT NULL COMMENT '开始日期',
  end_date DATE NOT NULL COMMENT '结束日期',
  reason VARCHAR(500) NOT NULL COMMENT '请假原因',
  approve_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '审核状态',
  approve_remark VARCHAR(500) DEFAULT NULL COMMENT '审核备注',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY idx_leave_student (student_id),
  KEY idx_leave_guardian (guardian_id),
  KEY idx_leave_status (approve_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假申请表';

CREATE TABLE IF NOT EXISTS growth_record (
  id BIGINT(20) NOT NULL COMMENT '主键',
  student_id BIGINT(20) NOT NULL COMMENT '学生ID',
  title VARCHAR(150) NOT NULL COMMENT '标题',
  content TEXT NOT NULL COMMENT '内容',
  record_date DATE NOT NULL COMMENT '记录日期',
  image_urls VARCHAR(1000) DEFAULT NULL COMMENT '图片地址',
  visible_to_parent TINYINT(1) NOT NULL DEFAULT 1 COMMENT '家长可见',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  KEY idx_growth_student_date (student_id, record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长记录表';
