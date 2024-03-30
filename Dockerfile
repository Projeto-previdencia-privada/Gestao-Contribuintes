# Use a imagem base com o Java 17 Alpine
FROM openjdk:17-alpine

# Definir argumento com o caminho do arquivo .jar
ARG JAR_FILE=target/gestao-contribuintes-0.0.1-SNAPSHOT.jar

# Copiar o arquivo .jar para o container
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
