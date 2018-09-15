INSERT INTO user(id, username, email, password, active)
    VALUES (1, 'admin', 'admin@localhost', '123', true);

INSERT INTO user_role(user_id, roles)
    VALUES (1, 'USER'), (1, 'ADMIN');