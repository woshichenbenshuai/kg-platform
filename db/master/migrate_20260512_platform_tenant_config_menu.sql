SET NAMES utf8mb4;
USE kg_platform;

INSERT INTO sys_menu (
  id, menu_code, menu_name, menu_scope, parent_id, route_path, component_path, icon, visible, keep_alive, sort_no, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(8, 'PLATFORM_TENANT_CONFIG', '幼儿园配置', 'PLATFORM', 1, '/platform/tenants', 'views/platform/TenantConfigView.vue', 'OfficeBuilding', b'1', b'1', 12, '平台幼儿园与租户数据库配置', b'1', b'0', 'system', NOW(), 'system', NOW())
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

INSERT INTO role_menu (
  id, role_id, menu_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(8, 1, 8, b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);
