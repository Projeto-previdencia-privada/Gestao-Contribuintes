CREATE SCHEMA IF NOT EXISTS contribuintes;

CREATE TABLE IF NOT EXISTS contribuintes.contribuintes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome_civil VARCHAR(100),
    nome_social VARCHAR(100),
    endereco VARCHAR(255),
    email VARCHAR(100),
    salario NUMERIC(10,2),
    categoria VARCHAR(50),
    telefone VARCHAR(20),
    inicio_contribuicao DATE,
    tipo_relacionamento VARCHAR(50),
    cpf_pai VARCHAR(11),
    cpf_mae VARCHAR(11),
    cpf_pai2 VARCHAR(11),
    cpf_mae2 VARCHAR(11),
    cpf_conjuge VARCHAR(11),
    status BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS contribuintes.dependentes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS contribuintes.contribuintes_dependentes (
    cpf_contribuinte VARCHAR(11),
    cpf_dependente VARCHAR(11),
    PRIMARY KEY (cpf_contribuinte, cpf_dependente),
    FOREIGN KEY (cpf_contribuinte) REFERENCES contribuintes.contribuintes(cpf),
    FOREIGN KEY (cpf_dependente) REFERENCES contribuintes.dependentes(cpf)
);

CREATE TABLE IF NOT EXISTS contribuintes.contribuintes_conjuges (
    cpf_contribuinte VARCHAR(11),
    cpf_conjuge VARCHAR(11),
    PRIMARY KEY (cpf_contribuinte, cpf_conjuge),
    FOREIGN KEY (cpf_contribuinte) REFERENCES contribuintes.contribuintes(cpf),
    FOREIGN KEY (cpf_conjuge) REFERENCES contribuintes.contribuintes(cpf)
);
