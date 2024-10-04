
# Documentação da Aplicação

API responsável por fazer o controle de registros de ponto entrada e saída

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.3.1
- Spring Data JPA
- Banco de dados H2
- Swagger com SpringDoc OpenAPI 3
- JUnit
- Mockito

## Configuração do Projeto

- **Configuração do Banco de Dados**: Utilização do H2 Database para desenvolvimento. Verifique o arquivo `application.properties` para detalhes.
- Link para acesso ao banco H2: http://localhost:8087/h2-console

## Executando a Aplicação

- Para executar a aplicação utilize IDE de sua escolha ou navege até a pasta e execute o comando:

	bash ./mvnw spring-boot:run

**Swagger**
- A API pode ser testada diretamente através do Swagger. Abra [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) no seu navegador para explorar e testar os endpoints disponíveis.

**Primeiros passos**
- Realizar o cadastro de um funcionario para gerar o ID
- Realizar as marcações de ponto com o numero de ID de funcionario gerado
