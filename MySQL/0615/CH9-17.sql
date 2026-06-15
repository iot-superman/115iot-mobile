-- 索引(CH9)
-- 主索引(資料不可重複/not null/在一個資料表中只能有一個PK(Primary key))
-- 一般索引(index)(可以允許資料重複/可null)--> FK
-- 唯一索引(unique index(UQ))(可允許資料null/資料不可重複)
-- 關聯式系統(FK(foreign key)參考主鍵(pk)的架構)


USE TEST1;                       -- 切換到 TEST1 資料庫
CREATE TABLE T1(                 -- 建立資料表 T1
    ID      INT,                 -- 主鍵欄位：整數型態
    NAME    VARCHAR(30),         -- 主鍵欄位：整數型態
    EMAIL   VARCHAR(30),         -- 唯一索引欄位：最多30字元的可變字串
    ADDRESS VARCHAR(30),         -- 一般索引欄位：最多30字元的可變字串
    PRIMARY KEY(ID,NAME),        -- 主索引：ID不可重複、不可null，一張表只能一個PK
    UNIQUE KEY(EMAIL),           -- 唯一索引(UQ)：EMAIL不可重複，但可null
    INDEX (ADDRESS(10) DESC)     -- 一般索引：ADDRESS前10字元建立index，可重複、可null 若不加(10)則為全部字元建立索引,DESC為倒序,不寫則為正序 ASC
);


CREATE TABLE T2(                 -- 建立資料表 T2
    ID      INT,                 -- 主鍵欄位：整數型態
    NAME    VARCHAR(10),         -- 主鍵欄位：整數型態
    EMAIL   VARCHAR(30),         -- 唯一索引欄位：最多30字元的可變字串
    ADDRESS VARCHAR(30)         -- 一般索引欄位：最多30字元的可變字串
);

ALTER TABLE T2 ADD PRIMARY KEY(ID);    -- 在ID欄位上建立主索引：ID不可重複、不可null，一張表只能一個PK
ALTER TABLE T2 ADD UNIQUE KEY(EMAIL);  -- 在EMAIL欄位上建立唯一索引(UQ)：EMAIL不可重複，但可null
ALTER TABLE T2 ADD INDEX(ADDRESS);     -- 在ADDRESS欄位上建立一般索引(index)：ADDRESS可重複、可null 

CREATE TABLE T3(
    ID     INT,
    NAME   VARCHAR(10) ,
    EMAIL  VARCHAR(30),
    ADDRESS VARCHAR(30)
    
);

CREATE UNIQUE INDEX email_index ON T3(EMAIL);  -- 在EMAIL欄位上建立唯一索引(UQ)：EMAIL不可重複，但可null

-- 09:13:08	CREATE INDEX PRIMARY ON T3(ID)	Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'PRIMARY ON T3(ID)' at line 1	0.000 sec
CREATE INDEX PRIMARY ON T3(ID);  -- ｃｒｅａｔｅ　ｉｎｄｅｘ　不可以建主索引

DROP INDEX `PRIMARY` ON T3;  -- 在EMAIL欄位上刪除唯一索引(UQ) `PRIMARY` 是主索引名稱    可以刪除索引名稱

DROP INDEX `EMAIL` ON T2;  -- 在EMAIL欄位上刪除唯一索引(UQ) `EMAIL` 是唯一索引名稱    可以刪除索引名稱

SHOW INDEX FROM T1;    -- 顯示T1的所有索引 


SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'TEST1'; -- 查詢TEST1有哪些資料表

-- 可以查詢索引有哪些資料
SELECT * FROM information_schema.INDEX   --ERROR不可執行成功


-- CH9-17
-- https://chatgpt.com/share/6a2f61ea-f62c-83e8-8f2f-79e2b8666361

-- (C) 1. 選擇可以正確的在建立表格時指定主索引鍵的敘述:
-- A. CREATE TABLE hello (id INT KEY)
-- B. CREATE TABLE hello (id INT PRIMARY)
-- C. CREATE TABLE hello (id INT, PRIMARY KEY (id))
-- D. CREATE TABLE hello (id INT, KEY (id))

-- (D) 2. 選擇可以正確的在建立表格時指定兩個欄位為主索引鍵的敘述:
-- A. CREATE TABLE hello (id INT KEY, name VARCHAR(20) KEY)
-- B. CREATE TABLE hello (id INT PRIMARY KEY, name VARCHAR(20)
-- PRIMARY KEY)
-- C. CREATE TABLE hello (id INT PRIMARY, name VARCHAR(20)
-- PRIMARY)
-- D. CREATE TABLE hello (id INT, name VARCHAR(20), PRIMARY
-- KEY (id, name))

-- (D) 3. 選擇可以正確為 hello 表格的 name 欄位加入一般索引的敘述:
-- A. CREATE UNIQUE INDEX name_index ON hello (name)
-- B. CREATE PRIMARY KEY name_index ON hello (name)
-- C. CREATE KEY name_index ON hello (name)
-- D. CREATE INDEX name_index ON hello (name)

-- (A) 4. 選擇可以查詢 hello 表格索引資訊的敘述:
-- A. SHOW INDEX FROM hello
-- B. LIST INDEX FROM hello
-- C. DESC hello
-- D. DESC INDEX hello

(A) 5. 「hello」表格的「name」欄位的型態為 VARCHAR(20),選擇可以
為 name 前五個字元建立一般索引的敘述:
A. CREATE INDEX name_index ON hello (name (5))
B. CREATE INDEX name_index ON hello (name, 5)
C. CREATE INDEX name_index ON hello (name)(5)
D. CREATE INDEX name_index ON hello (name)



(A) 6. 選擇可以正確為 hello 表格的 name 欄位加入唯一索引的敘述:
A. CREATE UNIQUE INDEX name_index ON hello (name)
B. CREATE UNIQUE KEY name_index ON hello (name)
C. CREATE KEY name_index ON hello (name)
D. CREATE INDEX name_index ON hello (name)


-- 1. 使用「ALTER TABLE」敘述,為「mylab.pet」表格的「name」欄位建立
-- 一個名稱為「pet_name_index」的非唯一索引。
ALTER TABLE mylab.pet ADD INDEX pet_name_index (name);

-- 2. 使用「CREATE INDEX」敘述,為「mylab.pet」表格的「owner」欄位建立
-- 一個名稱為「pet_owner_index」的非唯一索引。
CREATE INDEX pet_owner_index ON mylab.pet (owner);

-- 3. 參考下面的輸出畫面，查詢「mylab.pet」表格的索引資訊，
-- 確認上列練習建立的索引。
-- 預期結果：
--   Table | Non_unique | Key_name        | Seq_in_index | Column_name
--   pet   |          0 | PRIMARY         |            1 | id
--   pet   |          1 | pet_name_index  |            1 | name
--   pet   |          1 | pet_owner_index |            1 | owner
SHOW INDEX FROM MYLAB.PET





CREATE TABLE IF NOT EXISTS T4(
    ID INT PRIMARY KEY,
    NAME VARCHAR(20) UNIQUE KEY,
    EMAIL VARCHAR(30) UNIQUE KEY
);

SHOW INDEX FROM T4;   -- 顯示T4的所有索引 

-- 子查詢(主查詢中包含另一個查詢，而另一個查詢的結果作為主查詢的查詢條件)

