-- Enable the uuid-ossp extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--create table app_user
CREATE TABLE IF NOT EXISTS app_user (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  username VARCHAR(20),
  first_name VARCHAR(45),
  last_name VARCHAR(45),
  email VARCHAR(200),
  password VARCHAR(255),
  role VARCHAR(10),
  date_creation TIMESTAMP,
  date_update TIMESTAMP
);