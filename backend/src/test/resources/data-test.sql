INSERT INTO roles (id, title) VALUES (1, 'ADMIN');
INSERT INTO roles (id, title) VALUES (2, 'USER');

INSERT INTO users (id, email, name, password, role_id) VALUES (1, 'user1@gmail.com', 'User1', '$2a$10$quO0XMV4m8Lr.Uc1bTmppOcx3PmHVXOKGnoxVRH4ubc55d4ouyiVO', 2);
INSERT INTO users (id, email, name, password, role_id) VALUES (2, 'user2@gmail.com', 'User2', '$2a$10$quO0XMV4m8Lr.Uc1bTmppOcx3PmHVXOKGnoxVRH4ubc55d4ouyiVO', 2);
