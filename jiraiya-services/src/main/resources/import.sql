INSERT INTO `users` (username, password, enabled) VALUES ('user1','$2a$10$gV6k0H4V0TAlr80BIcf6IOqD0Gq7RJ3LC0gytuUcoBNQFsWYsWA7W',1);
INSERT INTO `users` (username, password, enabled) VALUES ('admin','$2a$10$lvrgiMlpEjMosIP6GUL/B.VnezopE3N9g3ADlbtDVBTB0wqUiilUm',1);

INSERT INTO `authorities` (authority) VALUES ('ROLE_USER');
INSERT INTO `authorities` (authority) VALUES ('ROLE_ADMIN');

