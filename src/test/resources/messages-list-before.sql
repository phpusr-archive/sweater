DELETE FROM hibernate_sequence;

INSERT INTO hibernate_sequence(next_val) VALUE (10);

DELETE FROM message;

INSERT INTO message(id, text, tag, user_id) VALUES
  (1, 'first', 'my-tag', 1),
  (2, 'second', 'more', 1),
  (3, 'third', 'my-tag', 1),
  (4, 'fourth', 'another', 1);

# TODO reset indexes