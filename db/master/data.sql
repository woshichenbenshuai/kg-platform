SET NAMES utf8mb4;
USE kg_platform;

INSERT INTO dict_type (
  id, name, code, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, '用户状态', 'user_status', '用户启用状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(2, '通用状态', 'common_status', '通用启停状态', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  name = VALUES(name), remarks = VALUES(remarks), status = VALUES(status), delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);

INSERT INTO dict_value (
  id, type, dict_type_id, label, value, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'user_status', 1, '启用', '1', '启用状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(2, 'user_status', 1, '禁用', '0', '禁用状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(3, 'common_status', 2, '开启', '1', '开启状态', 1, b'0', 'system', NOW(), 'system', NOW()),
(4, 'common_status', 2, '关闭', '0', '关闭状态', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  type = VALUES(type), dict_type_id = VALUES(dict_type_id), label = VALUES(label), value = VALUES(value), remarks = VALUES(remarks),
  status = VALUES(status), delete_status = VALUES(delete_status), last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_user (
  id, username, nickname, password, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'admin', '平台管理员', '$2b$10$gRwApN1uS2fYcTWQ84PchOirv0A7ZD4Y9ybSnldeW1BaJZlrTO2LG', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  nickname = VALUES(nickname),
  password = VALUES(password), status = VALUES(status), delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_menu (
  id, menu_code, menu_name, menu_scope, parent_id, route_path, component_path, icon, visible, keep_alive, sort_no, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'PLATFORM_SYSTEM', '平台系统', 'PLATFORM', NULL, '/platform', 'layouts/PlatformLayout.vue', 'Setting', b'1', b'1', 10, '平台系统根菜单', b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 'PLATFORM_MENU_MGMT', '菜单管理', 'PLATFORM', 1, '/platform/menu', 'views/platform/PlatformMenuView.vue', 'Menu', b'1', b'1', 14, '平台菜单管理', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  menu_name = VALUES(menu_name), menu_scope = VALUES(menu_scope), parent_id = VALUES(parent_id), route_path = VALUES(route_path), component_path = VALUES(component_path),
  icon = VALUES(icon), visible = VALUES(visible), keep_alive = VALUES(keep_alive), sort_no = VALUES(sort_no), remarks = VALUES(remarks),
  status = VALUES(status), delete_status = VALUES(delete_status), last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_role (
  id, role_code, role_name, role_scope, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'PLATFORM_ADMIN', '平台管理员', 'PLATFORM', '平台管理端管理员', b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 'KINDER_ADMIN', '园长', 'KINDER', '园所管理端管理员', b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 'TEACHER_PORTAL', '老师', 'KINDER', '老师业务身份入口', b'1', b'0', 'system', NOW(), 'system', NOW()),
(4, 'PARENT_PORTAL', '家长', 'KINDER', '家长业务身份入口', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  role_name = VALUES(role_name), role_scope = VALUES(role_scope), remarks = VALUES(remarks), status = VALUES(status), delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);

INSERT INTO user_role (
  id, user_id, role_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 1, 1, b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  status = VALUES(status), delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);

INSERT INTO role_menu (
  id, role_id, menu_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 1, 1, b'1', b'0', 'system', NOW(), 'system', NOW()),
(2, 1, 2, b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  status = VALUES(status), delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by), last_modified_time = VALUES(last_modified_time);
