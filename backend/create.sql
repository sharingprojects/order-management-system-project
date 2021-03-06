create table productcategory.product_category (product_id int4 not null, category_id int4 not null);
create table address (id int4 generated by default as identity, complement varchar(255), distric varchar(255), number varchar(255), postal_code varchar(255), street varchar(255), city_id int4, customer_id int4, primary key (id));
create table category (id int4 generated by default as identity, name varchar(255), primary key (id));
create table city (id int4 generated by default as identity, name varchar(255), state_id int4, primary key (id));
create table customer (id int4 generated by default as identity, cpf_or_cnpj varchar(255), customer_type int4, email varchar(255), name varchar(255), primary key (id));
create table item_order (discount float8, price float8, quantity int4, order_id int4 not null, product_id int4 not null, primary key (order_id, product_id));
create table payment (order_id int4 not null, payment_type int4, primary key (order_id));
create table payment_bank_slip (due_date timestamp, pay_date timestamp, order_id int4 not null, primary key (order_id));
create table payment_cart (installment_numbers int4, order_id int4 not null, primary key (order_id));
create table product (id int4 generated by default as identity, name varchar(255), unit_price float8, primary key (id));
create table state (id int4 generated by default as identity, name varchar(255), primary key (id));
create table tb_order (id int4 generated by default as identity, instant timestamp, customer_id int4, delivery_address_id int4, primary key (id));
create table telephone (customer_id int4 not null, phones varchar(255));
alter table if exists customer add constraint UK_dwk6cx0afu8bs9o4t536v1j5v unique (email);
alter table if exists productcategory.product_category add constraint FKkud35ls1d40wpjb5htpp14q4e foreign key (category_id) references category;
alter table if exists productcategory.product_category add constraint FK2k3smhbruedlcrvu6clued06x foreign key (product_id) references product;
alter table if exists address add constraint FKpo044ng5x4gynb291cv24vtea foreign key (city_id) references city;
alter table if exists address add constraint FK93c3js0e22ll1xlu21nvrhqgg foreign key (customer_id) references customer;
alter table if exists city add constraint FK6p2u50v8fg2y0js6djc6xanit foreign key (state_id) references state;
alter table if exists item_order add constraint FKahfswt8y755lvb7o11vmpxdrm foreign key (order_id) references tb_order;
alter table if exists item_order add constraint FKkybbpxcya7d6pdtyrhpmckdyl foreign key (product_id) references product;
alter table if exists payment add constraint FKldxyfrkgocxt41phgejfxwck4 foreign key (order_id) references tb_order;
alter table if exists payment_bank_slip add constraint FK5o0tdciwd5avwnxym4nrl2g0f foreign key (order_id) references payment;
alter table if exists payment_cart add constraint FKm9fibl0hbdseh61w141r1fbp foreign key (order_id) references payment;
alter table if exists tb_order add constraint FK59mx981vt9udoga067ma5reif foreign key (customer_id) references customer;
alter table if exists tb_order add constraint FK55c73lm5amnyf5f24hiu9xhfl foreign key (delivery_address_id) references address;
alter table if exists telephone add constraint FKd2kespl8p4rstkyphbkwxge3o foreign key (customer_id) references customer;
