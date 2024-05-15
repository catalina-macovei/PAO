INSERT INTO account_balance (amount) VALUES (500.00);
INSERT INTO account_balance (amount) VALUES (1000.00);
INSERT INTO account_balance (amount) VALUES (750.50);
INSERT INTO account_balance (amount) VALUES (200.25);
INSERT INTO account_balance (amount) VALUES (1500.75);
INSERT INTO account_balance (amount) VALUES (3000.75);

INSERT INTO landlord (name, password, email, accountNr) VALUES ('John Smith', 'password123', 'john@example.com', 1);
INSERT INTO landlord (name, password, email, accountNr) VALUES ('Alice Johnson', 'alice123', 'alice@example.com', 2);
INSERT INTO landlord (name, password, email, accountNr) VALUES ('Michael Brown', 'mike456', 'michael@example.com', 3);

INSERT INTO customer (name, password, email, accountNr) VALUES ('Sarah Johnson', 'sarah123', 'sarah@example.com', 4);
INSERT INTO customer (name, password, email, accountNr) VALUES ('Robert Lee', 'robert456', 'robert@example.com', 5);
INSERT INTO customer (name, password, email, accountNr) VALUES ('Jennifer Martinez', 'jennifer789', 'jennifer@example.com', 6);

INSERT INTO address (country, city, street, number) VALUES ('USA', 'New York', 'Broadway', 123);
INSERT INTO address (country, city, street, number) VALUES ('UK', 'London', 'Oxford Street', 45);
INSERT INTO address (country, city, street, number) VALUES ('France', 'Paris', 'Champs-Elysées', 67);
INSERT INTO address (country, city, street, number) VALUES ('Germany', 'Berlin', 'Alexanderplatz', 89);
INSERT INTO address (country, city, street, number) VALUES ('Italy', 'Rome', 'Via Condotti', 101);
INSERT INTO address (country, city, street, number) VALUES ('Spain', 'Barcelona', 'Passeig de Gràcia', 123);
INSERT INTO address (country, city, street, number) VALUES ('Japan', 'Tokyo', 'Shibuya', 1);

INSERT INTO payment (amount, status) VALUES (10000.75, 'Paid');
INSERT INTO payment (amount, status) VALUES (30000.25, 'Paid');
INSERT INTO payment (amount, status) VALUES (40000.00, 'Paid');

INSERT INTO house (name, address, landlord, price, yardSize) VALUES ("house 1",1, 1, 3000.00, 500.00);
INSERT INTO house (name, address, landlord, price, yardSize) VALUES ("house 2",2, 2, 2000.00, 750.00);
INSERT INTO house (name, address, landlord, price, yardSize) VALUES ("house 3",3, 3, 1800.00, 600.00);
INSERT INTO house (name, address, landlord, price, yardSize) VALUES ("house 4",4, 1, 1700.00, 550.00);
INSERT INTO house (name, address, landlord, price, yardSize) VALUES ("house 5",5, 2, 2200.00, 800.00);

INSERT INTO apartment (name, address, landlord, price, floorNr) VALUES ("apartment 1", 6, 1, 1200.00, 2);
INSERT INTO apartment (name, address, landlord, price, floorNr) VALUES ("apartment 2", 7, 3, 1500.00, 3);

INSERT INTO booking (customer, house, apartment, start_date, end_date, payment) VALUES (1, NULL, 1, '2023-06-01', '2024-01-30', 1);
INSERT INTO booking (customer, house, apartment, start_date, end_date, payment) VALUES (2, 2, NULL, '2023-07-01', '2024-03-30', 2);
INSERT INTO booking (customer, house, apartment, start_date, end_date, payment) VALUES (3, NULL, 2, '2023-08-01', '2024-03-30', 3);
