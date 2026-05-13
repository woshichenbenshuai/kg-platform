SET NAMES utf8mb4;
INSERT INTO sys_user_tenant (
  id, user_id, tenant_id, identity_type, default_flag, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(21002, 20001, 10002, 'PARENT', b'0', b'1', b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  identity_type = VALUES(identity_type), default_flag = VALUES(default_flag), status = VALUES(status), delete_status = VALUES(delete_status), last_modified_time = NOW();
