-- Drop constraints if they were added separately
ALTER TABLE `booking`.`booking` DROP FOREIGN KEY `fk_booking_apartment`;
ALTER TABLE `booking`.`booking` DROP FOREIGN KEY `fk_booking_house`;

-- Drop the `booking` table first, which has foreign keys referencing `apartment`, `house`, `customer`, `landlord`, and `payment`
DROP TABLE IF EXISTS `booking`.`booking`;

-- Drop the `apartment` and `house` tables, which reference `landlord` and `address`
DROP TABLE IF EXISTS `booking`.`apartment`;
DROP TABLE IF EXISTS `booking`.`house`;

-- Drop the `landlord`, `customer`, and `address` tables, which have no further dependencies now
DROP TABLE IF EXISTS `booking`.`landlord`;
DROP TABLE IF EXISTS `booking`.`customer`;
DROP TABLE IF EXISTS `booking`.`address`;

-- Drop the `payment` table, which has no further dependencies now
DROP TABLE IF EXISTS `booking`.`payment`;

-- Finally, drop the `account_balance` table
DROP TABLE IF EXISTS `booking`.`account_balance`;
