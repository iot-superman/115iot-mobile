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


USE MYLAB;
SELECT * FROM PET WHERE OWNER=(SELECT OWNER FROM PET WHERE NAME='WHISTLER' LIMIT 1);
-- 找出 Gwen 所有的寵物

-- 2. 查詢比「Slim」更早出生的寵物
SELECT * FROM PET WHERE BIRTH < (SELECT BIRTH FROM PET WHERE NAME='SLIM' LIMIT 1);

-- 3. 查詢和「Fang」相同品種和性別的寵物
SELECT * FROM PET WHERE (SPECIES,GENDER)=(SELECT SPECIES,GENDER FROM PET WHERE NAME='FANG' LIMIT 1);





-- 子查詢(主查詢中包含另一個查詢，而另一個查詢的結果作為主查詢的查詢條件)

USE WORLD;
SELECT POPULATION FROM WORLD.COUNTRY WHERE CODE ='USA';
SELECT  CODE,POPULATION FROM WORLD.COUNTRY WHERE POPULATION > 278357000;


-- 子查詢(主查詢結果為一個值/一筆資料的時候使用)子查詢通常用在 > < = 比較
SELECT  CODE,POPULATION FROM WORLD.COUNTRY WHERE POPULATION >(SELECT POPULATION FROM WORLD.COUNTRY WHERE CODE ='USA');

-- 子查詢結果為一個值/一筆資料的時候使用
SELECT CODE,NAME FROM WORLD.COUNTRY WHERE  GNP=(SELECT MAX(GNP) FROM WORLD.COUNTRY); -- 找出GNP最大的國家,並顯示其國家代碼與國名


SELECT CODE,NAME FROM WORLD.COUNTRY WHERE  GNP=(SELECT MAX(GNP) FROM WORLD.COUNTRY group by continent); -- 語法錯誤

SELECT CODE FROM WORLD.COUNTRY WHERE POPULATION> 900000000;

-- 子查詢結果為多筆資料的時候使用 IN
SELECT COUNTRYCODE FROM WORLD.CITY WHERE POPULATION >9000000;

SELECT NAME FROM WORLD.COUNTRY WHERE CODE IN (SELECT COUNTRYCODE FROM WORLD.CITY WHERE POPULATION >9000000);
SELECT NAME FROM WORLD.COUNTRY WHERE CODE IN ('BRA','IDN');


-- ALL (ANY SOME )
-- 公司有50位員工，其中有一些工讀生，將非工讀列出
USE test1;
CREATE TABLE OUTERTABLE(
    N INT
);
INSERT INTO OUTERTABLE VALUES(1),(2),(3),(4),(5);

CREATE TABLE INNERTABLE(
    N INT
);
INSERT INTO INNERTABLE VALUES(2),(3);


-- https://chatgpt.com/s/m_6a2f704012e081918e4774e012929053
SELECT * FROM OUTERTABLE WHERE N = ALL(SELECT N FROM INNERTABLE);  -- N=2 AND N=3（不可能同時成立，無結果）
SELECT * FROM OUTERTABLE WHERE N <> ALL(SELECT N FROM INNERTABLE); -- N≠2 AND N≠3，等同 NOT IN
SELECT * FROM OUTERTABLE WHERE N > ALL(SELECT N FROM INNERTABLE);  -- N>2 AND N>3，等同 N > MAX → N > 3
SELECT * FROM OUTERTABLE WHERE N >= ALL(SELECT N FROM INNERTABLE); -- N>=2 AND N>=3，等同 N >= MAX → N >= 3


-- ANY
-- https://chatgpt.com/s/m_6a2f719364f48191b2f6c7a34f5e7436
SELECT * FROM OUTERTABLE WHERE N=SOME(SELECT * FROM INNERTABLE);   -- N=2 OR N=3;
SELECT * FROM OUTERTABLE WHERE N<>ANY(SELECT * FROM INNERTABLE);   -- N<>2 OR N<>3;
SELECT * FROM OUTERTABLE WHERE N>ANY(SELECT * FROM INNERTABLE);    -- N>2 OR N>3;
SELECT * FROM OUTERTABLE WHERE N>=ANY(SELECT * FROM INNERTABLE);   -- N>=2 OR N>=3;
SELECT * FROM OUTERTABLE WHERE N<ANY(SELECT * FROM INNERTABLE);    -- N<2 OR N<3

