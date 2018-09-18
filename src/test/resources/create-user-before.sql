DELETE FROM user_role;
DELETE FROM user;

INSERT INTO user(id, active, password, username, email) VALUES
  (1, true, '$2a$08$Bq0ymI7lE6SxPguQJ6yYWeMPxc3DZu.eGPzhn7vD6c7Mz7Wl3rQ0O', 'admin', 'admin@test.ru'),
  (2, true, '$2a$08$Bq0ymI7lE6SxPguQJ6yYWeMPxc3DZu.eGPzhn7vD6c7Mz7Wl3rQ0O', 'mike', 'mike@test.ru');

INSERT INTO user_role(user_id, roles) VALUES
  (1, 'USER'), (1, 'ADMIN'),
  (2, 'USER');

