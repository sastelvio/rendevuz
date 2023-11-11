CREATE TABLE IF NOT EXISTS "app_user" (
	id uuid primary key,
	username varchar(20),
	first_name varchar(45),
	last_name varchar(45),
	password varchar(255),
	role varchar(10),
	date_creation timestamp,
	date_update timestamp
);