
CREATE TABLE component_rating (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    component_id BIGINT,
    pilot_id BIGINT,
    score INT,
    comment VARCHAR(100));