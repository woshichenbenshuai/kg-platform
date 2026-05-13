SET NAMES utf8mb4;
INSERT INTO kindergarten_class (
  id, class_code, class_name, grade_name, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(30002, 'CLASS_002', '中一班', '中班', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE class_name = VALUES(class_name), grade_name = VALUES(grade_name), status = VALUES(status), delete_status = VALUES(delete_status), last_modified_time = NOW();

INSERT INTO student (
  id, class_id, student_no, student_name, gender, birthday, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(31003, 30002, 'S2026003', '测试宝宝三', 'MALE', '2022-02-20', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE class_id = VALUES(class_id), student_name = VALUES(student_name), gender = VALUES(gender), birthday = VALUES(birthday), status = VALUES(status), delete_status = VALUES(delete_status), last_modified_time = NOW();

INSERT INTO guardian (
  id, user_id, guardian_name, phone, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(32002, 20001, '测试家长', '13900000001', 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE guardian_name = VALUES(guardian_name), phone = VALUES(phone), status = VALUES(status), delete_status = VALUES(delete_status), last_modified_time = NOW();

INSERT INTO student_guardian_relation (
  id, student_id, guardian_id, relation_type, primary_contact, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(33003, 31003, 32002, '妈妈', 1, 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE relation_type = VALUES(relation_type), primary_contact = VALUES(primary_contact), status = VALUES(status), delete_status = VALUES(delete_status), last_modified_time = NOW();

INSERT INTO leave_request (
  id, student_id, guardian_id, start_date, end_date, reason, approve_status, approve_remark, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(34003, 31003, 32002, '2026-05-16', '2026-05-16', '去医院复查请假一天', 'PENDING', NULL, 1, b'0', 'system', NOW(), 'system', NOW())
ON DUPLICATE KEY UPDATE reason = VALUES(reason), approve_status = VALUES(approve_status), approve_remark = VALUES(approve_remark), status = VALUES(status), delete_status = VALUES(delete_status), last_modified_time = NOW();
