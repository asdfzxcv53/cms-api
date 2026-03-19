-- TODO schema
-- example
create table members
(
    id                 bigint primary key         not null auto_increment,
    name               varchar(50)                not null,
    username           varchar(50)                not null constraint uk_member_username unique,
    password           varchar(255)               not null,
    created_date       timestamp default now()    not null,
    last_modified_date timestamp,
    role               varchar(20) default 'USER' not null
);

create table contents
(
    id                 bigint primary key         not null auto_increment,
    title              varchar(100)               not null,
    description        text,
    view_count         bigint                     not null,
    created_date       timestamp                  not null,
    created_by         varchar(50)                not null,
    last_modified_date timestamp,
    last_modified_by   varchar(50)
)