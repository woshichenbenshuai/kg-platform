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
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  class_code VARCHAR(64) NOT NULL COMMENT 'Class code',
  class_name VARCHAR(100) NOT NULL COMMENT 'Class name',
  grade_name VARCHAR(100) DEFAULT NULL COMMENT 'Grade name',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_class_code (class_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Kindergarten class';

CREATE TABLE IF NOT EXISTS student (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  class_id BIGINT(20) DEFAULT NULL COMMENT 'Class id',
  student_no VARCHAR(64) NOT NULL COMMENT 'Student number',
  student_name VARCHAR(100) NOT NULL COMMENT 'Student name',
  gender VARCHAR(20) DEFAULT NULL COMMENT 'Gender',
  birthday DATE DEFAULT NULL COMMENT 'Birthday',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_student_no (student_no),
  KEY idx_student_class (class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Student';

CREATE TABLE IF NOT EXISTS guardian (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  user_id BIGINT(20) DEFAULT NULL COMMENT 'Platform user id',
  guardian_name VARCHAR(100) NOT NULL COMMENT 'Guardian name',
  phone VARCHAR(32) DEFAULT NULL COMMENT 'Phone number',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_guardian_user (user_id),
  KEY idx_guardian_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Guardian';

CREATE TABLE IF NOT EXISTS student_guardian_relation (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  student_id BIGINT(20) NOT NULL COMMENT 'Student id',
  guardian_id BIGINT(20) NOT NULL COMMENT 'Guardian id',
  relation_type VARCHAR(30) NOT NULL COMMENT 'Relation type',
  primary_contact TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Primary contact',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_student_guardian (student_id, guardian_id),
  KEY idx_relation_guardian (guardian_id),
  KEY idx_relation_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Student guardian relation';

CREATE TABLE IF NOT EXISTS kindergarten_notice (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  title VARCHAR(150) NOT NULL COMMENT 'Title',
  content TEXT NOT NULL COMMENT 'Content',
  publish_time DATETIME DEFAULT NULL COMMENT 'Publish time',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  KEY idx_notice_publish_time (publish_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Kindergarten notice';

CREATE TABLE IF NOT EXISTS daily_recipe (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  recipe_date DATE NOT NULL COMMENT 'Recipe date',
  meal_type VARCHAR(30) NOT NULL COMMENT 'Meal type',
  content VARCHAR(500) NOT NULL COMMENT 'Content',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  KEY idx_recipe_date (recipe_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Daily recipe';

CREATE TABLE IF NOT EXISTS leave_request (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  student_id BIGINT(20) NOT NULL COMMENT 'Student id',
  guardian_id BIGINT(20) NOT NULL COMMENT 'Guardian id',
  start_date DATE NOT NULL COMMENT 'Start date',
  end_date DATE NOT NULL COMMENT 'End date',
  reason VARCHAR(500) NOT NULL COMMENT 'Leave reason',
  approve_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'Approval status',
  approve_remark VARCHAR(500) DEFAULT NULL COMMENT 'Approval remark',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  KEY idx_leave_student (student_id),
  KEY idx_leave_guardian (guardian_id),
  KEY idx_leave_status (approve_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Leave request';

CREATE TABLE IF NOT EXISTS growth_record (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  student_id BIGINT(20) NOT NULL COMMENT 'Student id',
  title VARCHAR(150) NOT NULL COMMENT 'Title',
  content TEXT NOT NULL COMMENT 'Content',
  record_date DATE NOT NULL COMMENT 'Record date',
  image_urls VARCHAR(1000) DEFAULT NULL COMMENT 'Image urls',
  visible_to_parent TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Visible to parent',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  KEY idx_growth_student_date (student_id, record_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Growth record';
