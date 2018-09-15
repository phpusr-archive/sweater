# Пароль "1", зашифрован с помощью Bcrypt(8) https://www.devglan.com/online-tools/bcrypt-hash-generator
INSERT INTO user(id, username, email, password, active)
    VALUES (1, 'admin', 'admin@localhost', '$2a$08$Bq0ymI7lE6SxPguQJ6yYWeMPxc3DZu.eGPzhn7vD6c7Mz7Wl3rQ0O', true);

INSERT INTO user_role(user_id, roles)
    VALUES (1, 'USER'), (1, 'ADMIN');