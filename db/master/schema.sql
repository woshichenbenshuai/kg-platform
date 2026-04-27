SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS kg_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE kg_platform;

CREATE TABLE IF NOT EXISTS dict_type (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  name VARCHAR(100) NOT NULL COMMENT 'Dictionary name',
  code VARCHAR(100) NOT NULL COMMENT 'Dictionary code',
  remarks VARCHAR(500) DEFAULT NULL COMMENT 'Remarks',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Dictionary type';

CREATE TABLE IF NOT EXISTS dict_value (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  type VARCHAR(100) NOT NULL COMMENT 'Dictionary type code',
  dict_type_id BIGINT(20) NOT NULL COMMENT 'Dictionary type id',
  label VARCHAR(100) NOT NULL COMMENT 'Display label',
  value VARCHAR(100) NOT NULL COMMENT 'Stored value',
  remarks VARCHAR(500) DEFAULT NULL COMMENT 'Remarks',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  KEY idx_dict_type_id (dict_type_id),
  KEY idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Dictionary value';

CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  username VARCHAR(100) NOT NULL COMMENT 'Login username',
  password VARCHAR(255) NOT NULL COMMENT 'Login password',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System user';

CREATE TABLE IF NOT EXISTS sys_menu (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  menu_code VARCHAR(100) NOT NULL COMMENT 'Menu code',
  menu_name VARCHAR(100) NOT NULL COMMENT 'Menu name',
  menu_scope VARCHAR(20) NOT NULL COMMENT 'Menu scope',
  parent_id BIGINT(20) DEFAULT NULL COMMENT 'Parent menu id',
  route_path VARCHAR(255) DEFAULT NULL COMMENT 'Route path',
  component_path VARCHAR(255) DEFAULT NULL COMMENT 'Component path',
  icon VARCHAR(100) DEFAULT NULL COMMENT 'Icon',
  visible BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Visible',
  keep_alive BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Keep alive',
  sort_no INT NOT NULL DEFAULT 0 COMMENT 'Sort number',
  remarks VARCHAR(500) DEFAULT NULL COMMENT 'Remarks',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_menu_code (menu_code),
  KEY idx_parent_id (parent_id),
  KEY idx_menu_scope (menu_scope)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System menu';

CREATE TABLE IF NOT EXISTS sys_tenant (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  tenant_code VARCHAR(100) NOT NULL COMMENT 'Tenant code',
  tenant_name VARCHAR(100) NOT NULL COMMENT 'Tenant name',
  contact_name VARCHAR(100) DEFAULT NULL COMMENT 'Contact name',
  contact_phone VARCHAR(20) DEFAULT NULL COMMENT 'Contact phone',
  address VARCHAR(255) DEFAULT NULL COMMENT 'Address',
  expire_date VARCHAR(20) DEFAULT NULL COMMENT 'Expire date',
  remarks VARCHAR(500) DEFAULT NULL COMMENT 'Remarks',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_tenant_code (tenant_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System tenant';

CREATE TABLE IF NOT EXISTS sys_user_tenant (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  user_id BIGINT(20) NOT NULL COMMENT 'User id',
  tenant_id BIGINT(20) NOT NULL COMMENT 'Tenant id',
  identity_type VARCHAR(20) NOT NULL COMMENT 'Identity type',
  default_flag BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Default tenant flag',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_tenant_identity (user_id, tenant_id, identity_type),
  KEY idx_tenant_user (tenant_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User tenant relation';

CREATE TABLE IF NOT EXISTS tenant_db_config (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  tenant_id BIGINT(20) NOT NULL COMMENT 'Tenant id',
  db_type VARCHAR(20) NOT NULL COMMENT 'Database type',
  db_host VARCHAR(128) NOT NULL COMMENT 'Database host',
  db_port INT NOT NULL COMMENT 'Database port',
  db_name VARCHAR(128) NOT NULL COMMENT 'Database name',
  db_username VARCHAR(128) NOT NULL COMMENT 'Database username',
  db_password_encrypted VARCHAR(512) NOT NULL COMMENT 'Encrypted database password',
  jdbc_params VARCHAR(500) DEFAULT NULL COMMENT 'JDBC params',
  schema_version VARCHAR(32) DEFAULT NULL COMMENT 'Tenant schema version',
  db_status VARCHAR(20) DEFAULT NULL COMMENT 'Database status',
  last_check_time DATETIME DEFAULT NULL COMMENT 'Last check time',
  last_check_result VARCHAR(500) DEFAULT NULL COMMENT 'Last check result',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tenant database config';

CREATE TABLE IF NOT EXISTS sys_role (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  role_code VARCHAR(100) NOT NULL COMMENT 'Role code',
  role_name VARCHAR(100) NOT NULL COMMENT 'Role name',
  role_scope VARCHAR(20) NOT NULL COMMENT 'Role scope',
  remarks VARCHAR(500) DEFAULT NULL COMMENT 'Remarks',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='System role';

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

CREATE TABLE IF NOT EXISTS role_menu (
  id BIGINT(20) NOT NULL COMMENT 'Primary key',
  role_id BIGINT(20) NOT NULL COMMENT 'Role id',
  menu_id BIGINT(20) NOT NULL COMMENT 'Menu id',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT 'Status',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Logical delete',
  create_by VARCHAR(64) DEFAULT NULL COMMENT 'Created by',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Created time',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT 'Last modified by',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Last modified time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_menu (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Role menu relation';
