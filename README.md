# Projeto: Automação de Testes API

## Sobre
Repositório dedicado à automação de testes API com RestAssured.

Este caso de Teste E2E tem como objetivo verificar as funcionalidades da API "ReqRes" disponivel em https://reqres.in/ 


## Tecnologias Utilizadas
- Java
- RestAssured
- Junit 5
- Maven

## Estrutura do Projeto

A estrutura do projeto é organizada da seguinte forma:

- src
    - test/java/com/example
        - domain
            - User.java
        - factories
            - UserBuilder.java
         
        - testes
             - BaseTest.java
             - DomainUserTest.java
             - LoginTest.java
             - RegisterTest.java
             - UnknownTest.java
             - UsersTest.java
     
- pom.xml

## Instruções de Execução

1. Clonar o repositório, instalar as dependências


2. Executar testes
```
mvn test
```
