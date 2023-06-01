INSERT INTO tb_branch(name) VALUES ('Carnacorp');
INSERT INTO tb_branch(name) VALUES ('Macecorp');

INSERT INTO tb_department(name, branch_id) VALUES ('Admin', 1);
INSERT INTO tb_department(name, branch_id) VALUES ('Marketing', 1);
INSERT INTO tb_department(name, branch_id) VALUES ('Atendimento', 1);

INSERT INTO tb_department(name, branch_id) VALUES ('Admin', 2);
INSERT INTO tb_department(name, branch_id) VALUES ('Suporte', 2);
INSERT INTO tb_department(name, branch_id) VALUES ('Desenvolvimento', 2);

INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Maria', 'maria@gmail.com', '782289', 1, 1);

INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Marcos', 'marcos@gmail.com', '78927', 2, 4);

INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Jose', 'jose@gmail.com', '456456', 3, 2);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Roberto', 'roberto@gmail.com', '7835169', 3, 5);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Carlos', 'carlos@gmail.com', '78314', 3, 3);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Evandro', 'evandro@gmail.com', '4567837', 3, 6);

INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Lorena', 'lorena@gmail.com', '45637869', 4, 2);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Aline', 'aline@gmail.com', '4564564', 4, 3);

INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Gabriel', 'gabriel@gmail.com', '9947756', 4, 5);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Pedro', 'pedro@gmail.com', '321456', 4, 6);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Marta', 'marta@gmail.com', '7894536', 4, 2);

INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Wilson', 'wilson@gmail.com', '123456', 4, 3);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Ana', 'ana@gmail.com', '4546454666', 4, 5);
INSERT INTO tb_user(name, email, password, id_profile, department_id) VALUES ('Eduarda', 'eduarda@gmail.com', '7878979', 4, 6);

INSERT INTO tb_task(name, description, moment, dead_line, need_auth, auth, department_id) VALUES ('Teste', 'Essa é uma task de teste', TIMESTAMP WITH TIME ZONE '2023-06-01T13:00:00Z', TIMESTAMP WITH TIME ZONE '2023-07-06T13:00:00Z', 1, 1, 2);

