To-Do List API – Teste Técnico Backend (Java)

*Descrição do Projeto:
Este projeto é uma API RESTful para gerenciamento de tarefas (To-Do List), desenvolvida como parte de um teste técnico para estágio backend em Java.
A aplicação utiliza Spring Boot, Spring Data JPA, H2 (banco em memória para testes) e segue boas práticas de desenvolvimento, 
incluindo tratamento centralizado de exceções e testes unitários com JUnit e Mockito.

##Tecnologias:

Java 17
Spring Boot 3.5.4
Spring Data JPA
H2 Database (teste)
JUnit 5 + Mockito
Maven

##Endpoints:

Método	  Endpoint	    Descrição
POST	   /tarefa	Cria  uma nova tarefa
GET	    /tarefa	Lista   todas as tarefas
GET	    /tarefa/{id}	  Retorna uma tarefa por ID
PUT    	/tarefa/{id}	  Atualiza status da tarefa
DELETE	/tarefa/{id}	  Deleta uma tarefa

##Testes:

TarefaServiceTest: testes unitários para métodos de negócio (criarTarefa, atualizarTarefa, etc.)
TarefaControleTest: testes com MockMvc para endpoints REST, simulando requisições HTTP e validando respostas.

##Configuração do H2:

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update

##Como Rodar:

Clone o repositório

Execute no terminal:
git clone <repositorio>
cd <projeto>
mvn clean install
mvn spring-boot:run
API disponível em http://localhost:8080/tarefa


##Segue a baixao exemplos de codigo JSON, para teste da api: 

##Criar uma tarefa pai com subtarefas:

POST /tarefa

{										
  "titulo": "Tarefa Pai",															
  "descricao": "Descrição da tarefa pai",		
  "dataVencimento": "2025-08-20T12:00:00",
  "status": "PENDENTE",
  "prioridade": "ALTA",
  "subtarefas": [
    {
      "titulo": "Subtarefa 1",
      "descricao": "Descrição da subtarefa",
      "dataVencimento": "2025-08-18T12:00:00",
      "status": "PENDENTE",
      "prioridade": "MEDIA"
    },
    {
      "titulo": "Subtarefa 2",
      "descricao": "Outra subtarefa",
      "dataVencimento": "2025-08-19T12:00:00",
      "status": "PENDENTE",
      "prioridade": "BAIXA"
    }
  ]
}

##Atualizar uma tarefa existente:

PUT /tarefa/{id}
{
  "titulo": "Tarefa Atualizada",
  "descricao": "Descrição atualizada",
  "dataVencimento": "2025-08-22T12:00:00",
  "status": "PENDENTE",
  "prioridade": "ALTA"
}
##Atualizar o status de uma tarefa:
PATCH /tarefa/{id}/status
{
  "status": "CONCLUIDO"
}


##Deleta uma tarefa 

DELETE /tarefa/id


OBS: Lembre que não dá para concluir uma tarefa pai se alguma subtarefa ainda estiver pendente.


##carregar todas as tarefa:

GET /tarefa
Sem filtro: retorna todas as tarefas.

Por status: 
GET /tarefa?status=PENDENTE

Por prioridade:
GET /tarefa?prioridade=ALTA

Por intervalo de vencimento:
GET /tarefa?status=PENDENTE&prioridade=ALTA&vencimentoInicio=2025-08-15T00:00:00&vencimentoFim=2025-08-20T23:59:59

##Combinando filtros:
GET /tarefa?status=PENDENTE&prioridade=ALTA&vencimentoInicio=2025-08-15T00:00:00&vencimentoFim=2025-08-20T23:59:59

Observação: Os valores de status e prioridade devem exatamente corresponder aos nomes dos enums (PENDENTE, CONCLUIDO, BAIXA, MEDIA, ALTA).




