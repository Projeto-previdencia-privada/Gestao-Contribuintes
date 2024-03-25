CREATE TABLE contribuintes(
    cpf varchar(255) NOT NULL,
    categoria varchar(255),
    email varchar(255),
    endereco varchar(255),
    nome_civil varchar(255),
    nome_social varchar(255),
    salario numeric,
    telefone varchar(255),
    inicio_contribuicao date,
    elefone varchar(255),
    PRIMARY KEY(cpf)
);

CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE dependentes (
    cpf VARCHAR(255) NOT NULL,
    contribuinte_cpf VARCHAR(255),
    cpf_responsavel VARCHAR(255),
    responsavel_cpf VARCHAR(255),
    nome_dependente VARCHAR(255),
    PRIMARY KEY(cpf),
    CONSTRAINT fk_dependentes_contribuintes FOREIGN KEY(contribuinte_cpf) REFERENCES contribuintes(cpf),
    CONSTRAINT fk_dependentes_responsavel FOREIGN KEY(cpf_responsavel) REFERENCES contribuintes(cpf),
    CONSTRAINT fk_dependentes_responsavel_principal FOREIGN KEY(responsavel_cpf) REFERENCES contribuintes(cpf)
);

CREATE TABLE email (
    endereco VARCHAR(255) NOT NULL,
    cpf_contribuinte VARCHAR(255),
    PRIMARY KEY(endereco),
    CONSTRAINT fk_email_contribuinte FOREIGN KEY(cpf_contribuinte) REFERENCES contribuintes(cpf)
);


CREATE TABLE endereco (
    cpf_contribuinte VARCHAR(255) NOT NULL,
    bairro VARCHAR(255),
    cep VARCHAR(255),
    cidade VARCHAR(255),
    complemento VARCHAR(255),
    estado VARCHAR(255),
    logradouro VARCHAR(255),
    numero VARCHAR(255),
    PRIMARY KEY(cpf_contribuinte),
    CONSTRAINT fk_endereco_contribuinte FOREIGN KEY(cpf_contribuinte) REFERENCES contribuintes(cpf)
);


CREATE TABLE filiacao(
    cpf varchar(255) NOT NULL,
    cpf_contribuinte varchar(255),
    cpf_dependente varchar(255),
    cpf_responsavel varchar(255),
    PRIMARY KEY(cpf),
    CONSTRAINT fk5vklxh8qfp20d0dak7s4ac1g6 FOREIGN key(cpf_contribuinte) REFERENCES contribuintes(cpf),
    CONSTRAINT fkiim6yvrohrlgfnol9k2f9cmq4 FOREIGN key(cpf_dependente) REFERENCES contribuintes(cpf),
    CONSTRAINT fklml394gefdwgb6nkd8wd3eet2 FOREIGN key(cpf_responsavel) REFERENCES responsavel(cpf),
    CONSTRAINT fk62s4rje657r85ap1uhnm4idod FOREIGN key(cpf_responsavel) REFERENCES contribuintes(cpf)
);

CREATE TABLE responsavel(
    cpf varchar(255) NOT NULL,
    nome varchar(255),
    PRIMARY KEY(cpf)
);

CREATE TABLE telefone(
    cpf varchar(255) NOT NULL,
    numero varchar(255),
    tipo varchar(255),
    cpf_contribuinte varchar(255),
    PRIMARY KEY(cpf),
    CONSTRAINT fkdtb26x1xginiebe0418xj0is4 FOREIGN key(cpf_contribuinte) REFERENCES contribuintes(cpf)
);