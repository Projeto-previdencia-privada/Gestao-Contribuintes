# Use a imagem base com o Java 17 Alpine
FROM openjdk:17-alpine

# Definir diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR pré-construído para o diretório de trabalho do contêiner
COPY target/gestao-contribuintes-0.0.1-SNAPSHOT.jar app.jar

# Definir ponto de entrada do contêiner para executar o JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
