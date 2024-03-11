CREATE USER 'root'@'%' IDENTIFIED BY 'rootusersisnice';

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

FLUSH PRIVILEGES;