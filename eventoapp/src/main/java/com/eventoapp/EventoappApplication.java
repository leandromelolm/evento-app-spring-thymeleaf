package com.eventoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoappApplication.class, args);
	}

}


/*

--- COMANDOS SQL
	
-- mysql
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

-- apagar tabela
DROP TABLE telefone;

-- apagar banco
DROP DATABASE eventosapp;

-- deletar coluna de uma tabela
ALTER TABLE evento DROP COLUMN email_responsavel_evento;

SELECT * FROM participante WHERE data_nascimento IS NOT NULL AND email IS NOT NULL AND nome_participante IS NOT NULL;

DELETE FROM participante WHERE cpf = '';
 
-- deleta linha que tem email que inicia com 'test';
DELETE FROM participante WHERE email LIKE 'test%';

-- deleta linha que tem data_nascimento igual a NULL;
DELETE FROM participante WHERE data_nascimento IS NULL;

-- alterar coluna
UPDATE participante SET evento_codigo=NULL WHERE evento_codigo=80;
UPDATE participante SET evento_codigo=NULL WHERE evento_codigo=80;
UPDATE participante SET evento_codigo=81 WHERE id_participante=89;


*/

