# Etapa 1: Use a imagem base do Maven para construir o JAR
FROM maven:3.8.5-openjdk-17 AS build

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo pom.xml e as dependências do Maven para o diretório de trabalho do contêiner
COPY pom.xml .

# Baixe as dependências do projeto (isto é feito separadamente para aproveitar o cache do Docker)
RUN mvn dependency:go-offline -B

# Copie todo o código fonte para o diretório de trabalho do contêiner
COPY src ./src

# Execute a construção do projeto Maven para criar o arquivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Use uma imagem menor do JDK para executar o JAR
FROM openjdk:17-alpine

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o JAR construído da etapa anterior para o diretório de trabalho do contêiner
COPY --from=build /app/target/gestao-contribuintes-0.0.1-SNAPSHOT.jar app.jar

# Defina o ponto de entrada do contêiner para executar o JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
