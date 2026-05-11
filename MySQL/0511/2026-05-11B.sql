-- SQL = Stucture Query LAnguage結構化東詢語言)

-- ISO / ANSI 92

#新增/刪除/修改／查詢 

-- table 資料表alter ->欄位coulumn(屬性) /(索引index /外鍵foregion key /觸發器 trigger)
-- views 檢視表
-- stored porfedrue 預存程式alter
-- function函式alter

-- cmdev資料庫alter
   -- dept=depatmen 部門表
   -- emp = emplyee 員工表 
   -- travel = 員工出差alter
   
-- world資料庫
  --city 城市資料表 
  --country國家資料表
  --countrylangguage 國家語言資料表

--ci   case insenstitive 不分大小寫
--cs   case  senstitivre  分大小寫
  
 
CREATE SCHEMA DBTEST  -- DDL DATADEFIN LANGUAGe:
show character set;  --顯示系統有什麼編馬可以用

SHOW COLLATION;
SHOW COLLATION LIKE '%big5%'    -- LIKE 模糊比對



CREATE DATABASE TEST1 CHARACTER SET big5 COLLATE big5_chinese_ci;
CREATE SCHEMA IF NOT EXISTS TEST1;   -- DDL
-- 修改資料庫 
Alter DATABASE TEST1 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci;  -- DDL
DROP SCHEMA dbtest;

DROP SCHEMA IF EXISTS dbtest;

--查詢資料庫資訊
SHOW DATABASES;
SHOW CREATE DATABASE MYDATABASE;
SELECT * FROM  INFORMATION_SCHEMA.SCHEMATA;
SELECT * FROM   INFORMATION_SCHEMA.SCHEMATA;

------

select "ABC"
select 1+100

 
