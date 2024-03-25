# Gestão de Contribuintes
Este é um projeto para uma aplicação de gestão de contribuintes, onde você pode cadastrar, atualizar, listar e desativar contribuintes.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL 

## Properties

- spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_contribuintes
- spring.datasource.username=seu_usuario
- spring.datasource.password=sua_senha

# Configuração e Execução

Clone o repositório: ``` git clone https://github.com/Projeto-previdencia-privada/Gestao-Contribuintes ```

Importe o projeto em sua IDE de preferência.

Certifique-se de ter o Maven configurado em seu ambiente.

Execute a aplicação. Isso pode ser feito executando a classe principal GestaoContribuintesApplication.java ou via linha de comando:

# Endpoints da API

- GET /contribuintes: Retorna todos os contribuintes cadastrados.
- POST /contribuintes: Cadastra um novo contribuinte.
- PUT /contribuintes/{cpf}: Atualiza os dados de um contribuinte existente.
- DELETE /contribuintes/{cpf}: Desativa um contribuinte existente.

# Exemplo de Requisição POST

Endpoint: http://localhost:8080/contribuintes
```
{
    "cpf": "00565454522",
    "nomeCivil": "Catarina Constantine Araujo",
    "nomeSocial": "Catarina Constantine",
    "salario": 35000.00,
    "inicioContribuicao": "2029-03-07",
    "enderecos": [],
    "telefones": [],
    "emails": [],
    "categoria": {
        "id": 1
    },
    "responsavelDe": [],
    "dependenteDe": []
}
```