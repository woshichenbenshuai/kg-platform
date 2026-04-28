SET NAMES utf8mb4;

INSERT INTO student (
  id, student_no, student_name, gender, birthday, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(1, 'STU001', 'Student Demo 001-A', 'MALE', '2020-01-01', 1, b'0', 'system', NOW(), 'system', NOW()),
(2, 'STU002', 'Student Demo 001-B', 'FEMALE', '2020-03-12', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE
  student_name = VALUES(student_name),
  gender = VALUES(gender),
  birthday = VALUES(birthday),
  status = VALUES(status),
  delete_status = VALUES(delete_status),
  last_modified_by = VALUES(last_modified_by),
  last_modified_time = VALUES(last_modified_time);
