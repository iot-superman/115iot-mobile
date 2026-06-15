-- #22
SELECT 
    CountryCode AS '國家代碼',
    Name AS '城市名稱', 
    Population AS '城市人口數'
FROM world.city
WHERE CountryCode IN (
    (SELECT Code 
    FROM world.country 
    WHERE Population BETWEEN 3000000 AND 6000000)
)
ORDER BY CountryCode, Name;

-- #23

USE CMDEV


-- #23 使用子查詢 
-- 1. 使用cmdev的emp / dept資料
-- 2. 查詢薪資大於3000元以上的部門有哪些?
SELECT  deptno,
    dname FROM dept 
WHERE deptno IN (
SELECT DISTINCT deptno FROM emp WHERE salary >3000
);


--#24

-- #24 分別建立兩個table，名稱自訂，資料表內容分別都有一個欄位n

-- 1. 工讀生資料表 內含資料 C,D
-- 2. 員工資料表 內含資料 A,B,C,D,E,F,G
-- 3. 請選擇用any 或 all 將上述兩個資料表做運算，顯示員工中不是工讀生的資料。

-- 1
CREATE TABLE parttime (
    n CHAR(1)
);
INSERT INTO parttime (n) VALUES ('C'), ('D');

-- 2
CREATE TABLE employee (
    n CHAR(1)
);
INSERT INTO employee (n) VALUES ('A'), ('B'), ('C'), ('D'), ('E'), ('F'), ('G');

-- 3
SELECT * FROM employee 
WHERE n <> ALL (SELECT n FROM parttime);

