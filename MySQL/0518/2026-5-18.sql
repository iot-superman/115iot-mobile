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

--MySQL　'ABC' || 'EFG' =　 ABCEFG

SELECT  'ABC' || 'EFG';

 -- MySQL　2026-05-18 + INTERVAL 30 DAY = '2026-06-17'
 SELECT '2026-5-18' + interval 30 day;

 
 -- MySQL　2026-05-18 + INTERVAL 90 YEAR = '2116-05-18'
 SELECT '2026-5-18' + interval 90 YEAR;
 
 -- 繁體中文: 年/月/日
 -- MySQL　2026-05-18 - INTERVAL 28 DAY = '2026-04-20'
SELECT '2026-5-18' - interval 28 DAY;

-- MySQL　2026-05-18 13:45:30 + INTERVAL 30 MINUTE = '2026-05-18 14:15:30'
SELECT '2026-5-18 13:45:30' + interval 30 MINUTE;

-- MySQL　2026-05-18 13:45:30 + INTERVAL 30 HOUR = '2026-05-18 16:45:30'
SELECT '2026-5-18 13:45:30' + interval 30 MINUTE;

-- **員工到公司已經幾天了？**
SELECT * FROM cmdev.emp;

-- **員工到公司已經幾小時了？**
-- curdate() 函數，返回當前日期的日期部分

SELECT ENAME,HIREDATE,DATEDIFF(CURDATE(), HIREDATE) AS 工作天數 FROM cmdev.emp;
-- **員工到公司已經幾小時了？** 
-- curtime() 函數，返回當前時間的時間部分
SELECT ENAME,HIREDATE,DATEDIFF(CURTIME(), HIREDATE) AS 工作天數 FROM cmdev.emp;

-- curtime() 函數，返回當前時間的時間部分
SELECT curtime();

-- **字串 **--
SELECT * FROM cmdev.emp;
SELECT ENAME,HIREDATE,DATEDIFF(CURDATE(), HIREDATE) AS DAYS FROM cmdev.emp;
SELECT CURTIME();
SELECT EMPNO,ENAME,salary,COMM,salary+COMM From cmdev.emp;

-- ifnull() 函數，如果 COMM 欄位為 NULL，則返回 0，否則返回 COMM 欄位的值
SELECT EMPNO,ENAME,salary,COMM,salary+ifnull(COMM,0) From cmdev.emp;


-- ** 字串函式 ** 
-- 將輸入的字串轉換為小寫
SELECT LOWER('ABC');
-- 將輸入的字串轉換為大寫
SELECT UPPER('abc');
-- 移除字串開頭的多餘空格
SELECT ltrim('    ABC')
-- 移除字串尾端的多餘空格
SELECT Rtrim('ABC           ');
-- 同時移除字串開頭與尾端的多餘空格
SELECT TRIM('   ABC   ');

-- 字串填充函數 LPAD 說明：在字串左側填充指定字符，使字串達到指定長度
-- LPAD 基本語法：LPAD(string, length, pad_string)
-- 範例：將國家名稱左側填充星號，使總長度達到50字符
SELECT LPAD(NAME, 50, '*') AS padded_name FROM world.country;

-- 字串填充函數 RPAD 說明：在字串右側填充指定字符，使字串達到指定長度
-- RPAD 基本語法：RPAD(string, length, pad_string)
-- 範例：將國家名稱右側填充星號，使總長度達到50字符
SELECT RPAD(NAME, 50, '*') AS padded_name FROM world.country;


-- LEFT() 函數：從字串左側擷取指定長度的字元，這裡擷取國家名稱的前3個字元
SELECT left(NAME,3),Name FROM  world.country;

-- RIGHT() 函數：從字串右側擷取指定長度的字元，這裡擷取國家名稱的後3個字元
SELECT right(NAME,3),Name FROM  world.country;

-- SUBSTRING() 函數：從指定位置開始擷取指定長度的字元，第一個參數是原字串，第二個是起始位置(從1開始計算)，第三個是要擷取的長度
-- 這裡從字串'A123456789'的第2個位置開始，擷取1個字元，會得到'1'
SELECT substring('A123456789',2,1);

