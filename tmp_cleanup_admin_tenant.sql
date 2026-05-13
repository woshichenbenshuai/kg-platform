SET NAMES utf8mb4;
DELETE FROM tenant_db_config WHERE tenant_id = 1 OR db_name = 'kg_platform';
DELETE FROM sys_user_tenant WHERE user_id = 1 OR tenant_id = 1;
DELETE FROM sys_tenant WHERE id = 1;
UPDATE user_role SET status=b'1', delete_status=b'0' WHERE user_id=1 AND role_id=1;
