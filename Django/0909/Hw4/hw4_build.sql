-- ============================================================
-- Django 第四次作業：hw4_build.sql
-- 功能：
--   1. 建立資料庫 h_iot_relay
--   2. 切換到 h_iot_relay
--   3. 匯入老師指定的 4 筆溫溼度資料
--
-- 注意：
--   myapp_temperature_db 資料表請先由 Django ORM 建立：
--       python manage.py makemigrations myapp
--       python manage.py migrate
-- ============================================================

-- 建立資料庫
CREATE DATABASE IF NOT EXISTS h_iot_relay
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 使用第四次作業資料庫
USE h_iot_relay;

-- ------------------------------------------------------------
-- 以下資料表 myapp_temperature_db
-- 應先由 Django models.py + migrate 建立
-- ------------------------------------------------------------

-- 避免重複執行本 SQL 時，同一批資料一直增加
DELETE FROM myapp_temperature_db
WHERE sensor_id = 1
  AND timestamp IN (
    '2022-08-09 08:14:10',
    '2022-08-09 09:29:29',
    '2022-08-09 11:00:00',
    '2022-08-09 13:00:00'
  );

-- 老師第四次作業指定資料
INSERT INTO myapp_temperature_db
(sensor_id, temperature, humidity, timestamp)
VALUES
(1, 30.2, 68.2, '2022-08-09 08:14:10'),
(1, 36,   80.3, '2022-08-09 09:29:29'),
(1, 30.2, 86.3, '2022-08-09 11:00:00'),
(1, 60,   86,   '2022-08-09 13:00:00');

-- 確認匯入結果：依時間遞減排序
SELECT
    myid,
    sensor_id,
    temperature,
    humidity,
    timestamp
FROM myapp_temperature_db
ORDER BY timestamp DESC;
