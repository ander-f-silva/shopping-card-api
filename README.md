# Projeto Shopping Card

Desenvolvi uma solução construindo um endpoint de checkout para um fluxo de de carrinho de compras escrita em Java com Micronault.

## Documentação para construir a solução

Criei um Wiki para ilustrar os meus pensamentos e como planejei o projeto no link abaixo:

- [Wiki](https://github.com/ander-f-silva/shopping-card-api/wiki/Wiki-da-aplica%C3%A7%C3%A3o)

## Reflexão sobre o problema

Para construir a aplicação utilizei o framework Micronault usando a liguagem Java, por ter um start rápido para subir a aplicação e poder usar o GraalVM nativamente, outro ponto é que os "beans" são criados no momento da execução.
Utilizei este framework também por ser "fácil" a integração com serviço gGRCP e por aproveitar o conhecimento que tenho em Sprin Boot.
Por entender que o mundo de desenvolvimento esta globalizado, utilizei como idioma o Inglês para escrever o código e as apis.

Ponto impontante, não é de constume, mas para reproveitar a usabilidade de código estou utilizando o @lombok para criação dos Dtos.

## Tecnologias utilizadas

* Linguagem Java - Versão 11 (JDK GraalVM)

``` shell script
openjdk version "11.0.10" 2021-01-19
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.10+9)
Eclipse OpenJ9 VM AdoptOpenJDK (build openj9-0.24.0, JRE 11 Mac OS X amd64-64-Bit Compressed References 20210120_897 (JIT enabled, AOT enabled)
OpenJ9   - 345e1b09e
OMR      - 741e94ea8
JCL      - 0a86953833 based on jdk-11.0.10+9)
```

* Gradle 7 - Ferramenta de Build (O projeto já contem um wrapper usa-lo, não precisa)

``` shell script
------------------------------------------------------------
Gradle 7.2
------------------------------------------------------------

Build time:   2021-08-17 09:59:03 UTC
Revision:     a773786b58bb28710e3dc96c4d1a7063628952ad

Kotlin:       1.5.21
Groovy:       3.0.8
Ant:          Apache Ant(TM) version 1.10.9 compiled on September 27 2020
JVM:          11.0.10 (Eclipse OpenJ9 openj9-0.24.0)
OS:           Mac OS X 10.16 x86_64

```

* Repositório e versão de código - Github e Git;

* Banco de Dados - In memory;

* Server - Netty;

* Micronault - Framework para desenvolver o backend;

* Docker e Docker Composer.

## Documentação da API

Para testar as apis eu uso comando curl em um terminal linux (pode ser mac também).

### Chamadas de API

#### Realizar o checkout

``` shell script
curl --location --request POST 'http://localhost:8080/api/v1/checkout' \
--header 'Content-Type: application/json' \
--data-raw '{
    "products": [
        {
            "id": 3,
            "quantity": 1 
        },
         {
            "id": 4,
            "quantity": 2
        }
    ]
}'
```

Request:

```
{
    "products": [
        {
            "id": 3,
            "quantity": 1 
        },
         {
            "id": 4,
            "quantity": 2
        }
    ]
}
```

Response:

```
{
    "products": [
        {
            "id": 3,
            "quantity": 1,
            "discount": 3017,
            "unit_amount": 60356,
            "total_amount": 60356,
            "is_gift": false
        },
        {
            "id": 4,
            "quantity": 2,
            "discount": 2811,
            "unit_amount": 56230,
            "total_amount": 112460,
            "is_gift": false
        }
    ],
    "total_amount": 172816,
    "total_amount_with_discount": 166988,
    "total_discount": 5828
}
```

## Para realizar o build e os testes do programa

Primeiro passo faça o clone do projeto e depois siga os passos abaixo.

Segue:

Acesse o projeto

```
cd shopping-card-api
```

### Executar os testes

Execute na raiz do comando para rodar os testes:

```shell script
./gradlew test
```

### Executar o build

Para executar o build:

```shell script
./gradlew clean build
```
### Executar o deploy

Primeiro crie o banco de dados:

```shell script
docker-compose up -d
```

## Gestão do Projeto e técnicas para construção da API

Usei um Kaban que esta ilustrado na Wiki.

Para seguir os passos do desenvolvimento poder acessar as páginas com as PR criadas na ordem de baixo para cima.

Página: [Histórico Commit](https://github.com/ander-f-silva/shopping-card-api/pulls?q=is%3Apr+is%3Aclosed)

Nota: Todos os commits estão assinandos por mim e a data do Black Friday esta na váriavel de ambiente DATE_BLACK_FRIDAY dentro do docker-composer, caso queira mudar fique avontade.

