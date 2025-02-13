-- Drop the database if it exists
DROP SCHEMA IF EXISTS `bookworms_online`;

-- Drop the user if it exists
DROP USER IF EXISTS `it2163_admin`;

-- Connect to the MySQL server as a user with sufficient privileges
-- Create the database schema
CREATE SCHEMA IF NOT EXISTS `bookworms_online`;

-- Create the user with the specified credentials
CREATE USER IF NOT EXISTS `it2163_admin` IDENTIFIED BY 'P@ssw0rd';

-- Grant full privileges on the `wss_checkin` database
GRANT GRANT OPTION ON bookworms_online.* TO `it2163_admin`;

-- Grant full privileges on all databases that start with `wss`
GRANT ALL PRIVILEGES ON `bookworms_online`.* TO `it2163_admin`;

SHOW GRANTS FOR `it2163_admin`;