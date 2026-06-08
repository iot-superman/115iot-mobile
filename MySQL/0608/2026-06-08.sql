-- https://chatgpt.com/s/m_6a26190507388191a74575c8f1910e0a

-- inner join 內部結合

-- corss join 交叉結合

SELECT * FROM cmdev.emp;


SELECT EMPNO,ENAME, DEPT.DNAME,DEPT.LOCATION
FROM CMDEV.EMP,CMDEV.DEPT
WHERE EMP.DEPTNO=DEPT.DEPTNO;


SELECT EMPNO,ENAME, DEPT.DNAME,DEPT.LOCATION
FROM CMDEV.EMP
INNER JOIN CMDEV.DEPT
ON EMP.DEPTNO=DEPT.DEPTNO;


SELECT EMPNO,ENAME,DEPTNO
FROM CMDEV.EMP;


-- ////
-- =====================================================
-- 查詢 cmdev.emp 資料表所有欄位資料
-- * 代表全部欄位
-- =====================================================
SELECT *
FROM cmdev.emp;
-- =====================================================
-- 舊式 JOIN 寫法（ANSI-89）
-- 使用 WHERE 條件進行資料表關聯
--
-- 查詢：
-- 員工編號
-- 員工名稱
-- 部門名稱
-- 部門位置
--
-- 關聯條件：
-- EMP.DEPTNO = DEPT.DEPTNO
-- =====================================================
SELECT
    EMPNO,
    ENAME,
    DEPT.DNAME,
    DEPT.LOCATION
FROM
    CMDEV.EMP,
    CMDEV.DEPT
WHERE
    EMP.DEPTNO = DEPT.DEPTNO;


-- =====================================================
-- INNER JOIN 標準寫法（ANSI-92）
-- 目前業界較推薦使用
--
-- 功能：
-- 只顯示 EMP 與 DEPT 有成功配對的資料
--
-- INNER JOIN：
-- 兩張表必須符合 ON 條件才會顯示
-- =====================================================
SELECT
    EMPNO,
    ENAME,
    DEPT.DNAME,
    DEPT.LOCATION
FROM
    CMDEV.EMP
INNER JOIN
    CMDEV.DEPT
ON
    EMP.DEPTNO = DEPT.DEPTNO;


-- =====================================================
-- 查詢員工基本資料
--
-- 顯示：
-- 員工編號
-- 員工名稱
-- 部門編號
-- =====================================================
SELECT
    EMPNO,
    ENAME,
    DEPTNO
FROM
    CMDEV.EMP;
    
SELECT WORLD.COUNTRY CO INNER JOIN WORLD.CITY CI ON CAPITAL=ID;    
FROM WORLD.COUNTRY CO INNER　JOIN WORLD.CITY CI ON CASPITASL=ID;
    
    
SELECT EMPNO,ENAME,DEPTNO FROM CMDEV.EMP;

    
    
    
    