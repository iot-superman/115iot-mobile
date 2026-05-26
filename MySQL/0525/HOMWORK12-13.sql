-- #12
-- #12 使用撰寫指令方式建立資料表VENDOR_MAIN
--   https://lh3.googleusercontent.com/drive-storage/AJQWtBMiheCoI4KV3IwOJYVQaGspfUL0gJG9CUy8Y4qeY5DGo3K-l9f8Iunp57jANvG8flOa6zKL9Kulrfw7ber0HEsbwjxS79b03ZYWMk37EKesZIBjEA=w962-h1037?auditContext=forDisplay

CREATE TABLE IF NOT EXISTS VENDOR_MAIN (
    
    VENDOR_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '廠商編號',
    VEN_NAME VARCHAR(36) NULL COMMENT '廠商名稱',
    VEN_TEL DECIMAL(20,0) NULL COMMENT '廠商電話',   
    VEN_AREA ENUM('台北市','桃園市','新竹市') NULL COMMENT '廠商所在地'
    
);


-- #13

--#13
-- 朱星念張貼了一個新問題：#13 請用指令方式建立一個表格tableX 張貼日期：下午4:07 已指派
--  1. 欄位no，主鍵，自動編號 
--  2. 欄位created，紀錄新增資料的日期及時間。 
--  3. 欄位updated，紀錄修改資料的日期及時間。 
--  4. 欄位total，整數型態，只能接受正整數，數字前可以補0。




CREATE TABLE IF NOT EXISTS tableX (

    -- 主鍵，自動編號
    no INT NOT NULL AUTO_INCREMENT,
    
    -- 建立資料日期時間
    created DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    -- 修改資料日期時間
    updated DATETIME DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,
    
    -- 只能接受正整數
    -- UNSIGNED 表示不可為負數
    -- ZEROFILL 表示前面可補 0
    total INT UNSIGNED ZEROFILL,
    
    -- 主鍵設定
    PRIMARY KEY (no)

);