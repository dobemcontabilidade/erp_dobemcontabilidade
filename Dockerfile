# Stage 1: Build the Node.js frontend
FROM node:20 AS node_builder

WORKDIR /app

# Instalar o Python e build-essential (necessário para compilar extensões nativas)
RUN apt-get update && apt-get install -y python3 build-essential

# Definir a configuração do npm para usar o Python 3
RUN npm config set python python3

# Copiar package.json e package-lock.json para aproveitar o cache do Docker
COPY package.json package-lock.json /app/

# Instalar dependências do Node.js
RUN npm install

# Copiar o restante do código-fonte da aplicação
COPY . /app

# Se você tiver um script de build específico, execute-o aqui
# Por exemplo, se estiver usando React ou Angular, execute o comando de build
# RUN npm run build

# Stage 2: Build the Spring Boot application
FROM maven:3.8.3-openjdk-17 AS maven_builder

WORKDIR /app

# Copiar o projeto inteiro
COPY . /app

# Copiar os arquivos de frontend construídos se você tiver um diretório separado para artefatos de frontend
# COPY --from=node_builder /app/build /app/src/main/resources/static

# Empacotar a aplicação
RUN ./mvnw package -Pprod -DskipTests

# Stage 3: Create the final image with the application
FROM openjdk:17-jdk-alpine

WORKDIR /opt/app

# Copiar o JAR do Spring Boot do estágio de build do Maven
COPY --from=maven_builder /app/target/*.jar /opt/app/app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Executar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
