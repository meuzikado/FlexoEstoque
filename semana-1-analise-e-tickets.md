# FlexoEstoque: análise inicial e tickets da Semana 1

Data: 3 nov 2026 · Meta: piloto apresentado ao encarregado em ~4 semanas · ~10 h/semana

> Observação honesta: esta análise é **estática** (li todo o código, o pom.xml, o README e o PRD). Não executei o projeto aqui. Onde digo "não sobe", é a conclusão da leitura; o Ticket FLX-01 é justamente você confirmar isso na sua máquina.

---

## 1. Stack encontrada

| Item | O que está no projeto | O que o plano pede | Comentário |
|---|---|---|---|
| Java | **25** (`pom.xml`) | 21 | Se o seu JDK for 21, o projeto nem compila. Decida um e alinhe pom + README. |
| Spring Boot | **4.0.6** | Spring Boot (3.x no README/PRD) | README e PRD dizem 3.x. Documentação desatualizada é um ponto que entrevistador nota. |
| Web | `spring-boot-starter-webmvc` | ok | |
| Persistência | Spring Data JPA / Hibernate | ok | Sem Flyway (o plano pede migrations). |
| Banco | Driver **MySQL** + módulo `h2console` | **PostgreSQL** | Não há driver H2 nem Postgres. Não há `application.properties`. |
| Testes | Só `contextLoads()` | JUnit/Mockito, >70% nos services | Praticamente zero cobertura. |
| Outros | Actuator | Docker, Swagger, CI | Nada disso existe ainda. Não há repositório Git no zip. |

**Estrutura:** `com.Estoque.demo` com pacotes `Controller`, `Model`, `repository`, `service`. Duas entidades (`Bobina`, `MovimentacaoEstoque`), um DTO (`RelatorioDiarioDTO`), dois repositories, um service, três controllers. Cerca de 350 linhas no total, ou seja: dá para você conhecer cada linha em uma semana.

---

## 2. Estado atual

### O que existe e faz sentido
- CRUD de bobina (`/api/bobinas`).
- Retirada por metragem vinculada a uma OS (`POST /api/movimentacoes/retirar`), com verificação de saldo e `@Transactional`.
- Relatório do dia (`GET /api/relatorios/diario`) somando metragem e bobinas.
- A ideia de domínio é boa e é sua: isso vale muito na entrevista e na apresentação.

### O que falta (comparando com o PRD e o plano)
- **Configuração de banco**: sem ela a aplicação provavelmente não sobe.
- **Entrada de material** (só existe saída).
- **Alerta de estoque mínimo** e **consumo por tipo de material** (o PRD chama o relatório de "o mais importante").
- `pesoInicial` e `fornecedor` do PRD não estão na entidade.
- Validação de entrada, tratamento global de erros, DTOs de entrada/saída.
- Testes, Docker, Swagger, README com como rodar.

### Riscos para a demo (em ordem de gravidade)
Não vou dizer onde está cada bug, porque isso vira ticket para você caçar na Semana 2. Mas saiba que eles existem:

1. **A aplicação não sobe** sem configuração de banco. Demo morta antes de começar.
2. **Dados somem ao reiniciar** se você usar banco em memória. Imagine reiniciar o PC na frente do encarregado.
3. **A regra de retirada aceita entradas que não deveria.** Há pelo menos uma entrada que faz o estoque *aumentar* numa retirada. Na frente de alguém do almoxarifado, isso derruba a confiança no sistema na hora.
4. **O cálculo de peso tem um caso de borda errado.** Pense em "o que acontece com o peso quando a bobina acaba".
5. **O número "quantidade de bobinas" usa um valor fixo** que não vem do cadastro.
6. **Respostas HTTP enganosas**: algumas operações que falham respondem como se tivessem dado certo.
7. **Excluir uma bobina que já teve retirada** vai estourar um erro feio (o PRD diz que isso deveria ser bloqueado).
8. **Relatório "do dia" vs. turno**: a gráfica trabalha em turno. Quem roda de 22h às 6h aparece em dois relatórios diferentes. Seu encarregado vai perguntar isso.

