-- **[這個有依外來鍵的相依性來改動create TABLE的順序ALTER]

-- 題目：
-- *建立一個買賣業之進貨/銷貨/存貨系統之資料庫模型系統。


-- **該系統包含：


-- * 1客戶基本資料表(客戶編號/客戶名稱/地址/電話/員工照片/負責員工)


-- * 2產品基本資料表(產品編號/產品名稱/價格/產品照片/類別代號/供應商編號)


-- * 3產品類別基本資料表(類別代號/類別名稱/供應商編號)


-- * 4員工基本資料表(員工編號/員工姓名/地址/電話/員工照片/部門)


-- * 5部門基本資料表(部門代號/部門名稱/部門主管-員工)

-- * 6供應商基本資料表(供應商編號/供應商名稱/地址/連絡電話)


-- * 7出貨紀錄(表頭/表身-明細)


-- * 8進貨紀錄(表頭/表身-明細)


-- **建立資料表之間的關聯邏輯



-- ==========================================
-- 買賣業進貨 / 銷貨 / 存貨系統
-- WORK1.SQL
-- ==========================================

-- ==========================================
-- STEP 1 建立資料庫
-- ==========================================

DROP DATABASE IF EXISTS SALES;

CREATE DATABASE SALES
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE SALES;


-- ==========================================
-- STEP 2 部門基本資料表5
-- ==========================================

CREATE TABLE Department
(
    department_no VARCHAR(3) COMMENT '部門代號' PRIMARY KEY,
    department_name VARCHAR(45) COMMENT '部門名稱',
    manager_employee_no VARCHAR(3) COMMENT '部門主管'
);


-- ==========================================
-- STEP 3 員工基本資料表2
-- ==========================================

CREATE TABLE Employee
(
    employee_no VARCHAR(3) COMMENT '員工編號' PRIMARY KEY,
    employee_name VARCHAR(45) COMMENT '員工姓名',
    address VARCHAR(45) COMMENT '地址',
    phone VARCHAR(20) COMMENT '電話',
    employee_photo BLOB COMMENT '員工照片',
    department_no VARCHAR(3) COMMENT '部門代號',

    CONSTRAINT FK_Employee_Department
    FOREIGN KEY (department_no)
    REFERENCES Department(department_no)
);


-- ==========================================
-- STEP 4 建立部門主管外來鍵
-- ==========================================

ALTER TABLE Department
ADD CONSTRAINT FK_Department_Manager
FOREIGN KEY (manager_employee_no)
REFERENCES Employee(employee_no);


-- ==========================================
-- STEP 5 供應商基本資料表6
-- ==========================================

CREATE TABLE Supplier
(
    supplier_no VARCHAR(3) COMMENT '供應商編號' PRIMARY KEY,
    supplier_name VARCHAR(45) COMMENT '供應商名稱',
    address VARCHAR(45) COMMENT '地址',
    contact_phone VARCHAR(20) COMMENT '連絡電話'
);


-- ==========================================
-- STEP 6 產品類別基本資料表3
-- ==========================================

CREATE TABLE Category
(
    category_no VARCHAR(3) COMMENT '類別代號' PRIMARY KEY,
    category_name VARCHAR(45) COMMENT '類別名稱',
    supplier_no VARCHAR(3) COMMENT '供應商編號',

    CONSTRAINT FK_Category_Supplier
    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no)
);


-- ==========================================
-- STEP 7 產品基本資料表
-- ==========================================

CREATE TABLE Product
(
    product_no VARCHAR(3) COMMENT '產品編號' PRIMARY KEY,
    product_name VARCHAR(45) COMMENT '產品名稱',
    price DECIMAL(10,2) COMMENT '價格',
    product_photo BLOB COMMENT '產品照片',
    category_no VARCHAR(3) COMMENT '類別代號',
    supplier_no VARCHAR(3) COMMENT '供應商編號',

    CONSTRAINT FK_Product_Category
    FOREIGN KEY (category_no)
    REFERENCES Category(category_no),

    CONSTRAINT FK_Product_Supplier
    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no)
);


-- ==========================================
-- STEP 8 存貨資料表
-- ==========================================

CREATE TABLE Inventory
(
    product_no VARCHAR(3) COMMENT '產品編號' PRIMARY KEY,
    stock_qty INT COMMENT '庫存數量',
    safety_qty INT COMMENT '安全庫存量',
    last_update DATETIME COMMENT '最後更新時間',

    CONSTRAINT FK_Inventory_Product
    FOREIGN KEY (product_no)
    REFERENCES Product(product_no)
);


-- ==========================================
-- STEP 9 客戶基本資料表1
-- ==========================================