-- 多欄位的查詢
SELECT  NAME,GNP FROM WORLD.COUNTRY WHERE (NAME,GNP)=(SELECT NAME,GNP FROM WORLD.COUNTRY WHERE CODE='USA');
SELECT NAME,GNP FROM WORLD.COUNTRY WHERE (CONTINENT,GOVERNMENTFORM,REGION)=('Asia','Republic','Eastern Asia'); -- AND

-- 找出與台灣同 REGION（地區）且同 GOVERNMENTFORM（政體）的所有國家
-- 兩個子查詢各自回傳一個值，主查詢用 AND 同時比對兩個條件
SELECT NAME FROM WORLD.COUNTRY
WHERE REGION=(SELECT REGION FROM WORLD.COUNTRY WHERE NAME='Taiwan')
AND GOVERNMENTFORM=(SELECT GOVERNMENTFORM FROM WORLD.COUNTRY WHERE NAME='Taiwan'); 

-- 使用 IN + 元件(多欄位組合)來比對 https://chatgpt.com/s/m_6a2f74dc693c81919ac31aa959e7abf1
SELECT NAME FROM WORLD.COUNTRY WHERE(REGION,GOVERNMENTFORM)=(SELECT REGION,GOVERNMENTFORM FROM WORLD.COUNTRY WHERE NAME='MONGOLIA'); 




-- 先查出每個洲的最大GNP
SELECT CONTINENT, MAX(GNP) FROM WORLD.COUNTRY GROUP BY CONTINENT;

SELECT CONTINENT, NAME, GNP FROM WORLD.COUNTRY WHERE (CONTINENT,GNP) IN -- OR
(SELECT CONTINENT, MAX(GNP) FROM WORLD.COUNTRY GROUP BY CONTINENT);


SELECT "THE GNP OF JAPAN IS"+(SELECT GNP FROM WORLD.COUNTRY WHERE NAME='Japan'); --錯


SELECT CONCAT("THE GNP OF JAPAN IS",(SELECT GNP FROM WORLD.COUNTRY WHERE NAME='Japan'))AS JAPAN; --對

--查詢INDIA人口問全人口面分百分比
SELECT (POPULATION/(SELECT SUM(POPULATION) FROM WORLD.COUNTRY))*100 AS 'INDIA POPULATION'
FROM WORLD.COUNTRY WHERE NAME='INDIA';

-- 格式化顯示 ex: 16.10 %
-- FORMAT(數值, 小數位數) 保留指定位數並補零，CONCAT 加上 ' %' 單位
SELECT CONCAT(FORMAT((POPULATION/(SELECT SUM(POPULATION) FROM WORLD.COUNTRY))*100, 2), '%') AS 'RATIO'
FROM WORLD.COUNTRY WHERE NAME='INDIA';

-- 老師的寫法: 用 ROUND 取代 FORMAT，子查詢分開寫更清楚
-- 查詢INDIA人口占全球人口百分比
SELECT CONCAT(ROUND(
    (SELECT POPULATION FROM WORLD.COUNTRY WHERE NAME='INDIA') /
    (SELECT SUM(POPULATION) FROM WORLD.COUNTRY) * 100, 2), '%') AS RATIO;

-- 子查詢查詢的結果可以當成外部查詢的資料表
SELECT NAME,GNP FROM
(SELECT * FROM WORLD.COUNTRY WHERE CONTINENT='ASIA') ASIACOUNTRY ORDER BY GNP DESC LIMIT 10; -- 亞洲各國GNP最高的前10名

