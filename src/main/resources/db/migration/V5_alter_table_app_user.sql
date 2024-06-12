-- Adicionar novas colunas à tabela app_user
ALTER TABLE app_user
ADD COLUMN phone VARCHAR(20),
ADD COLUMN about TEXT,
ADD COLUMN location VARCHAR(100),
ADD COLUMN link_linkedin VARCHAR(255),
ADD COLUMN link_facebook VARCHAR(255),
ADD COLUMN link_twitter VARCHAR(255),
ADD COLUMN link_instagram VARCHAR(255);

-- Atualizar a coluna date_update para a data e hora atual sempre que a linha for modificada
CREATE OR REPLACE FUNCTION update_date_update_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.date_update = NOW();
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_date_update
BEFORE UPDATE ON app_user
FOR EACH ROW
EXECUTE FUNCTION update_date_update_column();