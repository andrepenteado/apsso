INSERT INTO users (username, password, enabled) VALUES ('admin', '{bcrypt}$2a$10$DrggBKNTQujqeW2xPABOEuM1GgL.6VvdiZAP/hzChWxTj6TiWyLym', true);
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_admin');
