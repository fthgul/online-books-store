CREATE SCHEMA IF NOT EXISTS online_books_store;

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

-- view for statistics
create view monthly_order_statistic_view as
select count(distinct o.id) as total_order_count,
       sum(oi.quantity)      as total_book_count,
       sum(oi.sub_total)    as total_purchased_amount,
       MONTHNAME(order_date)    as month
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
VALUES ('1', 'john', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT'); -- 12345

INSERT IGNORE INTO authority (id, name, user)
VALUES ('1', 'READ', '1');
INSERT IGNORE INTO authority (id, name, user)
VALUES ('2', 'WRITE', '1');


alter table books
    add created_date date null;

alter table books
    add last_modified_date date null;

alter table books
    add created_by varchar(100) null;

alter table books
    add last_modified_by varchar(100) null;


alter table inventory
    add created_date date null;

alter table inventory
    add last_modified_date date null;

alter table inventory
    add created_by varchar(100) null;

alter table inventory
    add last_modified_by varchar(100) null;

-- spring data jpa hibernate envers
create table hibernate_sequence (next_val bigint) ;
insert into hibernate_sequence (next_val) VALUES (1);

create table rev_info_his
(
    id      int    auto_increment primary key,
    removal_time datetime(6) null,
    user_account varchar(50),
    timestamp bigint null
);

create table books_his
(
    id       int ,
    rev       int not null,
    revtype   tinyint      null,
    name     varchar(100),
    author   varchar(100),
    price    decimal(10, 2),
    created_by            varchar(50)  null,
    created_date          timestamp    null,
    last_modified_by      varchar(50)  null,
    last_modified_date    datetime(6)  null,
    primary key (id, rev)
);
create index IDX_BOOK_HIS_REV
    on books_his (rev);

ALTER TABLE books_his
    ADD CONSTRAINT FK_BOOK_HIS_REV FOREIGN KEY (rev) REFERENCES rev_info_his (id) ON DELETE CASCADE;


create table inventory_his
(
    rev       int not null,
    revtype   tinyint      null,
    book_id  int,
    quantity int,
    version  int,
    created_by            varchar(50)  null,
    created_date          timestamp    null,
    last_modified_by      varchar(50)  null,
    last_modified_date    datetime(6)  null,
    primary key (rev)
);
create index IDX_BOOK_HIS_REV
    on inventory_his (rev);

ALTER TABLE inventory_his
    ADD CONSTRAINT FK_INVENTORY_HIS_REV FOREIGN KEY (rev) REFERENCES rev_info_his (id) ON DELETE CASCADE;

