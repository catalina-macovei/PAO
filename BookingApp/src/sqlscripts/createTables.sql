
CREATE TABLE `booking`.`account_balance` (
                                      `accountNr` INT NOT NULL AUTO_INCREMENT,
                                      `amount` DECIMAL(10, 2) NULL,
                                      PRIMARY KEY (`accountNr`));

CREATE TABLE `booking`.`landlord` (
                                     `id` INT NOT NULL AUTO_INCREMENT,
                                     `name` VARCHAR(45) NULL,
                                     `password` VARCHAR(45) NULL,
                                     `email` VARCHAR(45) NULL,
                                     `accountNr` INT NULL,
                                     FOREIGN KEY (`accountNr`) REFERENCES account_balance (`accountNr`) ON DELETE CASCADE,
                                     PRIMARY KEY (`id`));

CREATE TABLE `booking`.`customer` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `name` VARCHAR(45) NULL,
                                      `password` VARCHAR(45) NULL,
                                      `email` VARCHAR(45) NULL,
                                      `accountNr` INT NULL,
                                      FOREIGN KEY (`accountNr`) REFERENCES account_balance (`accountNr`) ON DELETE CASCADE,
                                      PRIMARY KEY (`id`));

CREATE TABLE `booking`.`address` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `country` VARCHAR(45) NULL,
                                      `city` VARCHAR(45) NULL,
                                      `street` VARCHAR(45) NULL,
                                      `number` INT NULL,
                                      PRIMARY KEY (`id`));

CREATE TABLE `booking`.`payment` (
                                     `id` INT NOT NULL AUTO_INCREMENT,
                                     `amount` DECIMAL(10, 2) NULL,
                                     `status` VARCHAR(45) NULL,
                                     PRIMARY KEY (`id`));

CREATE TABLE `booking`.`house` (
                                     `id` INT NOT NULL AUTO_INCREMENT,
                                     `name` VARCHAR(45) NULL,
                                     `address`  INT NULL,
                                     `landlord` INT NULL,
                                     `price` DECIMAL(10, 2) NULL,
                                     `yardSize` DECIMAL(10, 2) NULL,
                                     FOREIGN KEY (`landlord`) REFERENCES landlord (`id`) ON DELETE CASCADE,
                                     FOREIGN KEY (`address`) REFERENCES address (`id`) ON DELETE CASCADE,
                                     PRIMARY KEY (`id`));

CREATE TABLE `booking`.`apartment` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `name` VARCHAR(45) NULL,
                                       `address`  INT NULL,
                                       `landlord` INT NULL,
                                       `price` DECIMAL(10, 2) NULL,
                                       `floorNr` INT NULL,
                                       FOREIGN KEY (`landlord`) REFERENCES landlord (`id`) ON DELETE CASCADE,
                                       FOREIGN KEY (`address`) REFERENCES address (`id`) ON DELETE CASCADE,
                                       PRIMARY KEY (`id`));

CREATE TABLE `booking`.`booking` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `customer` INT NULL,
                                       `house` INT NULL,
                                       `apartment` INT NULL,
                                       `start_date` DATE NULL,
                                       `end_date` DATE NULL,
                                       `payment` INT NULL,
                                       FOREIGN KEY (`payment`) REFERENCES payment (`id`) ,
                                       FOREIGN KEY (`apartment`) REFERENCES apartment (`id`),
                                       FOREIGN KEY (`house`) REFERENCES house (`id`),
                                       FOREIGN KEY (`customer`) REFERENCES customer (`id`) ON DELETE CASCADE,
                                       PRIMARY KEY (`id`));

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_apartment FOREIGN KEY (apartment) REFERENCES apartment(id) ON DELETE CASCADE;

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_house FOREIGN KEY (house) REFERENCES house(id) ON DELETE CASCADE;
