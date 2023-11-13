CREATE TABLE IF NOT EXISTS appointment (
	id serial primary key,
	description varchar(255),
	schedule timestamp,
	date_creation timestamp,
	patient_id integer,
	constraint fk_appointment_patient foreign key(patient_id) references patient(id)
);