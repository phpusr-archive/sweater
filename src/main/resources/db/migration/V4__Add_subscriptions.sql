CREATE TABLE user_subscribtions (
  channel_id BIGINT NOT NULL REFERENCES user,
  CONSTRAINT user_subscribtions__chanel_id__fk FOREIGN KEY (channel_id) REFERENCES user(id),
  subscriber_id BIGINT NOT NULL REFERENCES user,
  CONSTRAINT user_subscribtions__subscriber_id__fk FOREIGN KEY (subscriber_id) REFERENCES user(id),
  PRIMARY KEY (channel_id, subscriber_id)
) ENGINE = InnoDB;