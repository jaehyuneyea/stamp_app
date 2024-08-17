SELECT 'CREATE DATABASE stampapp'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stampapp')\gexec

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS stamps CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS photos;

CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   email varchar(45) DEFAULT NULL,
   username varchar(45) DEFAULT NULL,
   password varchar(45) DEFAULT NULL
);

CREATE TABLE stamps (
   id SERIAL PRIMARY KEY,
   description text DEFAULT NULL,
   rating float DEFAULT NULL,
   railway varchar(45) DEFAULT NULL
);

CREATE TABLE comments (
   id SERIAL PRIMARY KEY,
   stamp_id INT DEFAULT NULL,
   user_id INT DEFAULT NULL,
   parent_id INT DEFAULT NULL,
   description text NOT NULL,
   date_created timestamp DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (stamp_id) REFERENCES stamps(id) ON DELETE CASCADE,
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE photos (
   id text PRIMARY KEY,
   stamp_id INT DEFAULT NULL,
   comment_id INT DEFAULT NULL,
   file_path text DEFAULT NULL,
   date_created timestamp DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (stamp_id) REFERENCES stamps(id) ON DELETE CASCADE,
   FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

INSERT INTO users VALUES
	(1, 'example@email.com', 'jahjah', 'password123'),
	(2, 'example2@email.com', 'jah_rodman', 'password123');
	
INSERT INTO stamps VALUES
	(1, 'an example stamp', 5, 'Tokyo Railway'),
	(2, 'JR LINE stamp', 4, 'JR Line');
	
INSERT INTO comments VALUES
	(1, 1, 1, NULL, 'Pretty good stamp', CURRENT_TIMESTAMP);