-- 這裡從字串'A123456789'的第3個位置開始，擷取7個字元，會得到'2345678'
SELECT substring('A123456789',3,7);

-- 這裡從字串'A123456789'的第3個位置開始，擷取到字串結束，會得到'23456789'
SELECT substring('A123456789',3);


-- CONCAT_WS() 函數：將多個字串用指定的分隔符連接起來
-- 這裡將字串'ABC'和'DEF'用逗號連接起來，會得到'ABC,DEF'
SELECT  CONCAT_WS(',',"ABC","DEF");

-- 這裡將字串'ABC'和'DEF'用星號連接起來，會得到'ABC*DEF'
SELECT  CONCAT_WS('*',"ABC","DEF");


-- CONCAT() 函數：將多個字串用空格連接起來
-- 這裡將國家代碼、國家名稱、洲名稱用空格連接起來，會得到'CountryCode Name Continent'
SELECT concat(CODE,' ',NAME,' ',CONTINENT) FROM world.country;


-- 計算字串'資料庫'的位元組長度（在UTF-8編碼下返回6，因為每個中文字元佔用3位元組）
SELECT   length('資料庫');

-- 計算字串'ABCDE'的字元長度（返回5，因為它包含5個單位元組字元）
SELECT   char_length('ABCDE');

-- 計算字串'資料庫'的字元長度（返回3，因為它包含3個中文字元）
SELECT   char_length('資料庫');

-- LOCATE() 函數：在字串中查找指定字元或字串，返回其位置（從1開始計算）
-- 這裡查找國家名稱中是否有空格，有則返回空格的位置，沒有則返回0
SELECT NAME,LOCATE(' ',NAME) FROM world.country;


-- Extract first name from country names that contain a space
SELECT 
    NAME, LEFT(NAME, LOCATE(' ', NAME) - 1) FROM world.country
WHERE
    LOCATE(' ', NAME) <> 0;

-- ROUND() 函數：將數字四捨五入到指定小數位數
-- 這裡將123.345四捨五入到2位小數，會得到123.35
SELECT ROUND(123.345,2);

-- CEIL() 函數：將數字向上取整，返回大於等於該數字的最小整數
-- 這裡將123.345向上取整，會得到124
SELECT CEIL(123.345);

-- FLOOR() 函數：將數字向下取整，返回小於等於該數字的最大整數
-- 這裡將123.345向下取整，會得到123
SELECT FLOOR(123.345);

-- TRUNCATE() 函數：將數字截斷到指定小數位數
-- 這裡將123.456789截斷到3位小數，會得到123.456
SELECT truncate(123.456789,3);


-- 這裡將123.456789截斷到-2位小數，會得到123.45
SELECT truncate(123.456789,-2);

-- RAND() 函數：返回一個隨機數，範圍從0到1
-- 這裡返回一個隨機數，範圍從0到1
SELECT RAND();


 -- 這裡從world.country資料表中隨機取5筆資料
-- 這裡將資料按隨機數排序，取前5筆
 SELECT * FROM world.country ORDER BY RAND() LIMIT 5;


-- 這裡從10到20中隨機取1個整數


SELECT floor(10+RAND()*11)


-- Generate random integer between 1 and 49 inclusive
SELECT floor(1+RAND()*49)


-- Calculate the number of years since a country gained independence, sort by the calculated years in descending order
SELECT NAME, YEAR(CURDATE()) - IndepYear AS years_since_independence FROM WORLD.COUNTRY ORDER BY 2 DESC;
-- 取得本月的今日日期
SELECT DAY(CURDATE());
-- 取得本月的月份數字
SELECT MONTH(CURDATE());
-- 取得本週星期幾的完整名稱
SELECT DAYNAME(CURDATE());
-- 取得本週星期幾的索引 (1 = 星期日, 7 = 星期六)
SELECT DAYOFWEEK(CURDATE());
-- 取得本年的週數
SELECT WEEKOFYEAR(CURDATE());
-- 擷取當前日期的年份
SELECT EXTRACT(YEAR FROM CURDATE());

-- 取得員工到職日是星期幾  dayname sunday, monday, tuesday, wednesday, thursday, friday, saturday
SELECT dayname(HIREDATE) FROM cmdev.EMP ;













 
 
 
 
 
 
 
 