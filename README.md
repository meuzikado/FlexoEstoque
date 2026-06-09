FlexoEstoque 📊

**Sistema de Controle de Estoque e Fluxo de Bobinas para Indústria Gráfica (Setor de Flexografia)**

O **FlexoEstoque** é uma API REST desenvolvida em Java com Spring Boot projetada para solucionar falhas no fluxo de retirada de bobinas em indústrias gráficas. O sistema substitui o controle informal por um fluxo concreto de saídas e rastreabilidade, permitindo o abatimento automatizado do estoque por **metragem linear** e a geração de relatórios consolidados diários para o PCP (Planejamento e Controle de Produção) e fechamento de turnos.

---

## 🚀 Funcionalidades Principais

* **Gestão de Bobinas (CRUD):** Cadastro, alteração, exclusão e listagem de bobinas com propriedades específicas do setor de flexografia (Tipo de Material, Largura em mm, Gramatura/Micras, Peso Atual em KG, Metragem Linear e Número do Lote).
* **Fluxo de Retirada por Metragem:** Baixa automatizada de estoque baseada na metragem linear solicitada por uma Ordem de Serviço (OS) específica.
* **Cálculo Inteligente de Insumos:** O sistema calcula de forma automatizada a quantidade estimada de bobinas físicas retiradas com base na metragem padrão do lote.
* **Motor de Relatórios Diários:** Endpoint centralizado que consolida toda a metragem rodada no dia atual, o total de bobinas que saíram do estoque físico e o histórico detalhado de todas as OS's atendidas nas últimas 24 horas.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 25
* **Framework Principal:** Spring Boot 3.x
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados de Produção:** MySQL 8.x
* **Banco de Dados de Testes:** H2 Database (Memória)
* **Ferramenta de API:** Postman

---

## 📐 Arquitetura do Projeto

O projeto segue o padrão arquitetural de camadas (MVC), garantindo a separação de responsabilidades e facilitando a manutenção do código:

* **`Model / Entity`:** Mapeamento das tabelas do banco de dados relacional (`Bobina` e `MovimentacaoEstoque`) e estruturação de objetos de transferência de dados (DTO) para relatórios.
* **`Repository`:** Interfaces que herdam o `JpaRepository`, responsáveis pelas consultas automáticas e transações com o banco de dados via Spring Data.
* **`Service`:** Camada de regras de negócio encapsuladas, responsável pelas validações de saldo (bloqueio de retiradas caso a metragem do estoque seja insuficiente) e cálculos proporcionais.
* **`Controller`:** Exposição dos endpoints REST da API para comunicação via JSON.

---
