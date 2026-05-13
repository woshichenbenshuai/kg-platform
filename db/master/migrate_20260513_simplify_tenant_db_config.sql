USE kg_platform;

ALTER TABLE tenant_db_config
  ADD COLUMN db_password VARCHAR(512) NULL COMMENT 'Database password' AFTER db_username;

UPDATE tenant_db_config
SET db_password = CASE
    WHEN db_password_encrypted LIKE 'ENC(%)' THEN SUBSTRING(db_password_encrypted, 5, CHAR_LENGTH(db_password_encrypted) - 5)
    ELSE CAST(FROM_BASE64(db_password_encrypted) AS CHAR)
  END
WHERE db_password IS NULL;

ALTER TABLE tenant_db_config
  MODIFY COLUMN db_password VARCHAR(512) NOT NULL COMMENT 'Database password',
  DROP COLUMN db_type,
  DROP COLUMN db_password_encrypted,
  DROP COLUMN jdbc_params,
  DROP COLUMN schema_version,
  DROP COLUMN db_status,
  DROP COLUMN last_check_time,
  DROP COLUMN last_check_result;
