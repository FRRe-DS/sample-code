CREATE TABLE client(
  id bigint not null AUTO_INCREMENT PRIMARY KEY,
  first_name varchar(50) not null,
  last_name varchar(50) null
);

INSERT INTO client (first_name, last_name) VALUES('Fulano', 'De Tal');
