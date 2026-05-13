SET NAMES utf8mb4;
INSERT INTO kindergarten_class (
  id, class_code, class_name, grade_name, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(30001, 'CLASS_001', '小一班', '小班', 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO student (
  id, class_id, student_no, student_name, gender, birthday, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(31001, 30001, 'S2026001', '测试宝宝一', 'MALE', '2021-03-05', 1, b'0', 'system', NOW(), 'system', NOW()),
(31002, 30001, 'S2026002', '测试宝宝二', 'FEMALE', '2021-08-16', 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO guardian (
  id, user_id, guardian_name, phone, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(32001, 20001, '测试家长', '13900000001', 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO student_guardian_relation (
  id, student_id, guardian_id, relation_type, primary_contact, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(33001, 31001, 32001, '妈妈', 1, 1, b'0', 'system', NOW(), 'system', NOW()),
(33002, 31002, 32001, '妈妈', 1, 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO leave_request (
  id, student_id, guardian_id, start_date, end_date, reason, approve_status, approve_remark, status, delete_status,
  create_by, create_time, last_modified_by, last_modified_time
) VALUES
(34001, 31001, 32001, '2026-05-13', '2026-05-13', '发烧请假一天', 'PENDING', NULL, 1, b'0', 'system', NOW(), 'system', NOW()),
(34002, 31002, 32001, '2026-05-14', '2026-05-15', '家中有事请假两天', 'APPROVED', '同意', 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO kindergarten_notice (
  id, title, content, publish_time, status, delete_status, create_by, create_time, last_modified_by, last_modified_time
) VALUES
(35001, '测试通知', '请家长关注天气变化。', NOW(), 1, b'0', 'system', NOW(), 'system', NOW());

INSERT INTO daily_recipe (
  id, recipe_date, meal_type, content, status, delete_status, create_by, create_time, last_modified_by, last_modified_time
) VALUES
(36001, CURDATE(), 'BREAKFAST', '牛奶、鸡蛋、面包', 1, b'0', 'system', NOW(), 'system', NOW());
