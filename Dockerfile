# Use uma imagem base com o Java e Maven instalados
FROM maven:3.6.3-openjdk-11-slim AS build

# Copie o código-fonte para o contêiner
COPY . /app

# Defina o diretório de trabalho como a pasta raiz da aplicação
WORKDIR /app

# Compile a aplicação
RUN mvn clean package

# Imagem de destino para a aplicação
FROM openjdk:11-jre-slim

# Copie o artefato construído para a imagem
COPY target/*.jar /app/gestao-contribuintes.jar

# Exponha a porta da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "/app/gestao-contribuintes.jar"]