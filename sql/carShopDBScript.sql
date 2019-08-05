
create type user_role as enum ('user','manager');

create type transaction_status as enum (
'application confirmation',
'test-drive',
'contract preparation',
'payment expected',
'car delivery'); 


CREATE TABLE user_info( 	
	id serial primary key,
	firstname text not null,
	patronymic text default null,
	lastname text not null,
	email text unique not null,
	password text not null,
	role user_role not null
);

CREATE TABLE brand( 	
	id serial primary key,
	brand text not null
);

CREATE TABLE car_class( 	
	id serial primary key,
	class text not null
);

CREATE TABLE body_type( 	
	id serial primary key,
	type text not null
);

CREATE TABLE fuel( 	
	id serial primary key,
	type text not null
);

CREATE TABLE drive( 	
	id serial primary key,
	type text not null
);

CREATE TABLE engine_type( 	
	id serial primary key,
	type text not null
);

 CREATE table car (
 id serial primary key,

 /*GENERAL*/
 brand_id integer not null REFERENCES brand (id) on delete cascade,
 model text not null,
 price integer not null,
 production integer not null,
 car_class_id integer REFERENCES car_class (id),
 body_type_id integer REFERENCES body_type (id),
 seats integer not null,
 doors integer not null,
 color text not null,
 
  /*DRIVE*/
 drive_id integer REFERENCES drive (id),
 engine_type_id integer REFERENCES engine_type (id),
 fuel_id integer REFERENCES fuel (id),
 
 /*ENGINE*/
 cylinders integer not null,
 valves integer not null,
 cylinder_capacity real not null,
 cylinder_stroke real not null,
 cylinder_bore real not null,
 engine_power real not null,
 engine_torque real not null,
 compression_ration real not null,
 tank_capacity integer not null,
 
 /*PERFORMANCE*/
 top_speed integer not null,
 acceleration real not null,

 /*DIMENSIONS*/
 lenght integer not null,
 width integer not null,
 height integer not null,
 wheelbase integer not null,
 
 available boolean not null default true
 );

CREATE TABLE sales_transaction( 	
	id serial primary key,
	manager_id integer not null REFERENCES user_info (id),
	user_id integer not null REFERENCES user_info (id),
	car_id integer not null REFERENCES car (id),
	status transaction_status not null,
	updated_at timestamptz not null default now()
);



