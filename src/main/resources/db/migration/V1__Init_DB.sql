DROP TABLE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_role;

CREATE TABLE hibernate_sequence (
  next_val BIGINT
) ENGINE = InnoDB;

INSERT INTO hibernate_sequence VALUES (1);
INSERT INTO hibernate_sequence VALUES (1);

CREATE TABLE message (
  id       BIGINT NOT NULL,
  filename VARCHAR(255),
  tag      VARCHAR(255),
  text     VARCHAR(2048),
  user_id  BIGINT,
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE user (
  id              BIGINT NOT NULL,
  activation_code VARCHAR(255),
  active          BIT    NOT NULL,
  email           VARCHAR(255) NOT NULL,
  password        VARCHAR(255) NOT NULL,
  username        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE user_role (
  user_id BIGINT NOT NULL,
  roles   VARCHAR(255)
) ENGINE = InnoDB;

ALTER TABLE message
  ADD CONSTRAINT message__user__fk FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_role
  ADD CONSTRAINT user__user_role__fk FOREIGN KEY (user_id) REFERENCES user (id);