INSERT INTO app_user(
	username, first_name, last_name, password, role, date_creation, date_update, email)
	VALUES ('admin', 'System', 'Administrator', '$2a$10$spuYmIqTfhPQnKJFgxSP5eqqTA0qNzvNaDWOngiLRwqWYltgMVX6K', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@admin.com');