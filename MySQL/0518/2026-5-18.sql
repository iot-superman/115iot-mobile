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





