drop schema  public cascade;
create schema public;

create type status as enum (
'APPLICATION_CONFIRMATION',
'CONFIRMED'
'TEST_DRIVE',
'CONTRACT_PREPARATION',
'PAYMENT_EXPECTED',
'CAR_DELIVERY',
'REJECTED'); 


CREATE TABLE manager ( 	
	id serial primary key,
	username text unique not null,
	password text not null
);

 CREATE table car (
 id serial primary key,
 picture text not null,
 model text not null,
 price integer not null,
 production integer not null,  
 available boolean not null default true
 );

CREATE TABLE customer ( 	
	id serial primary key,
	name text not null,
	phone text default null,
	email text default null
);

CREATE TABLE transaction ( 	
	id serial primary key,
	car_id integer not null REFERENCES car (id),
	customer_id integer not null REFERENCES customer (id),
    manager_id integer default null REFERENCES manager (id)
);

CREATE TABLE transaction_status( 	
	id serial primary key,
	date timestamptz not null default now(),
	status_name status not null,
	description text default null,
	transaction_id integer not null REFERENCES transaction (id)
);



