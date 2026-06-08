# Documento de Requisitos do Produto (PRD)

## Projeto: FlexoStock – Sistema de Controle de Estoque e Fluxo de Bobinas
**Contexto:** Indústria Gráfica / Setor de Flexografia  
**Objetivo:** Solucionar o gargalo no fluxo de retirada de bobinas, garantindo rastreabilidade, controle de saldos e geração de relatórios diários de consumo para planejamento e fechamento de turnos.  
**Autor:** S. Neto  
**Data:** Junho de 2026  

---

## 1. Visão Geral do Produto
No setor de flexografia, o controle preciso do estoque de bobinas (sejam de filme plástico, papel, liner ou alumínio) é crítico. A falta de um fluxo concreto de registro de saídas gera furos de estoque, paradas de máquina por falta de insumo ou compras emergenciais.

O **FlexoStock** é uma solução de backend em Spring Boot projetada para digitalizar e organizar a entrada, a movimentação e, principalmente, a **retirada programada/realizada de bobinas** do estoque. O sistema focará na geração de relatórios diários automatizados para que a gerência e o PCP (Planejamento e Controle de Produção) tenham visibilidade total do fluxo físico de materiais consumidos no dia.

---

## 2. Objetivos do Projeto
1. **Rastreabilidade de Insumos:** Controlar o saldo físico de bobinas por tipo de material, gramatura, largura e peso/metragem.
2. **Organização do Fluxo de Retirada:** Eliminar a retirada informal de bobinas sem o devido registro no sistema.
3. **Relatórios de Consumo Diário:** Gerar relatórios consolidados automáticos dos materiais utilizados por dia/turno para alimentar o PCP e a contabilidade de custos da gráfica.
4. **Evolução Acadêmica e Prática:** Servir como o primeiro projeto completo em Spring Boot do desenvolvedor, aplicando conceitos de arquitetura em camadas, persistência de dados (JPA/Hibernate) e bancos de dados relacionais.

---

## 3. Escopo do Sistema (Funcionalidades)

### Módulo 1: Cadastro e Gestão de Bobinas (CRUD)
* **Cadastrar Bobina/Material:** Registro de novos lotes de bobinas no estoque com os seguintes atributos essenciais para a flexografia:
    * `id` (Código interno/SKU)
    * `tipoMaterial` (Ex: BOPP Transparente, PEBD, Papel Couchê, Alumínio)
    * `largura` (Em milímetros - ex: 400mm, 600mm)
    * `gramatura` (Em $g/m²$ ou micras para filmes - ex: 20 micras, 80g)
    * `pesoInicial` e `pesoAtual` (Em kg, controle fundamental em flexografia)
    * `metragemLinear` (Quantidade de metros na bobina)
    * `fornecedor` e `numeroLote`
* **Modificar Cadastro:** Ajustar metragens, pesos ou corrigir dados cadastrais.
* **Excluir Registro:** Remoção lógica do insumo caso tenha sido cadastrado errado (impedindo exclusão se já houver histórico de consumo).
* **Listar Estoque Atual:** Tela ou endpoint de consulta para o operador verificar quais bobinas estão disponíveis e qual o saldo em KG/Metros.

### Módulo 2: Fluxo de Movimentação (Retirada e Consumo)
* **Registrar Ordem de Retirada (Saída para Produção):** Quando a produção iniciar um trabalho na impressora flexográfica, o operador registra a retirada da bobina informando:
    * A bobina específica utilizada (via ID/Lote).
    * O número da Ordem de Produção (OP) ou serviço que vai consumir aquela bobina.
    * A data e hora exatas da retirada.
* **Atualização Automática de Saldo:** O sistema abate o peso/metragem consumido ou marca a bobina como "Totalmente Consumida".

