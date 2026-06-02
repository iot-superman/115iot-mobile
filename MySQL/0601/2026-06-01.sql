-- https://youtu.be/xzDpOlSyL_U
CREATE DATABASE IF NOT EXISTS DB1;
USE DB1;

CREATE TABLE IF NOT EXISTS Tw_Country 
AS SELECT Code, Name, Population FROM world.Country WHERE Code = 'TWN';

CREATE TABLE IF NOT EXISTS city_Auto (
    Id SERIAL,
    Name VARCHAR(30),
    CountryCode CHAR(3),
    Population INT
)
AS SELECT
    ID,
    Name,
    CountryCode,
    Population
FROM world.city
WHERE CountryCode = 'TWN';

SELECT * FROM city_Auto;

// 插入數據 INSERT INTO 表名 (列名1,列名2,列名3...) VALUES (值1,值2,值3...)
INSERT INTO city_Auto (ID,NAME,COUNTRYCODE,POPULATION)
VALUES (
    1,
    'Taipei',
    'TWN',
    2600000
);

SELECT * FROM city_Auto;


CREATE TABLE IF NOT EXISTS DB1.CITY2 (
    
    Name VARCHAR(35),
    POPULATION INT UNSIGNED,
    DESCRIPTION VARCHAR(50)
)SELECT NAME,POPULATION FROM WORLD.CITY WHERE COUNTRYCODE = 'USA';


--重命名城市名稱 AS 新名稱 FROM 才可以放入表中
CREATE TABLE IF NOT EXISTS DB1.CITYOFUSA1 (
    NEWNAME VARCHAR(35),
    POPULATION INT UNSIGNED,
    DESCRIPTION VARCHAR(50)
)SELECT NAME AS NEWNAME,POPULATION FROM WORLD.CITY WHERE COUNTRYCODE = 'USA';


-- 創建表 LIKE 表名 ,結構會一起複製過去
CREATE TABLE DB1.TESTOFCITY
LIKE WORLD.CITY;


-- 創建表 SELECT * FROM 表名 WHERE 1=0
-- 這個表會是空的，因為 1=0 是 false ，所以不會有數據, 但會有結構
CREATE TABLE DB1.CITYOFWORLD
SELECT * FROM WORLD.CITY WHERE 1=0;

-- 創建暫時表
CREATE TEMPORARY TABLE IF NOT EXISTS TEMP1 (
    ID VARCHAR(35),
    Name VARCHAR(35),
    SALARY INT UNSIGNED
);

INSERT INTO TEMP1 VALUES (1,"Bill",123459);
SELECT * FROM temp1

-- 創建暫時表 world.city
CREATE TEMPORARY TABLE IF NOT EXISTS world.city (
    ID VARCHAR(35),
    Name VARCHAR(35),
    SALARY INT UNSIGNED
);
SELECT * FROM world.city

-- 新增列 ADD COLUMN 列名 列類型 ,在最後面新增
alter table DB1.CITYOFUSA1 add newCOOLUMN VARCHAR(10);

-- 新增列 ADD COLUMN 列名 列類型 ,在第一個新增列
alter table DB1.CITYOFUSA1 add newCOOLUMN2 VARCHAR(10) first;

-- 新增列 ADD COLUMN 列名 列類型 ,在指定列後新增
alter table DB1.CITYOFUSA1 add newcol3 VARCHAR(10) AFTER descripTION;

-- 新增多列 ADD COLUMN 列名 列類型 ,在最後面新增
alter table DB1.CITYOFUSA1 ADD(col5 INT,COL6 VARCHAR(10));

-- 改變列型 CHANGE 列名 列名 列類型 ,並指定新增位置
alter table DB1.CITYOFUSA1 change newcol3 newcol3 VARCHAR(10) AFTER Population;

-- 改變列型 CHANGE 列名 列名 列類型 ,並指定新增位置
alter table DB1.CITYOFUSA1 change COL6 NEWCOL6 INT  first;

-- ALTER TABLE 列名 列類型 ,並指定新增位置
alter table DB1.CITYOFUSA1 modify NEWCOL6 VARCHAR(10) AFTER COL5;

-- 重命名表 RENAME TO 新名稱
ALTER TABLE DB1.CITYOFUSA1 RENAME TO DB1.NEW_CITYOFUSA;  

