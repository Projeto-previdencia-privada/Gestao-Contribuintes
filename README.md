# Gestão de Contribuintes
Este é um projeto para uma aplicação de gestão de contribuintes, onde você pode cadastrar, atualizar, listar e desativar contribuintes.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Docker

# Configuração e Execução

Clone o repositório: ``` git clone https://github.com/Projeto-previdencia-privada/Gestao-Contribuintes ```

Importe o projeto em sua IDE de preferência.

Certifique-se de ter o Maven configurado em seu ambiente.

Execute a aplicação. Isso pode ser feito executando a classe principal GestaoContribuintesApplication.java ou via linha de comando.

## Execução com Docker Compose
 utilize o comando: ```docker compose up --build```

# Endpoints da API

- GET /contribuintes: Retorna todos os contribuintes cadastrados.
- POST /contribuintes: Cadastra um novo contribuinte.
- PUT /contribuintes/{cpf}: Atualiza os dados de um contribuinte existente.
- DELETE /contribuintes/{cpf}: Desativa um contribuinte existente.

# Exemplo de Requisição POST

Endpoint: http://localhost:8080/contribuintes
```
    {
        "categoria": "MEI",
        "inicio_contribuicao": "25/03/2012",
        "dependentes": [],
        "cpf": "05264444888",
        "nome_civil": "Maria de Fatima de Oliveira",
        "endereco": "Quadra 22 Lote 9 casa 1002, MT",
        "nome_social": "Maria de Fatima",
        "email": "mariafatima@hotmail.com",
        "telefone": "28996663225",
        "salario": 1000.00
    }
```