-- 리뷰 요청 업적
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 10, 'REQUESTED_REVIEW');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 20, 'REQUESTED_REVIEW');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 30, 'REQUESTED_REVIEW');

-- 누적 추천 수 업적
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 100, 'CUMULATIVE_LIKES');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 200, 'CUMULATIVE_LIKES');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 300, 'CUMULATIVE_LIKES');

-- 채택률 업적
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 50, 'ADOPTION_RATE');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 55, 'ADOPTION_RATE');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 60, 'ADOPTION_RATE');

-- 연속 답변 업적
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 3, 'CONTINUOUS_ANSWER');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 7, 'CONTINUOUS_ANSWER');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 14, 'CONTINUOUS_ANSWER');

-- 채택 답변 개수 업적
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 10, 'ADOPTION_COUNT');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 30, 'ADOPTION_COUNT');
INSERT IGNORE INTO achievements (created_at, updated_at, is_deleted, description, standard, type)
VALUES ('2024-11-27 00:00:00', '2024-11-27 00:00:00', 'FALSE', '', 50, 'ADOPTION_COUNT');
