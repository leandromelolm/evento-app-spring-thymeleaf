-- Antes de adicionar um usuário é necessario adicionar esses perfis na tabela role.
-- Inserido usuario para test.

-- INSERIR PERFIL DE USUARIOS
INSERT INTO role (id, name_role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name_role) VALUES (2, 'ROLE_POWER_USER');
INSERT INTO role (id, name_role) VALUES (3, 'ROLE_STANDARD_USER');

-- INSERIR USUARIO
INSERT INTO usuario(id,	cpf, data_cadastro,	email, nome, senha, atual_acesso, enabled_user, ultimo_acesso) 
	VALUES(10101,'12345678910', '2007-02-10 18:06:35.756902Z','melo@email.com', 'melo', '$2a$10$k6mkWpp9G5bATYzRdJBbOe3EhjuAIOK25rahP9coVuXAqW1.tTAFi','2021-11-12 18:06:35', false, '2022-06-30 21:04:04');
INSERT INTO usuario(id,	cpf, data_cadastro,	email, nome, senha, atual_acesso, enabled_user, ultimo_acesso) 
	VALUES(10102,'12345678911', '2007-02-10 18:06:35.756902Z','usuario-padrao@email.com', 'usuario testpadrao', '$2a$10$k6mkWpp9G5bATYzRdJBbOe3EhjuAIOK25rahP9coVuXAqW1.tTAFi','2021-11-12 18:06:35', false, '2022-05-31 21:04:04');
INSERT INTO usuario(id,	cpf, data_cadastro,	email, nome, senha, atual_acesso, enabled_user, ultimo_acesso) 
	VALUES(10103,'12345678912', '2007-02-10 18:06:35.756902Z','usuario-avancado@email.com', 'usuario avançado', '$2a$10$k6mkWpp9G5bATYzRdJBbOe3EhjuAIOK25rahP9coVuXAqW1.tTAFi','2021-11-12 18:06:35', false, '2022-05-31 21:04:04');

-- INSERIR RELACAO DE USUARIO COM PERFIL	
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (10101,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (10102,3);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (10103,2);

-- INSERIR EVENTO
-- STATUS DO EVENTO -- 1 ABERTO, 2 PAUSADO, 3 ENCERRADO, 4 FINALIZADO
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20101,'2023-05-05 00:00:00','melo@email.com','06:30','Salão de festas do condomínio	','Reunião de condominio - Reajustes',2,0,1);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20102,'2023-12-31 00:00:00','melo@email.com','23:58','Salão de festas do condomínio	','Festa de fim de ano',0,0,1);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20103,'2023-08-15 00:00:00','usuario-padrao@email.com','23:58','Campo da Varzea','Jogo amistoso',0,0,2);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20104,'2023-04-19 00:00:00','usuario-padrao@email.com','23:58','Clube Spring',' Aulão sobre Spring',5,0,1);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20105,'2023-12-31 00:00:00','usuario-padrao@email.com','23:58','Salão de festas do condomínio','Outra Festa de Fim de Ano',2,0,1);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20106,'2023-04-19 13:00:00','usuario-padrao@email.com','23:58','Praça publica',' Campanha de Vacinação',3,0,4);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20107,'2023-01-02 12:00:00','usuario-padrao@email.com','12:18','Avenida com nome muito grande, bairro grande','Dia V de Vacinação(20107)',1,0,3);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20108,'2023-01-02 12:00:00','usuario-avancado@email.com','12:18','Avenida com nome muito grande, bairro grande','Evento Teste1',1,0,2);
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome, quant_part, max_part, status) 
    VALUES (20109,'2023-01-02 12:00:00','usuario-avancado@email.com','12:18','Avenida com nome muito grande, bairro grande','Evento Teste 2',3,0,1);

-- INSERIR RELAÇÃO USUARIO COM EVENTO
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('melo@email.com', 20101);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('melo@email.com', 20102);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20103);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20104);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20105);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20106);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20107);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-avancado@email.com', 20108);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-avancado@email.com', 20109);

-- INSERIR PARTICIPANTE EM EVENTOS
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22101,'222.222.222-01','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser01 silva santos',20104);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22102,'222.222.222-02','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuseR02 silva santos',20105);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22103,'222.222.222-03','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuseR03 silva santos',20105);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22104,'222.222.222-04','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser04 silva santos',20106);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22105,'222.222.222-05','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser05 silva santos',20106);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22106,'222.222.222-06','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser06 silva santos',20106);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22107,'222.222.222-07','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser07 alCantsantos',20104);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22108,'222.222.222-08','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser08 silva santos',20104);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22109,'222.222.222-09','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser09 silva santos',20104);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22110,'222.222.222-10','2022-08-03 11:14:59.899','1981-02-22','aaa10@email.com','testuser10 silva santos',20104);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22111,'222.222.222-11','2022-08-03 11:14:59.899','1981-02-22','usuario11l@email.com','testuser11 silva santos',20107);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22112,'222.222.222-12','2022-08-03 11:14:59.899','1981-02-22','cde12@email.com','testuser12 silva santos',20108);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22113,'222.222.222-13','2022-08-03 11:14:59.899','1981-02-22','test13l@email.com','testuser13 silva santos',20109);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22114,'222.222.222-14','2022-08-03 11:14:59.899','1981-02-22','test14@email.com','testuser14 silva santos',20109);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22115,'222.222.222-15','2022-08-03 11:14:59.899','1981-02-22','testusuario15@email.com','testuser15 silva santos',20109);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22116,'222.222.222-14','2022-08-03 11:14:59.899','1981-02-22','t16test@email.com','t16testuser silva santos',20101);
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) 
    VALUES (22117,'222.222.222-15','2022-08-03 11:14:59.899','1981-02-22','t17testusuario@email.com','t17testuser15 silva santos',20101);

INSERT INTO telefone(id_telefone, ddd, numero, tipo, id_participante) VALUES (721001,'11','9 1111-1111',NULL,22101);
