-- **[這個沒有有依外來鍵的相依性來改動create TABLE的順序ALTER]
-- 強制照老師的答題順序來CREATE TABLE

-- ==========================================
-- 買賣業進貨 / 銷貨 / 存貨系統
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
-- 暫時關閉外來鍵檢查，允許依任意順序建表
-- ==========================================
SET FOREIGN_KEY_CHECKS = 0;


-- ==========================================
-- * 1 客戶基本資料表
-- ==========================================
CREATE TABLE Customer
(
    customer_no VARCHAR(10) COMMENT '客戶編號' PRIMARY KEY,
    customer_name VARCHAR(45) COMMENT '客戶名稱',
    address VARCHAR(100) COMMENT '地址',
    phone VARCHAR(20) COMMENT '電話',
    customer_photo BLOB COMMENT '客戶照片',
    employee_no VARCHAR(10) COMMENT '負責員工',

    CONSTRAINT FK_Customer_Employee
    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- * 2 產品基本資料表
-- ==========================================
CREATE TABLE Product
(
    product_no VARCHAR(10) COMMENT '產品編號' PRIMARY KEY,
    product_name VARCHAR(45) COMMENT '產品名稱',
    price DECIMAL(10,2) COMMENT '價格',
    product_photo BLOB COMMENT '產品照片',
    category_no VARCHAR(10) COMMENT '類別代號',
    supplier_no VARCHAR(10) COMMENT '供應商編號',

    CONSTRAINT FK_Product_Category
    FOREIGN KEY (category_no)
    REFERENCES Category(category_no),

    CONSTRAINT FK_Product_Supplier
    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no)
);


-- ==========================================
-- * 3 產品類別基本資料表
-- ==========================================
CREATE TABLE Category
(
    category_no VARCHAR(10) COMMENT '類別代號' PRIMARY KEY,
    category_name VARCHAR(45) COMMENT '類別名稱',
    supplier_no VARCHAR(10) COMMENT '供應商編號',

    CONSTRAINT FK_Category_Supplier
    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no)
);


-- ==========================================
-- * 4 員工基本資料表
-- ==========================================
CREATE TABLE Employee
(
    employee_no VARCHAR(10) COMMENT '員工編號' PRIMARY KEY,
    employee_name VARCHAR(45) COMMENT '員工姓名',
    address VARCHAR(100) COMMENT '地址',
    phone VARCHAR(20) COMMENT '電話',
    employee_photo BLOB COMMENT '員工照片',
    department_no VARCHAR(10) COMMENT '部門代號',

    CONSTRAINT FK_Employee_Department
    FOREIGN KEY (department_no)
    REFERENCES Department(department_no)
);


-- ==========================================
-- * 5 部門基本資料表
-- ==========================================
CREATE TABLE Department
(
    department_no VARCHAR(10) COMMENT '部門代號' PRIMARY KEY,
    department_name VARCHAR(45) COMMENT '部門名稱',
    manager_employee_no VARCHAR(10) COMMENT '部門主管',

    CONSTRAINT FK_Department_Manager
    FOREIGN KEY (manager_employee_no)
    REFERENCES Employee(employee_no)
);


-- ==========================================
-- * 6 供應商基本資料表
-- ==========================================
CREATE TABLE Supplier
(
    supplier_no VARCHAR(10) COMMENT '供應商編號' PRIMARY KEY,
    supplier_name VARCHAR(45) COMMENT '供應商名稱',
    address VARCHAR(100) COMMENT '地址',
    contact_phone VARCHAR(20) COMMENT '連絡電話'
);


-- ==========================================
-- * 7 出貨紀錄 (表頭 / 表身-明細)
-- ==========================================
-- 表頭
CREATE TABLE Sales_Order
(
    sales_no VARCHAR(20) COMMENT '出貨單號' PRIMARY KEY,
    sales_date DATE COMMENT '出貨日期',
    customer_no VARCHAR(10) COMMENT '客戶編號',
    employee_no VARCHAR(10) COMMENT '員工編號',

    CONSTRAINT FK_SalesOrder_Customer
    FOREIGN KEY (customer_no)
    REFERENCES Customer(customer_no),

    CONSTRAINT FK_SalesOrder_Employee
    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);

-- 表身-明細
CREATE TABLE Sales_Detail
(
    sales_no VARCHAR(20) COMMENT '出貨單號',
    product_no VARCHAR(10) COMMENT '產品編號',
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
-- * 8 進貨紀錄 (表頭 / 表身-明細)
-- ==========================================
-- 表頭
CREATE TABLE Purchase_Order
(
    purchase_no VARCHAR(20) COMMENT '進貨單號' PRIMARY KEY,
    purchase_date DATE COMMENT '進貨日期',
    supplier_no VARCHAR(10) COMMENT '供應商編號',
    employee_no VARCHAR(10) COMMENT '員工編號',

    CONSTRAINT FK_PurchaseOrder_Supplier
    FOREIGN KEY (supplier_no)
    REFERENCES Supplier(supplier_no),

    CONSTRAINT FK_PurchaseOrder_Employee
    FOREIGN KEY (employee_no)
    REFERENCES Employee(employee_no)
);

-- 表身-明細
CREATE TABLE Purchase_Detail
(
    purchase_no VARCHAR(20) COMMENT '進貨單號',
    product_no VARCHAR(10) COMMENT '產品編號',
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
-- 額外附帶之存貨資料表 (原系統內含)
-- ==========================================
CREATE TABLE Inventory
(
    product_no VARCHAR(10) COMMENT '產品編號' PRIMARY KEY,
    stock_qty INT COMMENT '庫存數量',
    safety_qty INT COMMENT '安全庫存量',
    last_update DATETIME COMMENT '最後更新時間',

    CONSTRAINT FK_Inventory_Product
    FOREIGN KEY (product_no)
    REFERENCES Product(product_no)
);


-- ==========================================
-- 💡 核心關鍵：建表完成，重新開啟外來鍵檢查以確保資料完整性
-- ==========================================
SET FOREIGN_KEY_CHECKS = 1;


-- ==========================================
-- STEP 14 查看所有資料表
-- ==========================================
SHOW TABLES;


-- ==========================================
-- STEP 15 查看資料表結構 (依題目順序，純 DESC)
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

-- 額外附帶之存貨資料表
DESC Inventory;