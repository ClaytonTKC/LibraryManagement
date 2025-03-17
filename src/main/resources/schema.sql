DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS inventories;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS transactions;

CREATE TABLE IF NOT EXISTS members (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    memberid VARCHAR(50) UNIQUE,

    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50),

    street VARCHAR(50),
    city VARCHAR(50),
    postal VARCHAR(50),
    province VARCHAR(50),

    phone_number VARCHAR(50),
    phone_type VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS inventories (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    inventoryid VARCHAR(50) UNIQUE NOT NULL,
    bookid VARCHAR(50) NOT NULL,

    quantity INTEGER,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS books (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bookid VARCHAR(50) UNIQUE NOT NULL,
    authorid VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    publisher VARCHAR(100),
    released TIMESTAMP,
    availability VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    transactionid VARCHAR(50) UNIQUE NOT NULL,

    memberid VARCHAR(50) NOT NULL,
    bookid VARCHAR(50) NOT NULL,

    transaction_date TIMESTAMP,

    status VARCHAR(50),

    method VARCHAR(50),
    currency VARCHAR(50),
    amount DOUBLE
);
