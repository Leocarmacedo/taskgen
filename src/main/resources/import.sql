INSERT INTO tb_branch(name) VALUES ('Carnacorp');
INSERT INTO tb_branch(name) VALUES ('Macecorp');

INSERT INTO tb_department(name, branch_id) VALUES ('Admin', 1);
INSERT INTO tb_department(name, branch_id) VALUES ('Marketing', 1);
INSERT INTO tb_department(name, branch_id) VALUES ('Atendimento', 1);

INSERT INTO tb_department(name, branch_id) VALUES ('Admin', 2);
INSERT INTO tb_department(name, branch_id) VALUES ('Suporte', 2);
INSERT INTO tb_department(name, branch_id) VALUES ('Desenvolvimento', 2);

INSERT INTO tb_user(name, email, password, department_id) VALUES ('Maria', 'maria@gmail.com', '$2a$10$W5oqWSmqYYaaBo8oiFCk3eVNarC3.TdAgBvlE0gscQrXZk7dn7hZS', 1);

INSERT INTO tb_user(name, email, password, department_id) VALUES ('Marcos', 'marcos@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 4);

INSERT INTO tb_user(name, email, password, department_id) VALUES ('Jose', 'jose@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 2);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Roberto', 'roberto@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 5);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Carlos', 'carlos@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 3);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Evandro', 'evandro@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 6);

INSERT INTO tb_user(name, email, password, department_id) VALUES ('Lorena', 'lorena@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 2);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Aline', 'aline@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 3);

INSERT INTO tb_user(name, email, password, department_id) VALUES ('Gabriel', 'gabriel@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 5);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Pedro', 'pedro@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 6);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Marta', 'marta@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 2);

INSERT INTO tb_user(name, email, password, department_id) VALUES ('Wilson', 'wilson@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 3);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Ana', 'ana@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 5);
INSERT INTO tb_user(name, email, password, department_id) VALUES ('Eduarda', 'eduarda@gmail.com', '$2a$10$oV3BSgD.l1e6jNTmIYxDl.TCifiHfQ7HfCs1E/G.dyISpsKSKEgPu', 6);

INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_LEADER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (6, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (7, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (8, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (9, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (10, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (11, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (12, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (13, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (14, 1);

INSERT INTO tb_task(name, description, moment, dead_line, need_auth, auth, department_id) VALUES ('Teste', 'Essa é uma task de teste', TIMESTAMP WITH TIME ZONE '2023-06-01T13:00:00Z', TIMESTAMP WITH TIME ZONE '2023-07-06T13:00:00Z', 1, 1, 2);

