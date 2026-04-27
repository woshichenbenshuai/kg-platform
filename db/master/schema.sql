SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS kg_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE kg_platform;

CREATE TABLE IF NOT EXISTS dict_type (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  name VARCHAR(100) NOT NULL COMMENT '字典名称',
  code VARCHAR(100) NOT NULL COMMENT '字典编码',
  remarks VARCHAR(500) DEFAULT NULL COMMENT '备注',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

CREATE TABLE IF NOT EXISTS dict_value (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  type VARCHAR(100) NOT NULL COMMENT '字典类型编码',
  dict_type_id BIGINT(20) NOT NULL COMMENT '字典类型ID',
  label VARCHAR(100) NOT NULL COMMENT '字典标签',
  value VARCHAR(100) NOT NULL COMMENT '字典值',
  remarks VARCHAR(500) DEFAULT NULL COMMENT '备注',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  KEY idx_dict_type_id (dict_type_id),
  KEY idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典值表';

CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  username VARCHAR(100) NOT NULL COMMENT '登录账号',
  password VARCHAR(255) NOT NULL COMMENT '登录密码',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS sys_menu (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  menu_code VARCHAR(100) NOT NULL COMMENT '菜单编码',
  menu_name VARCHAR(100) NOT NULL COMMENT '菜单名称',
  menu_scope VARCHAR(20) NOT NULL COMMENT '菜单范围',
  parent_id BIGINT(20) DEFAULT NULL COMMENT '父级菜单ID',
  route_path VARCHAR(255) DEFAULT NULL COMMENT '路由路径',
  component_path VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
  icon VARCHAR(100) DEFAULT NULL COMMENT '图标',
  visible BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否显示',
  keep_alive BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否缓存',
  sort_no INT NOT NULL DEFAULT 0 COMMENT '排序',
  remarks VARCHAR(500) DEFAULT NULL COMMENT '备注',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_menu_code (menu_code),
  KEY idx_parent_id (parent_id),
  KEY idx_menu_scope (menu_scope)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

CREATE TABLE IF NOT EXISTS sys_tenant (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  tenant_code VARCHAR(100) NOT NULL COMMENT '租户编码',
  tenant_name VARCHAR(100) NOT NULL COMMENT '租户名称',
  contact_name VARCHAR(100) DEFAULT NULL COMMENT '联系人',
  contact_phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  address VARCHAR(255) DEFAULT NULL COMMENT '地址',
  expire_date VARCHAR(20) DEFAULT NULL COMMENT '到期日期',
  remarks VARCHAR(500) DEFAULT NULL COMMENT '备注',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_tenant_code (tenant_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统租户表';

CREATE TABLE IF NOT EXISTS sys_user_tenant (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  user_id BIGINT(20) NOT NULL COMMENT '用户ID',
  tenant_id BIGINT(20) NOT NULL COMMENT '租户ID',
  identity_type VARCHAR(20) NOT NULL COMMENT '身份类型',
  default_flag BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否默认租户',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_tenant_identity (user_id, tenant_id, identity_type),
  KEY idx_tenant_user (tenant_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户租户关系表';

CREATE TABLE IF NOT EXISTS tenant_db_config (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  tenant_id BIGINT(20) NOT NULL COMMENT '租户ID',
  db_type VARCHAR(20) NOT NULL COMMENT '数据库类型',
  db_host VARCHAR(128) NOT NULL COMMENT '数据库主机',
  db_port INT NOT NULL COMMENT '数据库端口',
  db_name VARCHAR(128) NOT NULL COMMENT '数据库名称',
  db_username VARCHAR(128) NOT NULL COMMENT '数据库用户名',
  db_password_encrypted VARCHAR(512) NOT NULL COMMENT '加密密码',
  jdbc_params VARCHAR(500) DEFAULT NULL COMMENT 'JDBC参数',
  schema_version VARCHAR(32) DEFAULT NULL COMMENT '子库版本',
  db_status VARCHAR(20) DEFAULT NULL COMMENT '数据库状态',
  last_check_time DATETIME DEFAULT NULL COMMENT '最近检查时间',
  last_check_result VARCHAR(500) DEFAULT NULL COMMENT '最近检查结果',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_tenant_id (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户数据库配置表';

CREATE TABLE IF NOT EXISTS sys_permission_point (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  permission_code VARCHAR(100) NOT NULL COMMENT '权限编码',
  permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
  permission_type VARCHAR(20) NOT NULL COMMENT '权限类型',
  permission_scope VARCHAR(20) NOT NULL COMMENT '权限范围',
  bind_menu_id BIGINT(20) DEFAULT NULL COMMENT '绑定菜单ID',
  api_path VARCHAR(255) DEFAULT NULL COMMENT 'API路径',
  api_method VARCHAR(20) DEFAULT NULL COMMENT 'API请求方法',
  remarks VARCHAR(500) DEFAULT NULL COMMENT '备注',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限点表';

CREATE TABLE IF NOT EXISTS role_permission_point (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  role_id BIGINT(20) NOT NULL COMMENT '角色ID',
  permission_point_id BIGINT(20) NOT NULL COMMENT '权限点ID',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_permission_point (role_id, permission_point_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限点关系表';

CREATE TABLE IF NOT EXISTS role_menu (
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  role_id BIGINT(20) NOT NULL COMMENT '角色ID',
  menu_id BIGINT(20) NOT NULL COMMENT '菜单ID',
  status BIT(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  delete_status BIT(1) NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  last_modified_by VARCHAR(64) DEFAULT NULL COMMENT '最后修改人',
  last_modified_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_menu (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关系表';
