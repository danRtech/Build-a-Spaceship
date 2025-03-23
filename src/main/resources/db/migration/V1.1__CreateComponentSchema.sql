CREATE TABLE spaceship(
  code CHAR(2) NOT NULL UNIQUE,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE spaceship_component (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  spaceship_code CHAR(2) NOT NULL,
  title VARCHAR(100) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  features VARCHAR(2000) NOT NULL,
  price INT NOT NULL,
  time_to_build VARCHAR(32) NOT NULL,
  spaceship_type VARCHAR(16) NOT NULL,
  spaceship_size VARCHAR(20) NOT NULL,
  keywords VARCHAR(100)
);

ALTER TABLE spaceship_component ADD CONSTRAINT FK_SPACESHIP_CODE FOREIGN KEY (spaceship_code) REFERENCES spaceship(code);

insert into spaceship (code, name) values
('FX', 'Falcon-X'),
('SD', 'Star Defender'),
('RP', 'Rogue Phoenix'),
('GE', 'Galactic Explorer'),
('NC', 'Nebula Cruiser');