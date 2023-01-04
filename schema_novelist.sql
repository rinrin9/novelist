DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS favorite CASCADE;
DROP TABLE IF EXISTS topic CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  user_id SERIAL NOT NULL,
  authority VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS topic (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  path VARCHAR(255) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  latitude VARCHAR(20),
  longitude VARCHAR(20),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE topic ADD CONSTRAINT FK_users_topic FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE IF NOT EXISTS favorite (
  id SERIAL NOT NULL,
  user_id INT NOT NULL,
  topic_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE favorite ADD CONSTRAINT FK_favorite_users FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE favorite ADD CONSTRAINT FK_favorite_topic FOREIGN KEY (topic_id) REFERENCES topic;

CREATE TABLE IF NOT EXISTS comment (
  id SERIAL NOT NULL,
  topic_id INT NOT NULL,
  description VARCHAR(1000) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE comment ADD CONSTRAINT FK_comment_topic FOREIGN KEY (topic_id) REFERENCES topic;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO novelist;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO novelist;