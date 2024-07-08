-- Создаем базу БД test
DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

USE test;

DROP TABLE IF EXISTS author;
CREATE TABLE author (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	first_name VARCHAR(45),
    last_name VARCHAR(45)
);

CREATE TABLE student (
	id INT AUTO_INCREMENT PRIMARY KEY, 
	first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(45)
);

INSERT INTO
	author (first_name, last_name)
VALUES 
	('Петр', 'Петров'),
	('Иван', 'Иванов');