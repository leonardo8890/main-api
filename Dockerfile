# Estágio 1: Build (Compilação)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências (melhora o cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte e compila o projeto ignorando os testes
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Run (Execução)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8081 para evitar conflito com o seu serviço atual na 8080
EXPOSE 8081

# Inicia a aplicação forçando a porta 8081 ou a porta definida pelo Render via variável de ambiente $PORT
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]