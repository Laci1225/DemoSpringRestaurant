create sequence order_sequence start with 1 increment by 50;
create sequence restaurant_sequence start with 1 increment by 50;

create table orders
(
    id               bigint       not null,
    delivery_address varchar(255) not null,
    drink_type       varchar(255),
    meal_type        varchar(255),
    order_status     varchar(255) not null,
    restaurant_id    bigint,
    primary key (id)
);

create table restaurants
(
    id               bigint       not null,
    address          varchar(255),
    can_deliver      boolean      not null,
    email            varchar(255),
    is_vegan         boolean,
    name             varchar(255) not null,
    number_of_tables integer,
    owner            varchar(255) not null,
    phone_number     varchar(255),
    primary key (id)
);

alter table if exists orders
    add constraint FK2m9qulf12xm537bku3jnrrbup
    foreign key (restaurant_id)
    references restaurants;