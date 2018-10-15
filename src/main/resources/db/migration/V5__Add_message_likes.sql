CREATE TABLE message_likes (
  user_id BIGINT NOT NULL,
  CONSTRAINT message_likes__user_id__fk FOREIGN KEY (user_id) REFERENCES user(id),
  message_id BIGINT NOT NULL,
  CONSTRAINT message_likes__message_id__fk FOREIGN KEY (message_id) REFERENCES message(id),
  PRIMARY KEY (user_id, message_id)
) ENGINE = InnoDB;