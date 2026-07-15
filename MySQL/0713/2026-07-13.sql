-- 1. 先切換資料庫與刪除舊的 Procedure（使用原本的分號 ';'）
DELIMITER $$

USE TEST1$$

DROP PROCEDURE IF EXISTS A_TEST$$

CREATE PROCEDURE A_TEST(
    P_DEPTNO INT, 
    P_DNAME VARCHAR(16), 
    P_LOCATION VARCHAR(16)
)
BEGIN
    -- 1. 所有的 DECLARE 錯誤處理器都必須宣告在最上方
    
    -- 處理 SQLSTATE '23000' (例如：主鍵重複)
    DECLARE EXIT HANDLER FOR SQLSTATE '23000'
    BEGIN
        SELECT 'ERROR';
        INSERT INTO CMDEV.DEPTLOG (MESSAGE) VALUES ('寫入資料失敗!!!');
    END;

    -- 處理錯誤代碼 1048 (例如：欄位不可為 NULL)
    DECLARE EXIT HANDLER FOR 1048
    BEGIN
        SELECT 'ERROR: Column cannot be null';
        INSERT INTO CMDEV.DEPTLOG (MESSAGE) VALUES ('寫入資料失敗(欄位不可為空)!!!');
    END;

    -- 2. 這裡才是主程式真正的執行邏輯
    INSERT INTO CMDEV.DEPT VALUES (P_DEPTNO, P_DNAME, P_LOCATION);
    
    -- 如果上面的 INSERT 成功（沒有觸發任何 Handler），就會繼續執行以下這兩行
    SELECT 'INSERT SUCCESS!!!';
    INSERT INTO CMDEV.DEPTLOG (MESSAGE) VALUES ('寫入資料成功!!!');

END $$

DELIMITER ;

-- ============================
-- 確保使用的是正確的資料庫
 
-- 1. 第一次呼叫：使用全新編號 555
-- 預期：成功寫入，回應 'INSERT SUCCESS!!!!'，並寫入一筆「寫入資料成功!!!」
CALL A_TEST(555, 'NEW_DEPT',NULL);

-- 2. 第二次呼叫：再次使用 555
-- 預期：主鍵重複，回應 'ERROR!!!!'，並寫入一筆「寫入資料失敗!!!」
CALL A_TEST(555, 'NEW_DEPT', 'TAIPEI');

CALL A_TEST(88, NULL, 'TAICHUNG');

-- 3. 最後查詢 log 表看結果
SELECT * FROM cmdev.deptlog;