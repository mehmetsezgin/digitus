INSERT INTO `roles` VALUES (1,'ADMIN');
INSERT INTO `roles` VALUES (2,'USER');

INSERT INTO `users` (user_id, user_name, email, password, name, last_name, active, logged_in) VALUES (100,'mehmet','mehmetszgn@gmail.com','$2a$10$evOsio6q7ArYFivEkDOaMe1Mer8H9rfv8fM26O9PNbmBPiLjuBFf2','Mehmet', 'Sezgin', 1, 0);
INSERT INTO `users` (user_id, user_name, email, password, name, last_name, active, logged_in) VALUES (101,'sezgin','sezgin_mehmet@hotmail.com','$2a$10$evOsio6q7ArYFivEkDOaMe1Mer8H9rfv8fM26O9PNbmBPiLjuBFf2','Mehmet', 'Sezgin', 0, 0);

INSERT INTO `user_role` (user_id, role_id) values(100,1);
INSERT INTO `user_role` (user_id, role_id) values(101,2);