SELECT NAME, LANGUAGE, POPULATION * PERCENTAGE FROM WORLD.COUNTRY,
(SELECT COUNTRYCODE,LANGUAGE,PERCENTAGE FROM WORLD.countrylanguage WHERE IsOfficial='T') OFFICIAL
WHERE CODE=COUNTRYCODE;  -- 這是一種用子查詢寫的的交叉查詢寫法

CREATE TABLE TEST1.MYCOUNTRY(
   CODE CHAR(3) PRIMARY KEY,
   NAME CHAR(52) NOT NULL,
   REGION CHAR(26),
   POPULATION INT,
   GNP FLOAT(10,2)
);

-- 建立一個新的資料表 (MYCOUNTRY) 並將WORLD.COUNTRY的所有資料都複製過,並且只複製亞洲的國家
INSERT INTO TEST1.MYCOUNTRY (SELECT CODE,NAME,REGION,POPULATION,GNP FROM WORLD.COUNTRY WHERE CONTINENT='Asia'); -- 用子查詢當成外部查詢的來源資料表
-- 插入
REPLACE INTO TEST1.MYCOUNTRY (SELECT CODE,NAME,REGION,POPULATION,GNP FROM WORLD.COUNTRY WHERE CONTINENT='Asia'); -- REPLACE 語法會先刪除主索引鍵相同的資料,再插入新的資料,可以避免重複插入資料的問題    
-- 修改
UPDATE CMDEV.EMP SET SALARY=SALARY*1.05 WHERE DEPTNO=(SELECT DEPTNO FROM CMDEV.DEPT WHERE DNAME='SALES'); -- 銷售部的員工薪水調薪5%

-- 更新
UPDATE CMDEV.EMP SET SALARY = SALARY * 1.05 WHERE JOB IN (SELECT JOB FROM CMDEV.EMP WHERE ENAME='ALLEN');　--會出錯，因為子查詢用到同一個表的資料


--刪除-- 刪除
DELETE FROM CMDEV.EMP WHERE JOB IN (SELECT JOB FROM CMDEV.EMP WHERE ENAME='ALLEN'); -- 也會出錯,，因為子查詢用到同一個表的資料
-- 14:03:40	--刪除-- 刪除 DELETE FROM CMDEV.EMP WHERE JOB IN (SELECT JOB FROM CMDEV.EMP WHERE ENAME='ALLEN')	Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near '--刪除-- 刪除 DELETE FROM CMDEV.EMP WHERE JOB IN (SELECT JOB FROM CMDEV.EMP ' at line 1	0.000 sec

--**以上所以更新、刪除的子查詢，來源資料表不可是自己，否則會造成循環依賴的問題


-- 關聯子查詢 (利用子查詢，查詢自己的比較結果)
-- 關聯子查詢是子查詢中包含主查詢的子查詢，用來查詢主查詢中未被直接引用的子查詢的結果

SELECT * FROM CMDEV.EMP;
-- 關聯子查詢 (利用子查詢，查詢自己的比較結果)
-- 關聯子查詢是子查詢中包含主查詢的子查詢，用來查詢主查詢中未被直接引用的子查詢的結果
SELECT EMPNO, ENAME, MANAGER, HIREDATE 
FROM CMDEV.EMP E 
WHERE HIREDATE < (
    SELECT HIREDATE 
    FROM EMP 
    WHERE EMPNO = E.MANAGER
); -- 找出比自己主管更早入职的员工

--　CH10 ＰＤＦ

-- Ｐ10-20 習題

-- 選擇題
-- (Ｃ) 1. 選擇可以使用子查詢的子句:

-- A. GROUP BY
-- B. LIMIT
-- C. HAVING(**Beacuse子句)
-- D. INDEX

-- (A) 2. 根據下列的查詢敘述:

-- SELECT ... FROM ... WHERE id = (子查詢)
-- 選擇正確的說明:
-- A. 子查詢不可以傳回多筆記錄,而且只能有一個欄位
-- B. 子查詢可以傳回多筆記錄,可以有多個欄位
-- C. 子查詢可以傳回多筆記錄,不過只能有一個欄位
-- D. 沒有特別的限制

