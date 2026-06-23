-- **建立一個買賣業之進貨/銷貨/存貨系統之資料庫模型系統。


-- **該系統包含：


-- * 客戶基本資料表(客戶編號/客戶名稱/地址/電話/員工照片/負責員工)


-- * 產品基本資料表(產品編號/產品名稱/價格/產品照片/類別代號/供應商編號)


-- * 產品類別基本資料表(類別代號/類別名稱/供應商編號)


-- * 員工基本資料表(員工編號/員工姓名/地址/電話/員工照片/部門)


-- * 部門基本資料表(部門代號/部門名稱/部門主管-員工)

-- * 供應商基本資料表(供應商編號/供應商名稱/地址/連絡電話)


-- * 出貨紀錄(表頭/表身-明細)


-- * 進貨紀錄(表頭/表身-明細)


-- **建立資料表之間的關聯邏輯

-- ==========================================
-- STEP 1 建立資料庫
-- ==========================================

DROP DATABASE IF EXISTS WORK1;

CREATE DATABASE WORK1
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE WORK1;


-- ==========================================
-- STEP 2 建立部門資料表
-- ==========================================

CREATE TABLE Department
(
    department_no VARCHAR(10) PRIMARY KEY,
    department_name VARCHAR(50),
    manager_employee_no VARCHAR(10)
);


-- ==========================================
-- STEP 3 建立員工資料表
-- ==========================================

CREATE TABLE Employee
(
    employee_no VARCHAR(10) PRIMARY KEY,
    employee_name VARCHAR(50),
    address VARCHAR(100),
    phone VARCHAR(20),
    employee_photo VARCHAR(255),
    department_no VARCHAR(10),

    FOREIGN KEY (department_no)
    REFERENCES Department(department_no)
);


-- ==========================================
-- STEP 4 建立供應商資料表
-- ==========================================

CREATE TABLE Supplier
(
    supplier_no VARCHAR(10) PRIMARY KEY,
    supplier_name VARCHAR(100),
    address VARCHAR(100),
    contact_phone VARCHAR(20)
);


-- ==========================================
-- STEP 5 建立產品類別資料表
-- ==========================================

CREATE TABLE Category
(
    category_no VARCHAR(10) PRIMARY KEY,
    category_name VARCHAR(50),
    supplier_no VARCHAR(10),

    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no)
);


-- ==========================================
-- STEP 6 建立產品資料表
-- ==========================================

CREATE TABLE Product
(
    product_no VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(100),
    price DECIMAL(10,2),
    product_photo VARCHAR(255),
    category_no VARCHAR(10),
    supplier_no VARCHAR(10),

    FOREIGN KEY (category_no)
    REFERENCES Category(category_no),

    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no)
);


-- ==========================================
-- STEP 7 建立客戶資料表
-- ==========================================

CREATE TABLE Customer
(
    customer_no VARCHAR(10) PRIMARY KEY,
    customer_name VARCHAR(100),
    address VARCHAR(100),
    phone VARCHAR(20),
    customer_photo VARCHAR(255),
    employee_no VARCHAR(10),

    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- STEP 8 建立出貨表頭
-- ==========================================

CREATE TABLE Sales_Order
(
    sales_no VARCHAR(10) PRIMARY KEY,
    sales_date DATE,
    customer_no VARCHAR(10),
    employee_no VARCHAR(10),

    FOREIGN KEY (customer_no)
    REFERENCES Customer(customer_no),

    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- STEP 9 建立出貨明細
-- ==========================================

CREATE TABLE Sales_Detail
(
    sales_no VARCHAR(10),
    product_no VARCHAR(10),
    quantity INT,
    unit_price DECIMAL(10,2),
    amount DECIMAL(10,2),

    PRIMARY KEY (sales_no, product_no),

    FOREIGN KEY (sales_no)
    REFERENCES Sales_Order(sales_no),

    FOREIGN KEY (product_no)
    REFERENCES Product(product_no)
);


-- ==========================================
-- STEP 10 建立進貨表頭
-- ==========================================

CREATE TABLE Purchase_Order
(
    purchase_no VARCHAR(10) PRIMARY KEY,
    purchase_date DATE,
    supplier_no VARCHAR(10),
    employee_no VARCHAR(10),

    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no),

    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- STEP 11 建立進貨明細
-- ==========================================

CREATE TABLE Purchase_Detail
(
    purchase_no VARCHAR(10),
    product_no VARCHAR(10),
    quantity INT,
    unit_price DECIMAL(10,2),
    amount DECIMAL(10,2),

    PRIMARY KEY (purchase_no, product_no),

    FOREIGN KEY (purchase_no)
    REFERENCES Purchase_Order(purchase_no),

    FOREIGN KEY (product_no)
    REFERENCES Product(product_no)
);


-- ==========================================
-- STEP 12 建立部門主管外來鍵
-- ==========================================

ALTER TABLE Department
ADD CONSTRAINT FK_Department_Manager
FOREIGN KEY (manager_employee_no)
REFERENCES Employee(employee_no);


-- ==========================================
-- STEP 13 查看所有資料表
-- ==========================================

SHOW TABLES;


-- ==========================================
-- STEP 14 查看資料表結構
-- ==========================================

DESC Department;
DESC Employee;
DESC Supplier;
DESC Category;
DESC Product;
DESC Customer;
DESC Sales_Order;
DESC Sales_Detail;
DESC Purchase_Order;
DESC Purchase_Detail;


-- ==========================================
-- STEP 15 查詢所有資料
-- ==========================================

SELECT * FROM Department;
SELECT * FROM Employee;
SELECT * FROM Supplier;
SELECT * FROM Category;
SELECT * FROM Product;
SELECT * FROM Customer;
SELECT * FROM Sales_Order;
SELECT * FROM Sales_Detail;
SELECT * FROM Purchase_Order;
SELECT * FROM Purchase_Detail;