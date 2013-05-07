-- NOTE: before using the script, execute the following command:
--		mysqladmin -u root password admin
-- USAGE: 
--		mysql -u root -padmin
--		source db.sql

-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';

-- create database schema

DROP DATABASE IF EXISTS chasemaster;
CREATE DATABASE chasemaster;
SHOW DATABASES;

USE chasemaster;

CREATE TABLE players (
	id INTEGER NOT NULL AUTO_INCREMENT, 
	username VARCHAR(20) NOT NULL,
	password VARCHAR(20) NOT NULL,
   	colour VARCHAR(10) NOT NULL,
	registered_on DATE,
	PRIMARY KEY (id)
) engine=innodb;	

CREATE TABLE matches (
	id INTEGER NOT NULL AUTO_INCREMENT, 
	play_on TIMESTAMP NOT NULL,
	PRIMARY KEY (id)
) engine=innodb;	

CREATE TABLE turns (
	id INTEGER NOT NULL AUTO_INCREMENT,
	match_id INTEGER NOT NULL,
	colour VARCHAR(10),
	winners VARCHAR(300) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (match_id) REFERENCES matches(id)
) engine=innodb;	

CREATE TABLE movements (
	id INTEGER NOT NULL AUTO_INCREMENT,
	turn_id INTEGER NOT NULL,
	player_id INTEGER NOT NULL,
	location_from VARCHAR(2),
	location_to VARCHAR(2),
	duration BIGINT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (turn_id) REFERENCES turns(id),
	FOREIGN KEY (player_id) REFERENCES players(id)
) engine=innodb;	

COMMIT;

SHOW TABLES;
DESC players;
DESC matches;
DESC turns;
DESC movements;

-- create admin user and grant necessary privileges --

-- DROP PROCEDURE IF EXISTS drop_user_if_exists;
-- DELIMITER $$
-- CREATE PROCEDURE drop_user_if_exists()
-- BEGIN
--  DECLARE foo BIGINT DEFAULT 0;
--  SELECT COUNT(*)
--  INTO foo
--    FROM mysql.user
--      WHERE User = 'frpadmin' and  Host = 'localhost';
--   IF foo > 0 THEN
--         DROP USER 'frpadmin'@'localhost';
--  END IF;
-- END;$$
-- DELIMITER;
-- CALL drop_user_if_exists();
-- DROP PROCEDURE IF EXISTS drop_users_if_exists;
-- SET SQL_MODE=@OLD_SQL_MODE;

-- NOTE:
-- the following error will appear the first time:
-- sql error 1396 (hy000) operation drop user failed for "frpamin@localhoust"
-- DROP USER 'frpadmin'@'localhost';
-- CREATE USER 'frpadmin'@'localhost' IDENTIFIED BY 'frpadmin';
-- GRANT ALL ON frp.* TO 'frpadmin'@'localhost';

-- SELECT * FROM mysql.user WHERE User = 'frpadmin' and  Host = 'localhost';
