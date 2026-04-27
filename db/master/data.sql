SET NAMES utf8mb4;
USE kg_platform;

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
  id, username, password, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
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
(2, 'PLATFORM_MENU_MGMT', '菜单管理', 'PLATFORM', 1, '/platform/menu', 'views/platform/PlatformMenuView.vue', 'Menu', b'1', b'1', 14, '平台菜单管理', b'1', b'0', 'system', NOW(), 'system', NOW())
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

INSERT INTO sys_tenant (
  id, tenant_code, tenant_name, contact_name, contact_phone, address, expire_date, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'TENANT_DEMO', '演示园所', '张老师', '13800000000', '山东省济南市', '2027-12-31', '默认演示租户', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  tenant_name = VALUES(tenant_name),
  contact_name = VALUES(contact_name),
  contact_phone = VALUES(contact_phone),
  address = VALUES(address),
  expire_date = VALUES(expire_date),
  remarks = VALUES(remarks),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_user_tenant (
  id, user_id, tenant_id, identity_type, default_flag, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 1, 1, 'PLATFORM', b'1', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  default_flag = VALUES(default_flag),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO tenant_db_config (
  id, tenant_id, db_type, db_host, db_port, db_name, db_username, db_password_encrypted,
  jdbc_params, schema_version, db_status, last_check_time, last_check_result, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 1, 'mysql', '127.0.0.1', 3306, 'kg_platform', 'root', 'ENC(root)', 'useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai', NULL, 'NORMAL', NOW(), '初始化导入', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  db_type = VALUES(db_type),
  db_host = VALUES(db_host),
  db_port = VALUES(db_port),
  db_name = VALUES(db_name),
  db_username = VALUES(db_username),
  db_password_encrypted = VALUES(db_password_encrypted),
  jdbc_params = VALUES(jdbc_params),
  schema_version = VALUES(schema_version),
  db_status = VALUES(db_status),
  last_check_time = VALUES(last_check_time),
  last_check_result = VALUES(last_check_result),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_permission_point (
  id, permission_code, permission_name, permission_type, permission_scope,
  bind_menu_id, api_path, api_method, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'PLATFORM_USER_ADD', '用户新增按钮', 'BUTTON', 'PLATFORM', 2, NULL, NULL, '平台用户新增按钮权限点', b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 'PLATFORM_USER_QUERY_API', '用户分页查询接口', 'API', 'PLATFORM', NULL, '/users/pages', 'GET', '平台用户分页查询接口权限点', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  permission_name = VALUES(permission_name),
  permission_type = VALUES(permission_type),
  permission_scope = VALUES(permission_scope),
  bind_menu_id = VALUES(bind_menu_id),
  api_path = VALUES(api_path),
  api_method = VALUES(api_method),
  remarks = VALUES(remarks),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

-- user_tenant_role 第一版暂不插入正式关系数据。
-- 待 sys_role 标准表落地后，再补充用户租户角色绑定初始化数据。

-- role_permission_point 第一版暂不插入正式关系数据。
-- 待权限点批量授权阶段后，再补充角色权限点授权初始化数据。

-- role_menu 第一版暂不插入正式关系数据。
-- 待权限模块进入批量授权阶段后，再补充角色菜单授权初始化数据。
