SET NAMES utf8mb4;

INSERT INTO student (
  id, student_no, student_name, gender, birthday, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'STU001', 'Student Demo 002-A', 'MALE', '2019-09-09', 1, b'0', 'system', NOW(), 'system', NOW()),
(2, 'STU002', 'Student Demo 002-B', 'FEMALE', '2021-05-21', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  student_name = VALUES(student_name),
  gender = VALUES(gender),
  birthday = VALUES(birthday),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);
