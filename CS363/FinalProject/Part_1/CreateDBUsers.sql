use group31;

CREATE USER 'cs363'@'localhost' IDENTIFIED BY '363F2020';
GRANT view ON group31.table_name TO 'cs363'@'localhost';
GRANT create ON group31.table_name TO 'cs363'@'localhost';
GRANT delete ON group31.table_name TO 'cs363'@'localhost';
GRANT drop ON group31.table_name TO 'cs363'@'localhost';
GRANT insert ON group31.table_name TO 'cs363'@'localhost';