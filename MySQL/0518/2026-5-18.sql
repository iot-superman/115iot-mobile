-- **相對於書的CHAP2 **

-- 切換到 WORLD 資料庫
USE WORLD;

-- 查詢 world.city 資料表中的 ID 與 NAME 欄位
SELECT ID, NAME FROM world.city;

-- 查詢人口介於 80000(含) 到 90000(不含) 的城市
SELECT ID, NAME, Population FROM world.city WHERE Population >= 80000 AND Population < 90000;

-- 查詢人口介於 80000 到 90000(含上下限) 的城市
SELECT ID, NAME, Population FROM world.city WHERE Population BETWEEN 80000 AND 90000;

-- 再次切換到 WORLD 資料庫
USE WORLD;

-- 查詢國家名稱與平均壽命
SELECT NAME, LifeExpectancy FROM world.country;

-- 查詢平均壽命為 NULL 的國家
SELECT NAME, LifeExpectancy FROM world.country WHERE LifeExpectancy IS NULL;

-- 使用 NULL-safe 比較運算子 <=> 查詢平均壽命為 NULL 的國家（MySQL 特有）
SELECT NAME, LifeExpectancy FROM world.country WHERE LifeExpectancy <=> NULL;

-- 查詢平均壽命不為 NULL 的國家，並依壽命由小到大排序
SELECT NAME, LifeExpectancy FROM world.country WHERE LifeExpectancy IS NOT NULL ORDER BY LifeExpectancy ASC;

-- 查詢平均壽命不為 NULL 的國家，並依壽命由大到小排序
SELECT NAME, LifeExpectancy FROM world.country WHERE LifeExpectancy IS NOT NULL ORDER BY LifeExpectancy DESC;

-- 查詢國家代碼為 TWN、USA、JPN、KOA 的城市資料
SELECT * FROM WORLD.CITY WHERE CountryCode IN ('TWN', 'USA', 'JPN', 'KOA');

-- 查詢佣金 COMM 不為 NULL 且不等於 0 的員工資料，依 salary 排序
SELECT * FROM cmdev.emp WHERE COMM IS NOT NULL AND COMM != 0 ORDER BY salary;

-- 查詢佣金 COMM 不為 NULL 且不等於 0 的員工資料，依第 5 欄排序
SELECT * FROM cmdev.emp WHERE COMM IS NOT NULL AND COMM != 0 ORDER BY 5;

-- 查詢所有員工資料，依第 2 欄排序
SELECT * FROM cmdev.emp ORDER BY 2;

-- 查詢員工編號、姓名與雇用日期，並使用中文欄位別名，依雇用日期排序
SELECT EMPNO AS 員工編號, ename AS 員工姓名, hiredate AS 雇用日期 FROM cmdev.EMP ORDER BY 雇用日期;

-- 查詢員工姓名包含字母 A 的員工資料
SELECT * FROM CMDEV.EMP WHERE ENAME LIKE '%A%';

-- 查詢 CMDEV.EMP 資料表中的所有欄位與所有資料
SELECT * FROM CMDEV.EMP;

-- 查詢國家代碼、國家名稱與洲別，篩選名稱中包含字母 G 的國家，並依名稱排序
SELECT CODE, NAME, Continent FROM WORLD.country WHERE NAME LIKE '%G%' ORDER BY NAME;

-- 查詢 WORLD.CITY 資料表中，國家代碼為 TWN 且城市名稱不是以 T 開頭的資料
SELECT * FROM WORLD.CITY WHERE CountryCode = 'TWN' AND NAME NOT LIKE 'T%';

-- 查詢員工姓名、月薪與年資，並依年資排序
SELECT ENAME,salary,salary*12 AS ANNUALALARY FROM cmdev.EMP ORDER BY ANNUALALARY;

-- 查詢員工姓名、月薪與年資，並依月薪排序

