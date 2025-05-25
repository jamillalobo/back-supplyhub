- Link do front-end: https://github.com/CaioFurtadoo/SupplyHub
- Link dos log trackers atualizado: https://drive.google.com/drive/folders/18BUcdpxpvCjsHbENt7K6asfZsgIOjCQl?usp=sharing

# Sistema de Gerenciamento de Inventário de Alimentos
Este projeto é um sistema de gerenciamento de inventário, focado no controle de estoques e dispensa de frios, carnes e outros alimentos. O objetivo principal é gerenciar e atualizar a quantidade de itens no estoque, garantir a rastreabilidade das baixas de produtos e manter o controle adequado dos insumos para evitar desperdícios e garantir a qualidade dos produtos.

O sistema foi desenvolvido para a disciplina de `Programação Orientada a Objetos` na turma de `ADS Embarque Digital - Cesar School`.

## Funcionalidades
- Controle de Estoques: Gerenciamento das quantidades de frios, carnes e outros alimentos no estoque.
- Baixa de Produtos: Registro de saídas de produtos do estoque, seja por venda, consumo interno ou outros motivos.
- Interface Amigável: Uma interface simples e eficaz para que os usuários possam realizar as operações de forma intuitiva.

## Tecnologias Utilizadas
- Frontend: React.js
- Back-end: Java (Spring Boot)
- Banco de Dados: MySQL/PostgreSQL (em análise)
- Ferramentas: Figma (UI/UX), GitHub Projects (quadro Kanban)

## Como Executar o Projeto
- Clone o repositório:
```
git clone https://github.com/jamillalobo/back-supplyhub.git

```

## Entrega 1
- Link protótipo Lo-fi: https://www.figma.com/design/WvTRqMYeRcnLiVc6AZAqCj/POO?node-id=0-1&p=f
- Link user stories: https://trello.com/b/jDt7Iy8l/supply-hub
- Link do screencast: https://youtu.be/Tl1fqRsO7uk?si=pEOzHgj5rj6XKEs5

## Autores
- Arthur Tavares Porto
- Caio Furtado de Miranda Carvalho
- Coraline Rodrigues de Oliveira
- Jamilla Soares Lobo
- Lucas Antônio Deodato
- Luis Henrique Facunde da Silva

# Pré requisitos pra testar
- Instalar Java 17 
- Instalar Maven (versão 3.9.9 - link: https://maven.apache.org/download.cgi)
- Instalar Docker desktop
- Instalar Insomnia (pra testar as requests)

# Como testar
1. Com todos os programas anteriores instalados, dê um push na branch `feat/register-user`
2. Modifique no arquivo `aplication.properties` as propriedades que conectam a aplicação spring boot com o banco no docker 
3. Crie uma pasta `jwt` no `resources`
4. No terminal, vá para essa pasta e rode os 2 comandos `openssl` abaixo:
```
cd src/main/resources/jwt
openssl genpkey -algorithm RSA -out app.key -outform PEM
openssl rsa -pubout -in app.key -out app.pub
```
5. Verifique se foi criado os arquivos `app.hub` e `app.key` na pasta `jwt` de `resources`
6. No terminal do Intellij, rode esse comando (para criar a imagem do DB no docker):
```
docker compose up --build

obs: importante ter o docker desktop aberto pra esse comando funcionar
```
7. Rode o projeto, se tudo der certo, algo como essas mensagens vão aparecer:
```
Tomcat started on port 8080 (http) with context path '/'
Started SupplyHubApplication in 7.414 seconds (process running for 8.03)
```
8. No postman, insominia ou qualquer outro aplicativo para testar requests, teste as requestes dessa forma:
```
Register a new user:
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d ‘{"username": "testuser", "email": "[test@example.com](mailto:test@example.com)", "password": "password123"}’

Log in and get a JWT token:
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d {"username" : "testuser", "password": "password123"}

Access the user profile (protected endpoint):
curl -X GET http://localhost:8080/api/user/me 
-H “Authorization: Bearer <JWT_TOKEN>”
```
