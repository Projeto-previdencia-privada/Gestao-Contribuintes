# Gestão de Contribuintes
Este é um projeto para uma aplicação de gestão de contribuintes, onde você pode cadastrar, atualizar, listar e desativar contribuintes.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Docker
- Swagger

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

# Requisição GET para Obter Contribuinte:
Para obter a lista de contribuintes, você pode fazer uma requisição GET para a seguinte URL:

```GET http://localhost:8081/contribuintes```: Retorna os contribuintes cadastrados.

# Exemplo de Requisição POST para adicionar contribuintes:

Endpoint: ```http://localhost:8081/contribuintes```

```
    {
        "nomeCivil": "Emmanuel da Costa",
        "nomeSocial": "Emmanuel",
        "endereco": "Rua sem nome, Casa 10, bairro nobre, RJ",
        "email": "emmanuel@costa.com",
        "salario": 10000.00,
        "categoria": "MEI",
        "telefone": "21985246666",
        "inicioContribuicao": "01/07/2006",
        "tipoRelacionamento": null,
        "cpfPai": "11122233304",
        "cpfMae": "11122233305",
        "cpfAvô": null,
        "cpfAvó": null,
        "cpfconjuge": "11122233303",
        "cpf": "11122233302"
    }
```

## Requisiçao GET para obter informações pertinentes a contribuição do Contribuintes

``` GET /contribuinte/{cpf_contribuinte}```: Retorna as informações do contribuinte pertinentes a contribuição

# Exemplo de requisição GET para as informações do contribuinte

Endpoint: ```http://localhost:8081/contribuintes/{cpf_contribuinte}```

```
        "inicioContribuicao": "01/07/2006"
        "salario": 10000.00,
        "categoria": "MEI",
        "cpf": "11122233302"
```

# Dependentes

- GET /contribuintes/{cpf_contribuinte}/dependentes: Retorna os dependentes do contribuinte.
- POST /contribuintes/{cpf_contribuinte}/dependentes: Cadastra um novo dependente ao contribuinte.

# Requisição POST para Adicionar Dependente:
Para adicionar um novo dependente a um contribuinte existente, você pode fazer uma requisição POST para a seguinte URL:

```POST http://localhost:8081/contribuintes/{cpf_contribuinte}/dependentes```

Substitua {cpf_contribuinte} pelo CPF do contribuinte ao qual você deseja adicionar o dependente.

## Corpo da Requisição (JSON):
```
{
    "cpf": "CPF_do_Dependente",
    "nome": "Nome_do_Dependente"
}
```

Exemplo de Corpo da Requisição:

# Requisição GET para Obter Dependentes de um Contribuinte:
Para obter a lista de dependentes de um contribuinte, você pode fazer uma requisição GET para a seguinte URL:

```GET http://localhost:8081/contribuintes/{cpf_contribuinte}/dependentes```

Substitua {cpf_contribuinte} pelo CPF do contribuinte do qual você deseja obter os dependentes.

# Familia

- GET /contribuintes/familia/{cpf do contribuinte}: Retorna uma arvore genealogica, apresentando os familiares do contribuinte.

# Requisição GET para Obter a familia do Contribuinte:
Para obter a lista da árvore genealógica de um contribuintes, você pode fazer uma requisição GET para a seguinte URL:

```GET http://localhost:8081/contribuintes/familia/{cpf}```

Substitua {cpf_contribuinte} pelo CPF do contribuinte do qual você deseja obter os familiares.