--        1  ,2    ,3    ,so 2 is salary
SELECT ENAME,salary,salary*12 AS ANNUALALARY FROM cmdev.EMP ORDER BY 2;  

-- mysql 特有LIMIT指令，查詢第10筆到第15筆資料
SELECT * FROM WORLD LIMIT 10,5;

-- 查詢員工編號、姓名與月薪，並依月薪由大到小排序，並只顯示前3名
SELECT EMPNO,ENAME,salary FROM cmdev.EMP ORDER by salary DESC LIMIT 3;

-- 查詢員工編號、姓名與月薪，並依月薪由小到大排序，並只顯示前3名
SELECT EMPNO,ENAME,salary FROM cmdev.EMP ORDER by salary ASC LIMIT 3;
-- [ALL | DISTINCT]
-- ALL 指令，查詢所有員工的姓名，並包含重複值
SELECT ALL ENAME FROM CMDEV.EMP;

-- DISTINCT 指令，查詢所有員工的職稱，並排除重複值
SELECT DISTINCT JOB FROM CMDEV.EMP;

-- DISTINCT 指令，查詢所有國家的政府形式，並排除重複值
SELECT DISTINCT GovernmentForm FROM WORLD.country;

SELECT DISTINCT Continent FROM WORLD.country ORDER BY Continent;

-- **PDF 習題**
-- 1
-- 查詢國家代碼、國家名稱與人口，並篩選人口大於 5000000 的國家
SELECT CODE,NAME,Population FROM WORLD.country WHERE Population > 5000000;

-- 2
-- 查詢洲別、國家代碼與國家名稱，並篩選洲別為 EUROPE、ASIA、AFRICA 的國家
SELECT Continent,CODE,NAME FROM world.country WHERE Continent IN("EUROPE","ASIA","AFRICA");

-- 3
-- 查詢國家代碼、國家名稱與人口，並篩選洲別為 MIDDLE EAST 的國家，並依人口由小到大排序
SELECT CODE,NAME,Population FROM WORLD.country WHERE REGION ="MIDDLE EAST" ORDER BY Population ; 


-- =============================================下午=============================================
-- 數值
-- 字串(字元)
-- 日期／時間
-- 空值
-- 布林值 TRUE 或 FALSE (1/0)
 
 -- 查詢國家人口，並依人口由大到小排序
 SELECT POPULATION *100000000 ,POPULATION*100000000 FROM WORLD.country WHERE CODE ="CHN";

-- MySQL DOUBLE 類型說明：DOUBLE 是一種浮點數值類型，用於儲存近似的十進位數值，可支援大範圍與小範圍的數值，精度最多可達約30位小數
-- Double 類型 最多30位小數點
SELECT 0.09876543210098765432100987654321 /2;

-- MySQL　１０Ｅ２　＝１00
SELECT 10E2;

-- MySQL　０．１＋０．１　＝０．２
SELECT 0.1+0.1

-- MySQL　０．１＋０．１　＝０．２
SELECT 0.1e0+0.1e0;

SELECT 1+'2'

-- MySQL　０．１＋０．１　＝０．２
-- 1.1e0+0.1e0=1.2e0 (True)
SELECT 0.1+0.2=0.3;

-- MySQL　１＋２　＝３
SELECT 1+'2';

-- MySQL　２．１＋１．２　＝３．３
-- Calculate the sum of string-converted numeric value and scientific notation numeric value
SELECT '2E1' + 1E2;
-- MySQL converts non-numeric string 'A' to 0 in numeric calculation, resulting in 1 + 0 = 1
SELECT 1 + 'A';
SELECT 1 +'A';

-- MySQL　'ABC' || 'EFG' =　 0
SELECT  'ABC' || 'EFG';


-- 啟用字符串拼接功能，使 || 運算子可正確拼接字符串，若未設定此參數，MySQL 會將 || 視為邏輯或運算子，返回數值 0
SET SQL_MODE ="PIPES_AS_CONCAT";
SELECT  'ABC' || 'EFG';

 
 
 