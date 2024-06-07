# Use a imagem base com o Java 17 Alpine
FROM openjdk:17-alpine

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do Maven Wrapper e do projeto para o diretório de trabalho do contêiner
COPY .mvn/ .mvn/
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src/ src/

# Garantir que o script Maven Wrapper tenha permissões de execução
RUN chmod +x mvnw

# Listar arquivos no diretório de trabalho para depuração
RUN ls -la && ls -la .mvn/wrapper/

# Executar Maven Wrapper para limpar e empacotar o projeto, criando o arquivo JAR
RUN ./mvnw clean package -DskipTests

# Copiar o arquivo JAR gerado para o diretório de trabalho do contêiner
COPY target/gestao-contribuintes-0.0.1-SNAPSHOT.jar app.jar

# Definir ponto de entrada do contêiner para executar o JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]