CREATE TABLE Customer
(
    customer_no VARCHAR(3) COMMENT '客戶編號' PRIMARY KEY,
    customer_name VARCHAR(45) COMMENT '客戶名稱',
    address VARCHAR(45) COMMENT '地址',
    phone VARCHAR(20) COMMENT '電話',
    customer_photo BLOB COMMENT '客戶照片',
    employee_no VARCHAR(3) COMMENT '負責員工',

    CONSTRAINT FK_Customer_Employee
    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- STEP 10 出貨紀錄表頭7
-- ==========================================

CREATE TABLE Sales_Order
(
    sales_no VARCHAR(3) COMMENT '出貨單號' PRIMARY KEY,
    sales_date DATE COMMENT '出貨日期',
    customer_no VARCHAR(3) COMMENT '客戶編號',
    employee_no VARCHAR(3) COMMENT '員工編號',

    CONSTRAINT FK_SalesOrder_Customer
    FOREIGN KEY (customer_no)
    REFERENCES Customer(customer_no),

    CONSTRAINT FK_SalesOrder_Employee
    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- STEP 11 出貨紀錄明細
-- ==========================================

CREATE TABLE Sales_Detail
(
    sales_no VARCHAR(3) COMMENT '出貨單號',
    product_no VARCHAR(3) COMMENT '產品編號',
    quantity INT COMMENT '數量',
    unit_price DECIMAL(10,2) COMMENT '單價',
    amount DECIMAL(10,2) COMMENT '金額',

    PRIMARY KEY (sales_no, product_no),

    CONSTRAINT FK_SalesDetail_SalesOrder
    FOREIGN KEY (sales_no)
    REFERENCES Sales_Order(sales_no),

    CONSTRAINT FK_SalesDetail_Product
    FOREIGN KEY (product_no)
    REFERENCES Product(product_no)
);


-- ==========================================
-- STEP 12 進貨紀錄表頭8
-- ==========================================

CREATE TABLE Purchase_Order
(
    purchase_no VARCHAR(3) COMMENT '進貨單號' PRIMARY KEY,
    purchase_date DATE COMMENT '進貨日期',
    supplier_no VARCHAR(3) COMMENT '供應商編號',
    employee_no VARCHAR(3) COMMENT '員工編號',

    CONSTRAINT FK_PurchaseOrder_Supplier
    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no),

    CONSTRAINT FK_PurchaseOrder_Employee
    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- STEP 13 進貨紀錄明細
-- ==========================================

CREATE TABLE Purchase_Detail
(
    purchase_no VARCHAR(3) COMMENT '進貨單號',
    product_no VARCHAR(3) COMMENT '產品編號',
    quantity INT COMMENT '數量',
    unit_price DECIMAL(10,2) COMMENT '單價',
    amount DECIMAL(10,2) COMMENT '金額',

    PRIMARY KEY (purchase_no, product_no),

    CONSTRAINT FK_PurchaseDetail_PurchaseOrder
    FOREIGN KEY (purchase_no)
    REFERENCES Purchase_Order(purchase_no),

    CONSTRAINT FK_PurchaseDetail_Product
    FOREIGN KEY (product_no)
    REFERENCES Product(product_no)
);


-- ==========================================
-- STEP 14 查看所有資料表
-- ==========================================

SHOW TABLES;


--- ==========================================
-- STEP 14 查看所有資料表
-- ==========================================

SHOW TABLES;


-- ==========================================
-- STEP 15 查看資料表結構 (依題目順序)
-- ==========================================

-- * 1客戶基本資料表
DESC Customer;

-- * 2產品基本資料表
DESC Product;

-- * 3產品類別基本資料表
DESC Category;

-- * 4員工基本資料表
DESC Employee;

-- * 5部門基本資料表
DESC Department;

-- * 6供應商基本資料表
DESC Supplier;

-- * 7出貨紀錄(表頭/表身-明細)
DESC Sales_Order;
DESC Sales_Detail;

-- * 8進貨紀錄(表頭/表身-明細)
DESC Purchase_Order;
DESC Purchase_Detail;

-- 另外附帶的存貨資料表
DESC Inventory;


-- ==========================================
-- STEP 16 查詢所有資料 (依題目順序)
-- ==========================================

-- * 1客戶基本資料表
SELECT * FROM Customer;

-- * 2產品基本資料表
SELECT * FROM Product;

-- * 3產品類別基本資料表
SELECT * FROM Category;

-- * 4員工基本資料表
SELECT * FROM Employee;

-- * 5部門基本資料表
SELECT * FROM Department;

-- * 6供應商基本資料表
SELECT * FROM Supplier;

-- * 7出貨紀錄(表頭/表身-明細)
SELECT * FROM Sales_Order;
SELECT * FROM Sales_Detail;

-- * 8進貨紀錄(表頭/表身-明細)
SELECT * FROM Purchase_Order;
SELECT * FROM Purchase_Detail;

-- 另外附帶的存貨資料表
SELECT * FROM Inventory;