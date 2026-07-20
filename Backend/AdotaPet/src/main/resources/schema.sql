-- Cria as tabelas do AdotaPet caso ainda não existam.
-- O Spring Boot executa este ficheiro automaticamente no arranque
-- (ver spring.sql.init.mode=always em application.properties).

CREATE TABLE IF NOT EXISTS Animal (
    id_animal INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    raca VARCHAR(100),
    porte VARCHAR(20),
    status_animal BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS Adotante (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    celular VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Adocao (
    id_adocao INT AUTO_INCREMENT PRIMARY KEY,
    id_animal INT NOT NULL,
    cpf_adotante VARCHAR(11) NOT NULL,
    data_adocao DATE NOT NULL,
    CONSTRAINT fk_adocao_animal FOREIGN KEY (id_animal) REFERENCES Animal(id_animal),
    CONSTRAINT fk_adocao_adotante FOREIGN KEY (cpf_adotante) REFERENCES Adotante(cpf)
);

CREATE TABLE IF NOT EXISTS Vacina (
    id_vacina INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Vacinacao (
    id_animal INT NOT NULL,
    id_vacina INT NOT NULL,
    data_aplicacao DATE NOT NULL,
    PRIMARY KEY (id_animal, id_vacina, data_aplicacao),
    CONSTRAINT fk_vacinacao_animal FOREIGN KEY (id_animal) REFERENCES Animal(id_animal),
    CONSTRAINT fk_vacinacao_vacina FOREIGN KEY (id_vacina) REFERENCES Vacina(id_vacina)
);
