SET NAMES utf8mb4;

INSERT INTO schema_version (id, version, remarks, create_time)
VALUES (1, '20260513.1', 'Kindergarten parent portal core modules', NOW())
ON DUPLICATE KEY UPDATE
  version = VALUES(version),
  remarks = VALUES(remarks);

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
