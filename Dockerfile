# Use a imagem base com o Java 17 Alpine
FROM openjdk:17-alpine

# Copie o artefato JAR da sua aplicação para a imagem
COPY target/*.jar /app/gestaocontribuintes.jar

# Exponha a porta da sua aplicação
EXPOSE 6666

# Comando para iniciar a sua aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "/app/gestaocontribuintes.jar"]
