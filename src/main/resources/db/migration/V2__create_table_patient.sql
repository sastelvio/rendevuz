CREATE TABLE IF NOT EXISTS patient (
	id serial primary key,
	first_name varchar(50),
	surname varchar(50),
	social_security varchar(15),
	email varchar(200)
);