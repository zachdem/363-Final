CREATE USER 'coms363'@'%' IDENTIFIED BY 'coms363';

GRANT SELECT, DROP, CREATE, INSERT, DELETE on group5.* TO 'coms363'@'%';

FLUSH PRIVILEGES;