### Pontos de qualidade (não quebram a demo, mas contam em entrevista)
- Pacotes com letra maiúscula (`Controller`, `Model`, `com.Estoque`): fora da convenção Java.
- `BobinaController` fala direto com o repository, enquanto a retirada passa pelo service. Inconsistência de camadas.
- Injeção por campo (`@Autowired` no atributo) em vez de por construtor.
- Entidades JPA expostas direto no JSON.
- `double` para peso/metragem e `RuntimeException` genérica para tudo.

---

## 3. Visão das 4 semanas (≈40 h)

| Semana | Foco | Saída |
|---|---|---|
| 1 | Ler e explicar: camadas, banco, fluxo de requisição | Projeto rodando, no GitHub, e um `APRENDIZADOS.md` com suas explicações |
| 2 | Banco real + bugs da retirada + validação | Postgres via Docker, retirada à prova de entradas ruins, erros HTTP corretos |
| 3 | Testes + o que falta para demo (estoque mínimo, entrada, relatório por material) | Testes no service, roteiro de demo que roda do zero sem susto |
| 4 | Apresentação do piloto | Problema → demo ao vivo → proposta de teste de 2 semanas |

Escopo da demo vai ser enxuto de propósito: **cadastrar bobina → retirar para uma OS → ver o relatório do turno → ver alerta de estoque baixo**. O resto é bônus.

---

## 4. Tickets da Semana 1

Ordem sugerida: FLX-01 → FLX-02 → FLX-03. Não corrija bugs que encontrar nesta semana além do necessário para rodar; **anote**. Corrigir é Semana 2.

### FLX-01 · Fazer o projeto subir e mapear as camadas
**Tipo:** setup + leitura · **Estimativa:** 3 h

**Contexto:** ninguém explica um sistema que não consegue rodar. Antes de entender o código, você precisa vê-lo funcionando.

**Tarefas**
1. Confira sua versão do Java (`java -version`) e compare com o `pom.xml`. Se não bater, decida qual usar e justifique por escrito.
2. Crie o repositório Git local, faça o primeiro commit com o código como está ("estado inicial") e suba para o GitHub.
3. Rode o projeto. Se falhar, **leia o log antes de qualquer coisa**: encontre a linha `APPLICATION FAILED TO START` ou o último `Caused by` e escreva sua hipótese em uma frase.
4. Faça o projeto subir usando um banco em memória (provisório; o banco real é Semana 2).
5. Desenhe o mapa de camadas: cada classe, em qual camada está, e quem chama quem (setas). Pode ser no papel, foto no repo.

**Conceitos:** autoconfiguração do Spring Boot, `DataSource`, dependência de runtime no Maven, injeção de dependência.

**Perguntas-guia**
- O pom tem o driver do MySQL. Onde o Spring Boot procuraria *o endereço* desse banco, e esse arquivo existe no projeto?
- O PRD diz qual banco usar na fase inicial. O que está no pom é o banco ou só uma ferramenta para olhar o banco?
- Se amanhã surgir a regra "não pode excluir bobina que já teve retirada", em qual classe você a colocaria hoje? E onde ela *deveria* ficar? Por que essas respostas são diferentes?

**Pronto quando**
- [ ] Projeto sobe e `GET /api/bobinas` responde `[]`.
- [ ] Código no GitHub com pelo menos 2 commits com mensagens claras.
- [ ] `APRENDIZADOS.md` com: o erro de startup, sua hipótese, o que realmente era, e o mapa de camadas.

---

### FLX-02 · Do `@Entity` à tabela: entender o banco
**Tipo:** leitura + SQL · **Estimativa:** 3 h

**Contexto:** o plano diz "SQL de verdade antes de JPA". Aqui você descobre o que o Hibernate faz por baixo.

**Tarefas**
1. **Antes** de olhar o banco, escreva à mão o `CREATE TABLE` que você acha que o Hibernate gera para `Bobina` e para `MovimentacaoEstoque` (nomes de colunas, tipos, chave primária, chave estrangeira).
2. Ative o log de SQL do JPA e abra o console do banco. Compare o que você escreveu com o que foi gerado. Anote cada diferença.
3. Desenhe o diagrama das duas tabelas com a cardinalidade da relação.
4. Cadastre 3 bobinas e faça 4 retiradas (pode ser via API). Depois escreva, **direto no console SQL, sem JPA**:
   - total de metros retirados por OS;
   - total de metros retirados por tipo de material (vai precisar de JOIN);
   - bobinas com menos de 1000 m de saldo.
