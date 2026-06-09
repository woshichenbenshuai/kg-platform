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
  nickname VARCHAR(100) DEFAULT NULL COMMENT 'Display nickname',
  phone VARCHAR(20) DEFAULT NULL COMMENT 'Phone number',
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
  db_host VARCHAR(128) NOT NULL COMMENT 'Database host',
  db_port INT NOT NULL COMMENT 'Database port',
  db_name VARCHAR(128) NOT NULL COMMENT 'Database name',
  db_username VARCHAR(128) NOT NULL COMMENT 'Database username',
  db_password VARCHAR(512) NOT NULL COMMENT 'Database password',
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

INSERT INTO dict_type (
  id, name, code, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, '用户状态', 'user_status', '用户启用状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(2, '通用状态', 'common_status', '通用启停状态', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  remarks = VALUES(remarks),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO dict_value (
  id, type, dict_type_id, label, value, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'user_status', 1, '启用', '1', '启用状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(2, 'user_status', 1, '禁用', '0', '禁用状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(3, 'common_status', 2, '开启', '1', '开启状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(4, 'common_status', 2, '关闭', '0', '关闭状态', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  type = VALUES(type),
  dict_type_id = VALUES(dict_type_id),
  label = VALUES(label),
  value = VALUES(value),
  remarks = VALUES(remarks),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_user (
  id, username, nickname, phone, password, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'admin', '平台管理员', NULL, '$2b$10$gRwApN1uS2fYcTWQ84PchOirv0A7ZD4Y9ybSnldeW1BaJZlrTO2LG', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  nickname = VALUES(nickname),
  phone = VALUES(phone),
  password = VALUES(password),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_menu (
  id, menu_code, menu_name, menu_scope, parent_id, route_path, component_path, icon, visible, keep_alive, sort_no, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'PLATFORM_SYSTEM', '平台系统', 'PLATFORM', NULL, '/platform', 'layouts/PlatformLayout.vue', 'Setting', b'1', b'1', 10, '平台系统根菜单', b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 'PLATFORM_MENU_MGMT', '菜单管理', 'PLATFORM', 1, '/platform/menu', 'views/platform/PlatformMenuView.vue', 'Menu', b'1', b'1', 14, '平台菜单管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 'SYSTEM_MGR', '系统管理', 'PLATFORM', NULL, '/system', NULL, 'Setting', b'1', b'1', 0, '系统管理根菜单', b'1', b'0', 'system', NOW(), 'system', NOW()),
(4, 'USER_MGR', '用户管理', 'PLATFORM', 3, '/system/users', NULL, 'User', b'1', b'1', 0, '用户管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(5, 'MENU_MGR', '菜单管理', 'PLATFORM', 3, '/system/menus', NULL, 'Menu', b'1', b'1', 0, '菜单管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(6, 'DICT_MGR', '字典管理', 'PLATFORM', 3, '/system/dicts', NULL, 'Notebook', b'1', b'1', 0, '字典管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(7, 'ROLE_MENU_MGR', '角色权限', 'PLATFORM', 3, '/system/role-menus', NULL, 'Lock', b'1', b'1', 4, '角色权限', b'1', b'0', 'system', NOW(), 'system', NOW()),
(8, 'PLATFORM_TENANT_CONFIG', '幼儿园配置', 'PLATFORM', 1, '/platform/tenants', 'views/platform/TenantConfigView.vue', 'OfficeBuilding', b'1', b'1', 12, '平台幼儿园与租户数据库配置', b'1', b'0', 'system', NOW(), 'system', NOW()),
(20, 'KINDER_BUSINESS', '园所业务', 'KINDER', NULL, '/kinder', NULL, 'School', b'1', b'1', 20, '园所业务根菜单', b'1', b'0', 'system', NOW(), 'system', NOW()),
(21, 'KINDER_TEACHER_MGMT', '教师管理', 'KINDER', 20, '/kinder/teachers', NULL, 'UserFilled', b'1', b'1', 21, '园所教师档案管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(22, 'KINDER_CLASS_MGMT', '班级管理', 'KINDER', 20, '/kinder/classes', NULL, 'Collection', b'1', b'1', 22, '园所班级管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(23, 'KINDER_STUDENT_MGMT', '学生管理', 'KINDER', 20, '/kinder/students', NULL, 'User', b'1', b'1', 23, '园所学生档案管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(24, 'KINDER_GUARDIAN_MGMT', '家长管理', 'KINDER', 20, '/kinder/guardians', NULL, 'Avatar', b'1', b'1', 24, '园所家长档案管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(25, 'KINDER_STUDENT_GUARDIAN_MGMT', '学生家长绑定', 'KINDER', 20, '/kinder/student-guardian-relations', NULL, 'Link', b'1', b'1', 25, '学生与家长关系管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(26, 'KINDER_NOTICE_MGMT', '通知管理', 'KINDER', 20, '/kinder/notices', NULL, 'Bell', b'1', b'1', 26, '园所通知管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(27, 'KINDER_RECIPE_MGMT', '食谱管理', 'KINDER', 20, '/kinder/recipes', NULL, 'Dish', b'1', b'1', 27, '园所每日食谱管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(28, 'KINDER_GROWTH_RECORD_MGMT', '成长记录', 'KINDER', 20, '/kinder/growth-records', NULL, 'Notebook', b'1', b'1', 28, '学生成长记录管理', b'1', b'0', 'system', NOW(), 'system', NOW()),
(29, 'KINDER_LEAVE_REQUEST_MGMT', '请假审批', 'KINDER', 20, '/kinder/leave-requests', NULL, 'Tickets', b'1', b'1', 29, '家长请假审批', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name),
  menu_scope = VALUES(menu_scope),
  parent_id = VALUES(parent_id),
  route_path = VALUES(route_path),
  component_path = VALUES(component_path),
  icon = VALUES(icon),
  visible = VALUES(visible),
  keep_alive = VALUES(keep_alive),
  sort_no = VALUES(sort_no),
  remarks = VALUES(remarks),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_role (
  id, role_code, role_name, role_scope, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'PLATFORM_ADMIN', '平台管理员', 'PLATFORM', '平台管理端管理员', b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 'OPERATOR', '园长', 'KINDER', '园所管理端管理员', b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 'TEACHER_PORTAL', '老师', 'KINDER', '老师业务身份入口', b'1', b'0', 'system', NOW(), 'system', NOW()),
(4, 'PARENT_PORTAL', '家长', 'KINDER', '家长业务身份入口', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  role_name = VALUES(role_name),
  role_scope = VALUES(role_scope),
  remarks = VALUES(remarks),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO user_role (
  id, user_id, role_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 1, 1, b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO role_menu (
  id, role_id, menu_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 1, 1, b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 1, 2, b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 1, 3, b'1', b'0', 'system', NOW(), 'system', NOW()),
(4, 1, 4, b'1', b'0', 'system', NOW(), 'system', NOW()),
(5, 1, 5, b'1', b'0', 'system', NOW(), 'system', NOW()),
(6, 1, 6, b'1', b'0', 'system', NOW(), 'system', NOW()),
(7, 1, 7, b'1', b'0', 'system', NOW(), 'system', NOW()),
(8, 1, 8, b'1', b'0', 'system', NOW(), 'system', NOW()),
(20, 2, 20, b'1', b'0', 'system', NOW(), 'system', NOW()),
(21, 2, 21, b'1', b'0', 'system', NOW(), 'system', NOW()),
(22, 2, 22, b'1', b'0', 'system', NOW(), 'system', NOW()),
(23, 2, 23, b'1', b'0', 'system', NOW(), 'system', NOW()),
(24, 2, 24, b'1', b'0', 'system', NOW(), 'system', NOW()),
(25, 2, 25, b'1', b'0', 'system', NOW(), 'system', NOW()),
(26, 2, 26, b'1', b'0', 'system', NOW(), 'system', NOW()),
(27, 2, 27, b'1', b'0', 'system', NOW(), 'system', NOW()),
(28, 2, 28, b'1', b'0', 'system', NOW(), 'system', NOW()),
(29, 2, 29, b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);
