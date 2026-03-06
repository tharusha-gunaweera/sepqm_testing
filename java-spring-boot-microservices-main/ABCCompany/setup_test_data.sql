-- SQL Script to Create Test Data for Microservices
-- Run this script in MySQL to set up all test data

-- ============================================
-- 1. CREATE PRODUCT DATA
-- ============================================
USE products;

-- Insert test products
INSERT INTO product (id, product_name, for_sale) VALUES
(1, 'Laptop', 1),
(2, 'Desktop Computer', 1),
(3, 'Tablet', 1),
(4, 'Smartphone', 1),
(5, 'Monitor', 0);

-- Verify
SELECT * FROM product;

-- ============================================
-- 2. CREATE INVENTORY DATA
-- ============================================
USE inventory;

-- Insert test inventory items
INSERT INTO inventory (id, item_id, product_id, quantity) VALUES
(1, 1, 1, 100),
(2, 2, 2, 50),
(3, 3, 3, 75),
(4, 4, 4, 200),
(5, 5, 5, 0);

-- Verify
SELECT * FROM inventory;

-- ============================================
-- 3. DATABASE READINESS CHECK
-- ============================================

-- Check Product Service Database
USE products;
SHOW TABLES;
SELECT COUNT(*) as total_products FROM product;

-- Check Inventory Service Database
USE inventory;
SHOW TABLES;
SELECT COUNT(*) as total_inventory_items FROM inventory;

-- Check Order Service Database
USE orders;
SHOW TABLES;