5. Reinicie a aplicação e veja o que aconteceu com os dados. Anote o porquê.

**Conceitos:** mapeamento objeto-relacional, `@Column`, `@ManyToOne`/`@JoinColumn`, chave estrangeira, `GROUP BY`, `JOIN`, `ddl-auto`, query derivada do Spring Data.

**Perguntas-guia**
- `largura` e `gramatura` não têm `@Column`. Que nome de coluna eles recebem, e quem decide isso?
- `findByDataRetiradaBetween` não tem implementação nenhuma. Como o Spring sabe qual SQL gerar? Que SQL você acha que sai?
- A relação está só de um lado (`MovimentacaoEstoque` conhece `Bobina`). Em qual tabela fica a FK, e por que nesse lado?
- Se os dados sumiram ao reiniciar, o que isso significa para uma demo na gráfica?

**Pronto quando**
- [ ] Diagrama do banco no repositório (imagem ou Markdown).
- [ ] As 3 queries SQL salvas em `docs/queries.sql` funcionando.
- [ ] `APRENDIZADOS.md` com as diferenças entre seu DDL e o gerado.

---

### FLX-03 · O caminho de uma retirada, do JSON ao banco e de volta
**Tipo:** leitura + depuração + exploração · **Estimativa:** 4 h

**Contexto:** "explique o fluxo de uma requisição" é das perguntas mais comuns em entrevista júnior de Spring. E é exatamente o que você vai mostrar na demo.

**Tarefas**
1. Pelo Postman (ou curl), faça: cadastrar bobina → `POST /api/movimentacoes/retirar` → `GET /api/relatorios/diario`.
2. Coloque breakpoints no controller, no service e antes de cada `save`. Rode em modo debug e siga uma retirada passo a passo, olhando os valores das variáveis.
3. Escreva o fluxo em até 10 passos, com suas palavras: do JSON chegando até o JSON saindo. Inclua quem transforma JSON em objeto e objeto em JSON.
4. **Exploração (sem corrigir):** tente pelo menos 5 requisições "erradas" que um operador real poderia mandar por engano ou pressa. Para cada uma registre: o que mandou, o que esperava, o que aconteceu, status HTTP. Pelo menos uma delas vai te surpreender.
5. Grave para você mesmo (ou escreva) uma explicação de 3 minutos do fluxo, como se fosse para o encarregado. Sem termos técnicos.

**Conceitos:** `DispatcherServlet`, desserialização com Jackson, `@RequestBody`, `ResponseEntity` e status HTTP, `@Transactional`, ciclo de vida de entidade JPA, debug com breakpoints.

**Perguntas-guia**
- `RequisicaoRetiradaMetros` tem campos públicos e não tem getters. Como o Jackson consegue preenchê-la? O que mudaria se os campos fossem privados?
- O service salva a bobina e depois a movimentação. Se o segundo `save` falhar, o que acontece com o saldo da bobina? Qual anotação responde isso?
- O controller transforma qualquer `RuntimeException` em 400. "Bobina não existe" e "saldo insuficiente" deveriam ter o mesmo status? Quais seriam os certos?
- O relatório considera "hoje" de 00:00 a 23:59. Como fica um turno das 22h às 6h?

**Pronto quando**
- [ ] Fluxo em 10 passos no `APRENDIZADOS.md`.
- [ ] Tabela de exploração com pelo menos 5 casos (vira o backlog da Semana 2).
- [ ] Você consegue explicar o fluxo sem abrir o código.

---

## 5. Exercício da semana (do zero, sem ajuda, ~45 min)

Em um projeto Java puro, **sem Spring**, crie uma classe `Bobina` com saldo em metros e um método `retirar(double metros)` que:
- lança exceção se o saldo não for suficiente;
- lança exceção para qualquer valor de retirada que não faça sentido;
- atualiza o saldo quando der certo.

Escreva 4 testes JUnit para ela. Não consulte o FlexoEstoque nem o Claude. Quando terminar, me mande o código e conte quanto tempo levou e onde travou.

---

## 6. Como me entregar cada ticket

Mande o link do commit (ou cole o código) + o trecho do `APRENDIZADOS.md`. Eu faço o code review, e depois peço que você explique o que fez e por quê.
