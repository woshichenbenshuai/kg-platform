SET NAMES utf8mb4;
DROP DATABASE IF EXISTS kg_kinder_00000001;
DROP DATABASE IF EXISTS kg_kinder_00000002;

DELETE FROM user_role WHERE user_id <> 1;
DELETE FROM sys_user_tenant WHERE user_id <> 1 OR tenant_id <> 1;
DELETE FROM tenant_db_config WHERE tenant_id <> 1 OR db_name LIKE 'kg_kinder_%';
DELETE FROM sys_tenant WHERE id <> 1;
DELETE FROM sys_user WHERE id <> 1;

UPDATE sys_tenant
SET tenant_code = 'PLATFORM_ADMIN', tenant_name = '平台管理租户', contact_name = 'admin', contact_phone = NULL,
    address = NULL, expire_date = NULL, remarks = '平台管理员保留租户', status = b'1', delete_status = b'0', last_modified_time = NOW()
WHERE id = 1;

INSERT INTO sys_tenant (
  id, tenant_code, tenant_name, contact_name, contact_phone, address, expire_date, remarks, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(10001, 'KINDER_TEST_001', '测试一园', '王园长', '13800000001', '测试地址一', '2027-12-31', '家长端测试园所一', b'1', b'0', 'system', NOW(), 'system', NOW()),
(10002, 'KINDER_TEST_002', '测试二园', '李园长', '13800000002', '测试地址二', '2027-12-31', '家长端测试园所二', b'1', b'0', 'system', NOW(), 'system', NOW());

INSERT INTO tenant_db_config (
  id, tenant_id, db_host, db_port, db_name, db_username, db_password, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(11001, 10001, '127.0.0.1', 3306, 'kg_kinder_00000001', 'root', 'root', b'1', b'0', 'system', NOW(), 'system', NOW()),
(11002, 10002, '127.0.0.1', 3306, 'kg_kinder_00000002', 'root', 'root', b'1', b'0', 'system', NOW(), 'system', NOW());

INSERT INTO sys_user (
  id, username, nickname, phone, password, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(20001, '13900000001', '测试家长', '13900000001', '$2b$10$1.xoJJNZ1k11xCRDAWts7.mpDSK97Crf9hZPmfkE503QrNowe7n0W', 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO sys_user_tenant (
  id, user_id, tenant_id, identity_type, default_flag, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(21001, 20001, 10001, 'PARENT', b'1', b'1', b'0', 'system', NOW(), 'system', NOW());

INSERT INTO user_role (
  id, user_id, role_id, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(22001, 20001, 4, b'1', b'0', 'system', NOW(), 'system', NOW());
