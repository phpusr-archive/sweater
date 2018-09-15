DROP TABLE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_role;
CREATE TABLE hibernate_sequence (
  next_val BIGINT
)
  ENGINE = MyISAM;
INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);
CREATE TABLE message (
  id       BIGINT NOT NULL,
  filename VARCHAR(255),
  tag      VARCHAR(255),
  text     VARCHAR(2048),
  user_id  BIGINT,
  PRIMARY KEY (id)
)
  ENGINE = MyISAM;
CREATE TABLE user (
  id              BIGINT NOT NULL,
  activation_code VARCHAR(255),
  active          BIT    NOT NULL,
  email           VARCHAR(255) NOT NULL,
  password        VARCHAR(255) NOT NULL,
  username        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = MyISAM;
CREATE TABLE user_role (
  user_id BIGINT NOT NULL,
  roles   VARCHAR(255)
)
  ENGINE = MyISAM;
ALTER TABLE message
  ADD CONSTRAINT message_user_fk FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_role
  ADD CONSTRAINT user_user_role_fk FOREIGN KEY (user_id) REFERENCES user (id);