-- DROP SCHEMA IF EXISTS ecommerce; 
CREATE SCHEMA ecommerce;

USE ecommerce;

-- DROP TABLE IF EXISTS products; 
CREATE TABLE products(pid INT AUTO_INCREMENT,
description TEXT NOT NULL,
product_name VARCHAR(255) NOT NULL,
price DOUBLE NOT NULL,
quantity INT NOT NULL,
PRIMARY KEY(pid)
);

-- DROP TABLE IF EXISTS users;
CREATE TABLE users(
id INT AUTO_INCREMENT,
firstname VARCHAR(100) NOT NULL,
lastname VARCHAR(100) NOT NULL,
username VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
email VARCHAR(50) NOT NULL,
phone VARCHAR(15) NOT NULL,
address VARCHAR(255) NOT NULL,
role enum("admin","user","guest") NOT NULL,
isloggedin BOOLEAN,
isactive BOOLEAN,
PRIMARY KEY(id)
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    total_amount DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE orderDetails (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Products(pid)
);

INSERT INTO products (description, product_name, price, quantity) 
VALUES ('High-quality wireless headphones', 'Wireless Headphones', 99.99, 50);


INSERT INTO users (firstname, lastname, username, password, email, phone, address, role, isloggedin, isactive) 
VALUES ('John', 'Doe', 'johndoe', 'securepassword123', 'johndoe@example.com', '1234567890', '123 Main St, Anytown, USA', 'user', FALSE, TRUE);


INSERT INTO orders (user_id, total_amount) 
VALUES (1, 199.98);

INSERT INTO orderDetails (order_id, product_id, quantity, price) 
VALUES (1, 1, 2, 99.99);

