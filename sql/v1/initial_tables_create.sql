create table IF NOT EXISTS books
(
    id       int auto_increment primary key,
    name     varchar(100),
    author   varchar(100),
    price    decimal(10, 2),
    quantity int
);

create table IF NOT EXISTS customers
(
    id        int auto_increment primary key,
    username  varchar(100),
    firstname varchar(100),
    lastname  varchar(100),
    address   text,
    phone     varchar(100),
    email     varchar(100)
);

create unique index customers_email_uindex
    on customers (email);


create table IF NOT EXISTS orders
(
    id           int auto_increment primary key,
    customer_id  int,
    order_date   date,
    total_amount decimal(10, 2),
    CONSTRAINT FK_ORDER_CUSTOMER_ID
        FOREIGN KEY (customer_id)
            REFERENCES customers (id)
);

create table IF NOT EXISTS order_item
(
    id       int auto_increment primary key,
    order_id int,
    book_id  int,
    quantity int,
    sub_total decimal(10,2),
    constraint UC_BOOK_ORDER unique (order_id, book_id)
);

create table IF NOT EXISTS inventory
(
    id       int auto_increment primary key,
    book_id  int,
    quantity int,
    version  int,
    CONSTRAINT FK_INVENTORY_BOOK_ID
        FOREIGN KEY (book_id)
            REFERENCES books (id)

);

create unique index FK_INVENTORY_BOOK_ID
    on inventory (book_id);

--view for statistics
create view monthly_order_statistic_view as
select
       uuid() as id,
       count(distinct o.id) as total_order_count,
       sum(oi.quantity)      as total_book_count,
       sum(oi.sub_total)    as total_purchased_amount,
       MONTH(order_date)    as month
from orders o
         left join order_item oi on o.id = oi.order_id
Group By MONTH(o.order_date);

-- user authentication tables
CREATE TABLE IF NOT EXISTS user
(
    id        INT         NOT NULL AUTO_INCREMENT,
    username  VARCHAR(45) NOT NULL,
    password  TEXT        NOT NULL,
    algorithm VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS authority
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    user INT         NOT NULL,
    PRIMARY KEY (id)
);



INSERT IGNORE INTO user (id, username, password, algorithm)
VALUES ('1', 'john', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT'); --12345

INSERT IGNORE INTO authority (id, name, user)
VALUES ('1', 'READ', '1');
INSERT IGNORE INTO authority (id, name, user)
VALUES ('2', 'WRITE', '1');
