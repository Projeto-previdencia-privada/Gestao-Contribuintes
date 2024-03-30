CREATE TABLE contribuintes (
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
    cpf_avô VARCHAR(11),
    cpf_avó VARCHAR(11)
);

-- Criação da tabela de dependentes
CREATE TABLE dependentes (
    cpf VARCHAR(11) PRIMARY KEY,  -- Chave primária definida como CPF
    nome VARCHAR(100),
    data_nascimento DATE,
    responsavel_id VARCHAR(11),  -- chave estrangeira referenciando a tabela de contribuintes
    FOREIGN KEY (responsavel_id) REFERENCES contribuintes(cpf)
);

-- Criação da tabela de junção
CREATE TABLE contribuintes_dependentes (
    cpf_contribuinte VARCHAR(11),
    cpf_dependente VARCHAR(11),
    PRIMARY KEY (cpf_contribuinte, cpf_dependente),
    FOREIGN KEY (cpf_contribuinte) REFERENCES contribuintes(cpf),
    FOREIGN KEY (cpf_dependente) REFERENCES dependentes(cpf)
);