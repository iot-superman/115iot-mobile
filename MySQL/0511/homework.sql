-- #02 建立一個名稱為DB1的資料庫
-- 朱星念
-- •
-- 下午2:46
-- 編碼為big5，編碼排序為big5_bin
CREATE DATABASE DB1 CHARACTER SET big5 COLLATE big5_bin;


-- #01 請用指令建立一個名稱為TESTDB的資料庫 
CREATE SCHEMA IF NOT EXISTS TESTDB;   -- DDL



-- #03 查詢顯示world資料庫中city資料表所有的資料


SELECT * FROM  WORLD.CITY


-- 請查詢顯示cmdev資料庫中emp資料表

SELECT empno as 員工編號 , ename as 員工姓名 ,hiredate as 雇用日期, salary as 薪資 FROM cmdev.emp WHERE salary>=1000 

