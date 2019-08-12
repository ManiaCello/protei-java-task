create table account(
	id bigint primary key generated always as identity,
	name varchar(50) not null,
	email varchar(254) not null unique,
	phoneNumber varchar(15)
)