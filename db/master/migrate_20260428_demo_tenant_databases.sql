SET NAMES utf8mb4;
USE kg_platform;

INSERT INTO sys_tenant (
  id, tenant_code, tenant_name, contact_name, contact_phone, address, expire_date, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(2, 'KINDER_DEMO_001', '演示园所001', '园长001', '13800000001', '本地演示园所001', '2027-12-31', 'demo tenant database kg_kinder_demo_001', b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 'KINDER_DEMO_002', '演示园所002', '园长002', '13800000002', '本地演示园所002', '2027-12-31', 'demo tenant database kg_kinder_demo_002', b'1', b'0', 'system', NOW(), 'system', NOW())
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

INSERT INTO tenant_db_config (
  id, tenant_id, db_type, db_host, db_port, db_name, db_username, db_password_encrypted,
  jdbc_params, schema_version, db_status, last_check_time, last_check_result, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(2, 2, 'mysql', '127.0.0.1', 3306, 'kg_kinder_demo_001', 'root', 'ENC(root)', 'useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai', NULL, 'NORMAL', NOW(), 'manual local tenant database attach', b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 3, 'mysql', '127.0.0.1', 3306, 'kg_kinder_demo_002', 'root', 'ENC(root)', 'useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai', NULL, 'NORMAL', NOW(), 'manual local tenant database attach', b'1', b'0', 'system', NOW(), 'system', NOW())
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

INSERT INTO sys_user (
  id, username, nickname, password, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(2, 'demo001_admin', '园所管理员001', '$2b$10$gRwApN1uS2fYcTWQ84PchOirv0A7ZD4Y9ybSnldeW1BaJZlrTO2LG', 1, b'0', 'system', NOW(), 'system', NOW()),
(3, 'demo002_admin', '园所管理员002', '$2b$10$gRwApN1uS2fYcTWQ84PchOirv0A7ZD4Y9ybSnldeW1BaJZlrTO2LG', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  nickname = VALUES(nickname),
  password = VALUES(password),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO sys_user_tenant (
  id, user_id, tenant_id, identity_type, default_flag, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(2, 2, 2, 'KINDER', b'1', b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 3, 3, 'KINDER', b'1', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  identity_type = VALUES(identity_type),
  default_flag = VALUES(default_flag),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);

INSERT INTO user_role (
  id, user_id, role_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(2, 2, 2, b'1', b'0', 'system', NOW(), 'system', NOW()),
(3, 3, 2, b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);
