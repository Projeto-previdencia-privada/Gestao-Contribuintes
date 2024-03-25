# Gestão de Contribuintes
Este é um projeto de exemplo para uma aplicação de gestão de contribuintes, onde você pode cadastrar, atualizar, listar e desativar contribuintes.

# Tecnologias Utilizadas
Java
Spring Boot
Spring Data JPA
Hibernate
PostgreSQL (ou o banco de dados de sua preferência)
Configuração do Banco de Dados
Certifique-se de ter um servidor PostgreSQL em execução e atualize as configurações de banco de dados no arquivo application.properties.

# properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_contribuintes
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
Configuração e Execução
Clone o repositório:
bash
Copy code
git clone https://github.com/Projeto-previdencia-privada/Gestao-Contribuintes
Importe o projeto em sua IDE de preferência.

Certifique-se de ter o Maven configurado em seu ambiente.

Execute a aplicação. Isso pode ser feito executando a classe principal GestaoContribuintesApplication.java ou via linha de comando:


mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080.

# Endpoints da API
GET /contribuintes: Retorna todos os contribuintes cadastrados.
POST /contribuintes: Cadastra um novo contribuinte.
PUT /contribuintes/{cpf}: Atualiza os dados de um contribuinte existente.
DELETE /contribuintes/{cpf}: Desativa um contribuinte existente.

# Exemplo de Requisição POST

Endpoint: http://localhost:8080/contribuintes

json
Copy code
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

# Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests ou abrir issues se você encontrar algum problema.