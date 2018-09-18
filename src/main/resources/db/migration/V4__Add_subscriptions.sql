CREATE TABLE user_subscribtions (
  channel_id BIGINT NOT NULL REFERENCES user,
  subscriber_id BIGINT NOT NULL REFERENCES user,
  PRIMARY KEY (channel_id, subscriber_id)
)