drop schema  public cascade;
create schema public;

CREATE TABLE card( 	
	id serial primary key,
	card_number text unique not null,
	validity text not null,
	cvv text not null,
	card_holder_name text not null,
	balance double precision not null
);

CREATE TABLE operation( 	
	id serial primary key,
	card_id integer not null REFERENCES card (id),
	operation text not null,
	date timestamptz not null default now()
);




