create schema sa;
create table CUSTOMER (
    id bigint not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    firstname varchar(100),
    lastname varchar(100)
);