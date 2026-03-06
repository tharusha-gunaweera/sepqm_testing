-- Create databases for microservices
CREATE DATABASE IF NOT EXISTS products CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS orders CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS inventory CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Grant privileges to root user
GRANT ALL PRIVILEGES ON products.* TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON orders.* TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON inventory.* TO 'root'@'localhost' WITH GRANT OPTION;

-- Flush privileges to apply changes
FLUSH PRIVILEGES;

-- Verify databases were created
SHOW DATABASES LIKE '%product%';
SHOW DATABASES LIKE '%order%';
SHOW DATABASES LIKE '%inventory%';

