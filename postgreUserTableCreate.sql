CREATE TABLE users(
  id serial PRIMARY KEY,
  username VARCHAR(32) UNIQUE NOT NULL,
  password VARCHAR(32) NOT NULL,
);
INSERT INTO users(username, password) VALUES ('admin','admin');
