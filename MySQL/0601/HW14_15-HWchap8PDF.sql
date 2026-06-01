
-- #16 產生一個與city資料表相同結構的cityofworld資料表，該資料表不包含資料。

CREATE TABLE cityofworld LIKE city;


-- #15 world.country資料表，該如何建立之語法，可用指令為何?
SHOW CREATE TABLE world.country;
 




-- 14請寫出查詢cmdev資料庫中有幾個資料表的sql敘述(較詳細資料)
SHOW TABLE STATUS FROM cmdev ;

-- chap8-1
----延續上一章的實作題。使用「USE」敘述切換到「mylab」資料庫。參考
-- 下列的說明,使用「CREATE TABLE」敘述建立「pet」表格:
-- 欄位名稱 型態 NULL 索引 預設值 其它資訊 說明
-- id SERIAL NO PRI NULL 寵物編號
-- name varchar(30) NO NULL 寵物名稱
-- owner varchar(30) YES NULL 主人名稱
-- species varchar(30) YES NULL 寵物種類
-- gender ENUM('m','f') YES NULL 寵物性別
-- birth date YES NULL 出生日期


-- 
CREATE　mylab;
USE mylab;
 
CREATE TABLE pet (
    id SERIAL,
    name VARCHAR(30) NOT NULL,
    owner VARCHAR(30),
    species VARCHAR(30),
    gender ENUM('m', 'f'),
    birth DATE
);

-- chap8-2

-- 2. 參考下列的說明,使用「CREATE TABLE」敘述建立「event」表格:
-- 欄位名稱 型態 NULL 索引 預設值 其它資訊 說明
-- id

-- BIGINT
-- UNSIGNED NO NULL 寵物編號
-- edate date NO NULL 日期
-- etype varchar(15) NO NULL 種類
-- remark varchar(255) YES NULL 說明

-- 沒有寫是null
CREATE TABLE IF NOT EXISTS EVENT (
    id BIGINT UNSIGNED NOT NULL,
    edate DATE NOT NULL,
    etype VARCHAR(15) NOT NULL,
    remark VARCHAR(255)
);

