DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS favorite CASCADE;
DROP TABLE IF EXISTS topic CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS chara CASCADE;

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
  title VARCHAR(50) NOT NULL,
  description VARCHAR(25000) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  keep bool NOT NULL,
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
  description VARCHAR(25000) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);  

ALTER TABLE comment ADD CONSTRAINT FK_comment_topic FOREIGN KEY (topic_id) REFERENCES topic;

CREATE TABLE IF NOT EXISTS chara (
  id SERIAL NOT NULL,
  topic_id INT NOT NULL,
  name VARCHAR(50),
  nickname VARCHAR(50),
  role VARCHAR(50),
  gendere VARCHAR(50),
  age VARCHAR(50),
  birthday VARCHAR(50),
  height VARCHAR(50),
  weight VARCHAR(50),
  personality VARCHAR(50),
  skill VARCHAR(100),
  ability VARCHAR(100),
  Appearance VARCHAR(100),
  upbringing VARCHAR(100),
  background VARCHAR(100),
  others VARCHAR(200),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);  

ALTER TABLE chara ADD CONSTRAINT FK_chara_topic FOREIGN KEY (topic_id) REFERENCES topic;

CREATE TABLE IF NOT EXISTS plot (
  id SERIAL NOT NULL,
  topic_id INT NOT NULL,
  setting VARCHAR(300),
  confrontation VARCHAR(300),
  Resolution VARCHAR(300),
  memo VARCHAR(300),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);  

ALTER TABLE plot ADD CONSTRAINT FK_plot_topic FOREIGN KEY (topic_id) REFERENCES topic;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO novelist;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO novelist;