-- (B) 3. 選擇與「ANY」有相同效果的運算子:

-- A. IN
-- B. SOME
-- C. BETWEEN
-- D. AND

-- (D) 4. 根據下列的敘述:

-- () 4. SELECT * FROM (SELECT * FROM emp)
-- 執行上列的敘述後,選擇正確的說明:
-- A. 傳回 emp 表格所有資料
-- B. 沒有傳回任何資料
-- C. 加入 ORDER BY 子句的設定後,就可以傳回 emp 表格所有資料
-- D. 發生錯誤
-- 
-- 解释：

-- - 子查询当作资料表用时， 必须给别名 ！
-- - 正确写法： SELECT * FROM (SELECT * FROM emp) AS temp;




-- 習題 10-21

-- ( D) 5. 根據下列的敘述:

-- DELETE FROM emp WHERE deptno IN (SELECT deptno FROM emp)
-- 執行上列的敘述後,選擇正確的說明:
-- A. 刪除 emp 表格所有資料
-- B. 不會刪除 emp 表格資料
-- C. 加入 ORDER BY 子句的設定後,就可以刪除 emp 表格所有資料
-- D. 發生錯誤

-- (D ) 6. 根據下列的敘述:

-- UPDATE emp SET salary = salary * 1.05 WHERE deptno IN (SELECT
-- deptno FROM emp)
-- 執行上列的敘述後,選擇正確的說明:
-- A. 修改 emp 表格所有資料
-- B. 不會修改 emp 表格資料
-- C. 加入 ORDER BY 子句的設定後,就可以修改 emp 表格所有資料
-- D. 發生錯誤
-- 1. 查詢「Whistler」的主人還有哪些寵物
SELECT * FROM PET
truncate table pet;  -- 刪除 pet 表格所有資料


INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Fluffy','Harold','cat','f','1993-02-04');
INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Claws','Gwen','cat','m','1994-03-17');
INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Buffy','Harold','dog','f','1989-05-13');
INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Fang','Benny','dog','m','1990-08-27');
INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Chirpy','Gwen','bird','f','1998-09-11');
INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Whistler','Gwen','bird',NULL,'1997-12-09');
INSERT INTO pet (id,name,owner,species,gender,birth) VALUES (0,'Slim','Benny','snake','m','1996-04-29');

INSERT INTO event (id,edate,etype,remark) VALUES (1,'1995-05-15','litter','4 kittens, 3 female, 1 male');
INSERT INTO event (id,edate,etype,remark) VALUES (3,'1993-06-23','litter','5 puppies, 2 female, 3 male');
INSERT INTO event (id,edate,etype,remark) VALUES (3,'1994-06-19','litter','3 puppies, 3 female');
INSERT INTO event (id,edate,etype,remark) VALUES (5,'1999-03-21','vet','needed beak straightened');
INSERT INTO event (id,edate,etype,remark) VALUES (7,'1997-08-03','vet','broken rib');
INSERT INTO event (id,edate,etype,remark) VALUES (4,'1991-10-12','kennel',NULL);
INSERT INTO event (id,edate,etype,remark) VALUES (4,'1998-08-28','birthday','Gave him a new chew toy');
INSERT INTO event (id,edate,etype,remark) VALUES (2,'1998-03-17','birthday','Gave him a new flea collar');
INSERT INTO event (id,edate,etype,remark) VALUES (6,'1998-12-09','birthday','First birthday');


USE MYLAB;
SELECT * FROM PET WHERE OWNER=(SELECT OWNER FROM PET WHERE NAME='WHISTLER');
-- 找出 Gwen 所有的寵物

-- 2. 查詢比「Slim」更早出生的寵物
SELECT * FROM PET WHERE BIRTH < (SELECT BIRTH FROM PET WHERE NAME='SLIM');

-- 3. 查詢和「Fang」相同品種和性別的寵物
SELECT * FROM PET WHERE (SPECIES,GENDER)=(SELECT SPECIES,GENDER FROM PET WHERE NAME='FANG');