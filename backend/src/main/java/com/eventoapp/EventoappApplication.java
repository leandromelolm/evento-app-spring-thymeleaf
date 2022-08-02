package com.eventoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EventoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoappApplication.class, args);
		//Gerar senha criptografada para teste
//		System.out.println(new BCryptPasswordEncoder().encode("12345"));
	}

}



/*


### COMANDOS SQL
	
## mysql
mysql --version
mysql -u admin -p
CREATE DATABASE eventosapp;
SHOW DATABASES;
USE eventosapp;
SHOW TABLES;
SHOW COLUMNS FROM evento; 
DESCRIBE evento;
SELECT * FROM evento;
SHOW VARIABLES LIKE 'port';

EXIT;

DROP TABLE telefone;
DROP DATABASE eventosapp;

##Deletar coluna de uma tabela
ALTER TABLE evento DROP COLUMN email_responsavel_evento;

 SELECT * FROM participante WHERE data_nascimento IS NOT NULL AND email IS NOT NULL AND nome_participante IS NOT NULL;

 DELETE FROM participante WHERE cpf = '';
 
### Deleta linha que tem email que inicia com 'test';
DELETE FROM participante WHERE email LIKE 'test%';
### Deleta linha que tem data_nascimento igual a NULL;
DELETE FROM participante WHERE data_nascimento IS NULL;

UPDATE participante SET evento_codigo=NULL WHERE evento_codigo=80;
UPDATE participante SET evento_codigo=NULL WHERE evento_codigo=80;
UPDATE participante SET evento_codigo=81 WHERE id_participante=89;

### INSERIR USUARIO
NSERT INTO usuario(id,	cpf, data_cadastro,	email, nome, senha, atual_acesso, enabled_user, ultimo_acesso) 
	VALUES(1,'12345678910', '2007-02-10 18:06:35.756902Z','melo@email.com', 'melo', '$2a$10$k6mkWpp9G5bATYzRdJBbOe3EhjuAIOK25rahP9coVuXAqW1.tTAFi','2021-11-12 18:06:35', false, '2022-06-30 21:04:04'))
 	

### INSERIR PERFIL DE USUARIOS
INSERT INTO role VALUES(1, 'ROLE_DEVELOPER')
INSERT INTO role VALUES(2, 'ROLE_ADMIN')
INSERT INTO role VALUES(3, 'ROLE_USER')

INSERT INTO usuarios_roles (usuario_id, role_id) values ('admin','ROLE_ADMIN')
INSERT INTO usuarios_roles (usuario_id, role_id) values ('user1', 'ROLE_USER')

*/

