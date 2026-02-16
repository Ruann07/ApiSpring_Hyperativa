# ApiSpring_Hyperativa

API REST desenvolvida em Java com Spring Boot para:

Autenticação com JWT

Cadastro de usuários

Armazenamento seguro de cartões

Upload de cartões via arquivo TXT

Consulta de cartão por número

# Tecnologias utilizadas

Java 21

Spring Boot

Spring Security + JWT

MySQL

JUnit + MockMvc

AES/GCM para criptografia

SHA-256 para hash de número do cartão

# Configure
Antes de execultar a api gere as duas chaves secret com o openssl e adicione dentro do application.yml:

JWT - execute openssl rand -base64 32 

CRYPTO - openssl rand -hex 32 

