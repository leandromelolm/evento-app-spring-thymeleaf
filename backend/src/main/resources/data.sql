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
	VALUES(10102,'12345678910', '2007-02-10 18:06:35.756902Z','usuario-padrao@email.com', 'usuario testpadrao', '$2a$10$k6mkWpp9G5bATYzRdJBbOe3EhjuAIOK25rahP9coVuXAqW1.tTAFi','2021-11-12 18:06:35', false, '2022-05-31 21:04:04');

-- INSERIR RELACAO DE USUARIO COM PERFIL	
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (10101,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (10102,3);

-- INSERIR EVENTO
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome) VALUES (20101,'2023-05-05 00:00:00','melo@email.com','06:30','Salão de festas do condomínio	','Reunião de condominio - Reajustes');
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome) VALUES (20102,'2023-12-31 00:00:00','melo@email.com','23:58','Salão de festas do condomínio	','Festa de fim de ano');
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome) VALUES (20103,'2023-08-15 00:00:00','usuario-padrao@email.com','23:58','Campo da Varzea','Jogo amistoso');
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome) VALUES (20104,'2023-04-19 00:00:00','usuario-padrao@email.com','23:58','Clube Spring',' Aulão sobre Spring');
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome) VALUES (20105,'2023-12-31 00:00:00','usuario-padrao@email.com','23:58','Salão de festas do condomínio	','Outra Festa de Fim de Ano');
INSERT INTO evento (codigo, data, email_responsavel_evento, horario, local, nome) VALUES (20106,'2023-04-19 00:00:00','usuario-padrao@email.com','23:58','Praça publica',' Campanha de Vacinação');

-- INSERIR RELAÇÃO USUARIO COM EVENTO
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('melo@email.com', 20101);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('melo@email.com', 20102);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20103);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20104);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20105);
INSERT INTO usuarios_eventos (usuario_email, evento_id) VALUES ('usuario-padrao@email.com', 20106);

-- INSERIR PARTICIPANTE EM EVENTOS
INSERT INTO participante (id_participante, cpf, data_cadastro, data_nascimento, email, nome_participante, evento_codigo) VALUES (22101,'222.222.222-22','2022-08-03 11:14:59.899','1981-02-22','Wmsial@email.com','testuser silva santos',20104);

INSERT INTO telefone(id_telefone, ddd, numero, tipo, id_participante) VALUES (721001,'11','9 1111-1111',NULL,22101);
