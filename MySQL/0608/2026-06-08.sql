-- https://chatgpt.com/s/m_6a26190507388191a74575c8f1910e0a

-- https://chatgpt.com/s/m_6a261bf20ce08191b6e205570a1198ef


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

-- 合併查詢 (查詢的欄位名稱要相同)
SELECT REGION,NAME,POPULATION FROM WORLD.COUNTRY WHERE REGION='SOUTHEAST ASIA' AND POPULATION<20000000
UNION
SELECT REGION,NAME,POPULATION FROM WORLD.COUNTRY WHERE REGION='EASTERN ASIA' AND POPULATION<10000000
UNION  
SELECT REGION,NAME,POPULATION FROM WORLD.COUNTRY WHERE REGION='EASTERN AFRICA' AND POPULATION<10000000;


    
    
SELECT REGION, NAME, POPULATION
FROM WORLD.COUNTRY
WHERE REGION='SOUTHEAST ASIA'
AND POPULATION <2000000

UNION ALL

SELECT REGION, NAME, POPULATION
FROM WORLD.COUNTRY
WHERE REGION='EASTERN ASIA'
AND POPULATION <1000000

UNION ALL

SELECT REGION, NAME, POPULATION
FROM WORLD.COUNTRY
WHERE REGION='EASTERN AFRICA'
AND POPULATION <1000000;    

-- 實作題
-- 習題1
-- 前兩題的欄位名稱不需要用中文字型別名（AS 員工編號 等），直接使用原本的欄位代號 empno, ename, job, deptno, dname 即可。
-- 第三題的輸出畫面中，欄位名稱被改成了 Asia 與 Population，且左邊多了一個分類標籤。這在 SQL 中通常需要配合 MIN() 與 MAX() 的別名，或是透過 UNION 來達成畫面上的
-- 1
SELECT e.empno, e.ename, e.job, e.deptno, d.dname
FROM cmdev.emp e
INNER JOIN cmdev.dept d ON e.deptno = d.deptno
USING(DEPTNO);


-- 2
-- 2. 查詢所有員工資料（包含部門編號為 NULL 的員工）
-- 畫面的倒數第二行出現了員工 BLAKE（其 deptno 與 dname 皆顯示為 NULL），這證實了必須使用左外連接。
SELECT e.empno, e.ename, e.job, e.deptno, d.dname
FROM cmdev.emp e
LEFT OUTER JOIN cmdev.dept d ON e.deptno = d.deptno;


-- 3
-- 3. 查詢亞洲國家最小與最大人口數
-- 從輸出畫面來看，它將「最小」與「最大」拆成了兩列（Rows）顯示，而不是傳統的兩欄。
-- 要做出這種「橫變直」的呈現效果，最標準且符合題意的做法是使用 UNION 將兩個查詢結果上下拼接，並刻意塞入字串 'Minimum' 與 'Maximum' 作為分類：

SELECT 'Minimum' AS Asia, MIN(Population) AS Population FROM world.country WHERE Continent = 'Asia'
UNION
SELECT 'Maximum' AS Asia, MAX(Population) AS Population FROM world.country WHERE Continent = 'Asia';

    