### Módulo 3: Relatórios de Serviços e Consumo Diário (O Mais Importante)
* **Filtro por Data Atual:** O sistema deve agrupar automaticamente todas as retiradas que aconteceram na data do dia.
* **Relatório de Consumo por Tipo de Material:** Total de quilos (KG) e metros lineares rodados no dia de BOPP, Papel, etc.
* **Relatório de OPs/Serviços Atendidos:** Quais ordens de serviço foram rodadas na flexografia durante o dia e quais insumos consumiram.
* **Indicador de Alerta de Estoque Mínimo:** Lista de bobinas/materiais que atingiram o nível crítico no dia e precisam de reposição urgente.

---

## 4. Requisitos Técnicos & Arquitetura
* **Linguagem:** Java 17 ou superior.
* **Framework:** Spring Boot 3.x.
* **Camadas do Projeto:**
    * `Model/Entity`: Definição das tabelas de Bobinas e Movimentações.
    * `Repository`: Interfaces que herdam o `JpaRepository` para comunicação direta com o banco.
    * `Service`: Onde ficarão as regras (Ex: Não permitir retirar uma bobina que já consta como zerada).
    * `Controller`: Exposição das rotas da API (`/api/bobinas`, `/api/movimentacoes`, `/api/relatorios`).
* **Bancos de Dados:**
    * *Fase Inicial:* **H2 Database** (em memória para agilizar os testes).
    * *Fase Final (Produção):* **PostgreSQL** ou **MySQL** (para salvar os dados definitivamente na gráfica).

---

## 5. Roteiro de Desenvolvimento Progressivo (Passo a Passo)

```
[Fase 1: CRUD de Bobinas] ➔ [Fase 2: Persistência H2] ➔ [Fase 3: Fluxo de Retirada] ➔ [Fase 4: Motor de Relatórios] ➔ [Fase 5: Banco Real]
```

### 🛠️ Fase 1: O Início (API de Bobinas em Memória)
* **O que construir:** Criar as classes `Bobina` e `BobinaController`. Configurar uma lista provisória para testar os métodos `GET`, `POST`, `PUT` e `DELETE`.
* **Objetivo de Aprendizado:** Dominar a estrutura de pastas do VS Code e entender como enviar/receber dados de bobinas via JSON.

### 💾 Fase 2: Integração com Banco de Dados Em Memória (H2)
* **O que construir:** Adicionar as dependências do Spring Data JPA e H2 no `pom.xml`. Anotar a classe `Bobina` como `@Entity`. Criar a interface `BobinaRepository`.
* **Objetivo de Aprendizado:** Compreender como o Spring mapeia automaticamente classes Java em tabelas de banco de dados, eliminando o gerenciamento manual de listas.

### ⚙️ Fase 3: Camada de Negócio e Fluxo de Saída (Retirada)
* **O que construir:** Criar a entidade `MovimentacaoEstoque` (com data, quantidade retirada e número da OP). Criar o pacote `Service` (`BobinaService`). Implementar a lógica: sempre que houver uma saída, o sistema localiza a bobina, diminui seu peso atual e salva o histórico da movimentação.
* **Objetivo de Aprendizado:** Praticar relacionamentos entre tabelas (`@ManyToOne` / `@OneToMany`) e encapsular regras de negócio fora do Controller.

### 📊 Fase 4: O Coração do Projeto (Geração dos Relatórios Diários)
* **O que construir:** Criar métodos customizados no repositório de movimentações usando datas (`LocalDate.now()`). Criar o `RelatorioController` com o endpoint `/api/relatorios/diario`. Esse método vai somar e listar tudo o que foi retirado para a flexografia nas últimas 24 horas.
* **Objetivo de Aprendizado:** Aprender a trabalhar com funções agregadas no SQL/JPA (`SUM`, `COUNT`) e manipulação de datas e períodos em Java.

### 🔒 Fase 5: Estabilização e Banco de Dados de Produção
* **O que construir:** Substituir o banco H2 pelo **PostgreSQL** ou **MySQL** instalados localmente. Configurar o arquivo `application.properties` com as credenciais seguras da gráfica. Adicionar tratamentos de erro (ex: exibir mensagem amigável se tentarem retirar uma bobina que não existe).
* **Objetivo de Aprendizado:** Preparar uma aplicação para rodar em um cenário real de produção industrial.
