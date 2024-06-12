-- Enable the uuid-ossp extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--create table app_user
CREATE TABLE IF NOT EXISTS app_user (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  username VARCHAR(20) UNIQUE NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  email VARCHAR(200) UNIQUE NOT NULL,
  phone VARCHAR(20),
  about TEXT,
  location VARCHAR(100),
  link_linkedin VARCHAR(255),
  link_facebook VARCHAR(255),
  link_twitter VARCHAR(255),
  link_instagram VARCHAR(255),
  password VARCHAR(255),
  role VARCHAR(10),
  date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Atualizar a coluna date_update para a data e hora atual sempre que a linha for modificada
-- CREATE OR REPLACE FUNCTION update_date_update_column()
-- RETURNS TRIGGER AS $$
-- BEGIN
--    NEW.date_update = NOW();
--   RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- CREATE TRIGGER update_date_update
-- BEFORE UPDATE ON app_user
-- FOR EACH ROW
-- EXECUTE FUNCTION update_date_update_column();