-- 重命名表 RENAME TO 新名稱
-- 注意：重命名表時，如果新名稱已經存在，會被覆蓋
RENAME TABLE DB1.NEW_CITYOFUSA TO DB1.CITYOFUSA2;


-- 新增／刪除／修改／查詢 
SHOW TABLES FROM WORLD;

-- 查詢所有表名 包含 '%Y%'
SHOW TABLES FROM WORLD LIKE '%Y%';

-- 查詢所有表名 以及表結構 但不包含數據
SELECT * FROM INFORMATION_SCHEMA.TABLES;

-- 查詢所有表名 以及表結構 但不包含數據 且只查詢 DB1 資料庫的表
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'DB1';

-- DESC 表名 查詢表結構
DESCRIBE WORLD.Country

DESC WORLD.Country;

-- SHOW COLUMNS FROM 表名 查詢表所有列名
SHOW COLUMNS FROM WORLD.Country;

-- 在 WorkBeach   world >Table> Country >右鍵 >Send to SQL EDitor >Create Segmenet
-- 產出 CREATE TABLE 語句 用來創建表 country

CREATE TABLE `country` (
  `Code` char(3) NOT NULL DEFAULT '',
  `Name` char(52) NOT NULL DEFAULT '',
  `Continent` enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia',
  `Region` char(26) NOT NULL DEFAULT '',
  `SurfaceArea` decimal(10,2) NOT NULL DEFAULT '0.00',
  `IndepYear` smallint DEFAULT NULL,
  `Population` int NOT NULL DEFAULT '0',
  `LifeExpectancy` decimal(3,1) DEFAULT NULL,
  `GNP` decimal(10,2) DEFAULT NULL,
  `GNPOld` decimal(10,2) DEFAULT NULL,
  `LocalName` char(45) NOT NULL DEFAULT '',
  `GovernmentForm` char(45) NOT NULL DEFAULT '',
  `HeadOfState` char(60) DEFAULT NULL,
  `Capital` int DEFAULT NULL,
  `Code2` char(2) NOT NULL DEFAULT '',
  PRIMARY KEY (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 8Bit(位元) = 1 Byte(位元組)
CREATE TABLE IF NOT EXISTS BITTABLE (
    N1 BIT     -- 1
    N2 Byte,   --  255
    N3 BIGINT  --  65535
);

INSERT INTO BITTABLE VALUES (1, 255, 65535);
INSERT INTO BITTABLE VALUES (0b00000001, 0b11111111, 0b1111111100);


SELECT * FROM cmdev.nonbinarytable2
-- LENGTH() 查詢字元長度,不同編碼方式的字元長度不同,但字元數量相同中文 big5 編碼下為 2 個字元,utf8mb4 編碼下為 3 個字元
SELECT S,S2,LENGTH(S2),S3,LENGTH(S3) FROM cmdev.nonbinarytable2

SHOW CHARACTER SET;


-- char_length() 查詢字元長度
SELECT S,char_length(S),S2,char_length(S2),S3,char_length(S3) FROM cmdev.nonbinarytable2

SELECT * FROM  cmdev.nonbinarytable3
-- CI CASE INSENSTIVEV 查詢字元長度,(不)考慮大小寫
-- CS CASE Sensitive 查詢字元長度,考慮大小寫
-- LATIN_GENERAL_CI    
SELECT S,char_length(S),S2,char_length(S2),S3,char_length(S3) FROM cmdev.nonbinarytable3



SELECT * FROM cmdev.nonbinarytable3
-- CS CASE Sensitive 查詢字元長度,考慮大小寫
SELECT * FROM cmdev.nonbinarytable3　 ORDER BY S;


-- CI CASE INSENSTIVEV 查詢字元長度,(不)考慮大小寫
--  2 筆 'AAA' 和'aaa' 會被視為相同，所以查詢結果會包含 2 筆資料
SELECT * FROM cmdev.nonbinarytable3 WHERE S='AAA';

-- CS CASE Sensitive 查詢字元長度,考慮大小寫
--  1 筆 'AAA' 會被視為不同，所以查詢結果會包含 1 筆資料
SELECT * FROM cmdev.nonbinarytable3 WHERE S2='AAA';


-- able: enumtable
-- Columns:
-- enumsize enum('XS','S','M','L','XL') 
-- stringsize varchar(2)

INSERT INTO enumtable VALUES ('YY','YY');
-- 1= 'XS'
INSERT INTO enumtable VALUES (1,'14');
-- 2= 'S'
INSERT INTO enumtable VALUES (2,'15');
-- 3= 'M'
INSERT INTO enumtable VALUES (3,'16');
-- 4= 'L'
INSERT INTO enumtable VALUES (4,'17');
-- 5= 'XL'
INSERT INTO enumtable VALUES (5,'18');

-- 查詢 enumtable 表
-- ORDER BY  ENUMSIZE 按 enumsize 順序排序
-- ORDER BY  stringsize 按 stringsize 順序排序
SELECT * FROM cmdev.enumtable ORDER BY  ENUMSIZE;

-- 查詢 enumtable 表
-- ORDER BY  stringsize 按 stringsize 降序排序
SELECT * FROM cmdev.enumtable ORDER BY  ENUMSIZE DESC;

-- 查詢 enumtable 表
-- WHERE  enumsize=3 ='M'
-- 查詢 enumsize  於 3 的資料
SELECT * FROM cmdev.enumtable WHERE ENUMSIZE=3;

--Table: settable
-- Columns:
-- workingday set('MON','TUE','WED','THU','FRI','SAT','SUN'
--                  
SELECT * FROM cmdev.settable;
INSERT INTO settable VALUES('MON,TUE,FRI');

INSERT INTO settable VALUES(0);   -- 空的
INSERT INTO settable VALUES(1);   -- MON 2^0
INSERT INTO settable VALUES(2);   -- TUE 2^1
INSERT INTO settable VALUES(4);   -- WED 2^2
INSERT INTO settable VALUES(8);   -- THU 2^3
INSERT INTO settable VALUES(16);   -- FRI 2^4
INSERT INTO settable VALUES(32);   -- SAT 2^5
INSERT INTO settable VALUES(64);   -- SUN 2^6
INSERT INTO settable VALUES(1+2+64);   -- MON,TUE,SUN

INSERT INTO settable VALUES(14);   --  TUE,WED,THU =(2^1 + 2^2+ 2^3= 2 + 4 + 8 = 14)
INSERT INTO settable VALUES(21);   --  TUE,WED,FRI =(2^1 + 2^2+ 2^4= 2 + 16 = 21)

USE DB1;
CREATE TABLE IF NOT EXISTS PHOTOS (
    ID SERIAL,
    PHOTO BLOB
);
INSERT INTO PHOTOS VALUES (1, 'photo1.jpg');
INSERT  INTO dttable  (D) VALUES('900-01-01');
INSERT  INTO dttable  (D) VALUES('20-01-01');     -- 自動補成 2020
INSERT  INTO dttable  (D) VALUES('2-01-01');       -- 自動補成 0002
INSERT  INTO dttable  (D) VALUES('132-01-01');   -- 自動補成 0132
INSERT  INTO dttable  (t) VALUES('200:30:01');
INSERT  INTO dttable  (t) VALUES('-200:30:01');

-- 自動補成 00:02:00，這個寫法不好
INSERT  INTO dttable  (t) VALUES('200');  

-- 自動補成 2026-06-01 15:40:00
INSERT  INTO dttable  (dt) VALUES('2026-06-01 15:40');  
-- 自動補成 2026-06-01 00:00:00
INSERT  INTO dttable  (dt) VALUES('2026-06-01');  

-- 自動補成 2026-06-01 23:59:59  24小時制
INSERT  INTO dttable  (dt) VALUES('2026-06-01 23:59:59');  

INSERT  INTO dttable  (y4) VALUES('0000');  


INSERT  INTO dttable  (y4) VALUES('0');  -- 2000

INSERT  INTO dttable  (y4) VALUES('00');  -- 2000

INSERT  INTO dttable  (ts) VALUES('1970-01-01 8:0:01');

--- 1970-01-01 00:00:00 1 0:00:00後的值 
-- 以下這個會-8HR 會不可以執行
INSERT  INTO dttable  (ts) VALUES('1970-01-01 0:0:01');

SELECT * FROM dttable 
