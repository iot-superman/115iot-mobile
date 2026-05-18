--ｈｏｍｅ　ｗｏｒｋ　＃６：#06 使用world資料庫的city資料表
-- 
-- 
-- 1. 顯示id, name欄位
-- 2. 過濾條件為CountryCode欄位中開頭有t的資料。
-- 你的答案

SELECT ID,NAME FROM world.city WHERE CountryCode LIKE 't%';


-- #07 使用world中的city資料表
 
-- 1. 顯示id, name, CountryCode 欄位
-- 2. 過濾條件為CountryCode欄位中有T的資料。

SELECT ID,NAME,CountryCode FROM world.city WHERE CountryCode LIKE '%T%';


---
-- #08 使用world資料庫的city資料表
 
-- 1. 呈現所有欄位。
-- 2. 找出name欄位中有4個字的資料。

SELECT * FROM world.city WHERE NAME LIKE '____';





-- #09 使用cmdev資料庫

-- 1. 使用emp資料表。
-- 2. 找出並顯示該公司有幾種職務名稱。

SELECT DISTINCT JOB FROM cmdev.EMP;
