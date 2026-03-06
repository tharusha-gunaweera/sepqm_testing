-- ============================================
-- COMPLETE TEST DATA SETUP
-- ============================================

-- ============================================
-- 1. CREATE PRODUCT DATA
-- ============================================
USE products;

INSERT INTO product (id, product_name, for_sale) VALUES
(1, 'Laptop', 1),
(2, 'Desktop Computer', 1),
(3, 'Tablet', 1),
(4, 'Smartphone', 1),
(5, 'Monitor', 0);

SELECT 'Products inserted:' as status;
SELECT COUNT(*) as total_products FROM product;

-- ============================================
-- 2. CREATE INVENTORY DATA
-- ============================================
USE inventory;

INSERT INTO inventory (id, item_id, product_id, quantity) VALUES
(1, 1, 1, 100),
(2, 2, 2, 50),
(3, 3, 3, 75),
(4, 4, 4, 200),
(5, 5, 5, 0);

SELECT 'Inventory items inserted:' as status;
SELECT COUNT(*) as total_inventory FROM inventory;
SELECT * FROM inventory;

-- ============================================
-- 3. VERIFICATION
-- ============================================
SELECT 'Data Setup Complete!' as final_status;

