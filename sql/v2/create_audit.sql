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
