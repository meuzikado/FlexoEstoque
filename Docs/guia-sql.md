# Guia rápido de SQL

Consulta rápida para o dia a dia. Funciona no console do H2 e, com poucas diferenças, no MySQL e no PostgreSQL.

Os exemplos usam **tabelas inventadas** de uma gráfica, e não as do FlexoEstoque, para você adaptar com as suas:

```
tb_cliente  (id, nome, cidade)
tb_servico  (id, cliente_id, tipo_papel, metros, valor, data_servico)
```

`cliente_id` é a **chave estrangeira**: guarda o `id` de um cliente (uma linha de `tb_cliente`).

---

## 1. Ler dados: `SELECT`

```sql
SELECT * FROM tb_servico;                          -- todas as colunas, todas as linhas
SELECT tipo_papel, metros FROM tb_servico;         -- só algumas colunas
SELECT DISTINCT tipo_papel FROM tb_servico;        -- sem repetir valores
SELECT * FROM tb_servico ORDER BY metros DESC;     -- ordenar (DESC = maior primeiro; ASC = menor primeiro)
SELECT * FROM tb_servico ORDER BY metros DESC LIMIT 5;   -- só as 5 primeiras linhas
```

Apelidos (deixam o resultado e as consultas mais legíveis):

```sql
SELECT metros AS metragem FROM tb_servico;
SELECT s.metros FROM tb_servico s;                 -- "s" é o apelido da tabela
```

---

## 2. Filtrar: `WHERE`

```sql
SELECT * FROM tb_servico WHERE tipo_papel = 'COUCHE';
SELECT * FROM tb_servico WHERE metros > 1000;
SELECT * FROM tb_servico WHERE metros < 1000;      -- menor que (1000 NÃO entra)
SELECT * FROM tb_servico WHERE metros <= 1000;     -- menor ou igual (1000 entra)
SELECT * FROM tb_servico WHERE tipo_papel <> 'BOPP';           -- diferente
SELECT * FROM tb_servico WHERE metros BETWEEN 500 AND 2000;    -- inclui 500 e 2000
SELECT * FROM tb_servico WHERE tipo_papel IN ('BOPP', 'COUCHE');
SELECT * FROM tb_servico WHERE tipo_papel LIKE 'CO%';          -- começa com "CO"
SELECT * FROM tb_servico WHERE valor IS NULL;                  -- vazio (use IS NULL, nunca = NULL)
SELECT * FROM tb_servico WHERE tipo_papel = 'BOPP' AND metros > 1000;
SELECT * FROM tb_servico WHERE tipo_papel = 'BOPP' OR tipo_papel = 'COUCHE';
```

Regras:
- **Texto** vai entre **aspas simples** (`'BOPP'`). Aspas duplas são para nomes de coluna.
- **Número** vai sem aspas.
- Com `AND` e `OR` juntos, use **parênteses** para deixar a ordem clara.

---

## 3. Contar e somar: funções de agregação

```sql
SELECT COUNT(*)      FROM tb_servico;     -- quantas linhas
SELECT SUM(metros)   FROM tb_servico;     -- soma
SELECT AVG(metros)   FROM tb_servico;     -- média
SELECT MIN(metros)   FROM tb_servico;     -- menor valor
SELECT MAX(metros)   FROM tb_servico;     -- maior valor
```

### Agrupar: `GROUP BY`
Responde "total **por** alguma coisa".

```sql
SELECT tipo_papel, SUM(metros) AS total_metros
FROM tb_servico
GROUP BY tipo_papel;
```

Regra de ouro: toda coluna que aparece no `SELECT` **sem** função (`SUM`, `COUNT`...) precisa estar no `GROUP BY`.

### Filtrar depois de agrupar: `HAVING`
`WHERE` filtra **linhas antes** de agrupar. `HAVING` filtra os **grupos depois**.

```sql
SELECT tipo_papel, SUM(metros) AS total_metros
FROM tb_servico
GROUP BY tipo_papel
HAVING SUM(metros) > 5000;
```

---

## 4. Juntar tabelas: `JOIN`

Use quando a informação que você quer está em **duas tabelas** ligadas por uma FK.

```sql
SELECT c.nome, s.tipo_papel, s.metros
FROM tb_servico s
JOIN tb_cliente c ON c.id = s.cliente_id;       -- o "ON" diz como as duas se ligam (FK = id)
```

Junta e agrupa:

```sql
SELECT c.nome, SUM(s.metros) AS total_metros
FROM tb_servico s
JOIN tb_cliente c ON c.id = s.cliente_id
GROUP BY c.nome;
```

Tipos de `JOIN`:

| Tipo | O que traz |
|---|---|
| `JOIN` (ou `INNER JOIN`) | só linhas que têm par nas **duas** tabelas |
| `LEFT JOIN` | todas da tabela da esquerda, mesmo sem par (as colunas da direita vêm vazias) |

Exemplo de `LEFT JOIN`: listar clientes mesmo que ainda não tenham nenhum serviço.

---

## 5. Inserir, alterar e apagar

```sql
INSERT INTO tb_cliente (nome, cidade) VALUES ('Gráfica Alfa', 'São Paulo');

UPDATE tb_servico SET metros = 1200 WHERE id = 3;

DELETE FROM tb_servico WHERE id = 3;
```

**Cuidado:** `UPDATE` e `DELETE` **sem `WHERE`** mudam ou apagam **todas** as linhas. Primeiro rode o mesmo `WHERE` em um `SELECT` para ver o que vai ser atingido.

No console do H2, o *Auto commit* fica ligado: o que você faz **vale na hora e não dá para desfazer**.

---

## 6. Estrutura: criar e alterar tabelas

```sql
CREATE TABLE tb_cliente (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome    VARCHAR(100) NOT NULL,
    cidade  VARCHAR(60)
);

CREATE TABLE tb_servico (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id    BIGINT NOT NULL,
    tipo_papel    VARCHAR(30),
    metros        DOUBLE,
    valor         DECIMAL(10,2),
    data_servico  DATE,
    FOREIGN KEY (cliente_id) REFERENCES tb_cliente(id)
);

ALTER TABLE tb_cliente ADD COLUMN telefone VARCHAR(20);
ALTER TABLE tb_cliente DROP COLUMN telefone;
DROP TABLE tb_servico;                      -- apaga a tabela inteira
```

Tipos mais comuns:

| Tipo | Para quê |
|---|---|
| `BIGINT` / `INT` | números inteiros (ids, quantidades) |
| `DOUBLE` | decimais com imprecisão (evite para dinheiro e peso exato) |
| `DECIMAL(10,2)` | decimais **exatos** (dinheiro, peso) |
| `VARCHAR(n)` | texto de até n caracteres |
| `DATE` / `TIMESTAMP` | data / data e hora |
| `BOOLEAN` | verdadeiro ou falso |

Restrições (constraints):

| Restrição | Significa |
|---|---|
| `PRIMARY KEY` | identifica cada linha de forma única |
| `FOREIGN KEY ... REFERENCES` | liga a uma linha de outra tabela |
| `NOT NULL` | obrigatório |
| `UNIQUE` | não pode repetir |

---

## 7. Funções úteis

```sql
SELECT UPPER(tipo_papel), LOWER(tipo_papel) FROM tb_servico;
SELECT ROUND(metros / 3, 2) FROM tb_servico;              -- arredonda (2 casas)
SELECT COALESCE(valor, 0) FROM tb_servico;                -- troca vazio por 0
SELECT CAST(data_servico AS DATE) FROM tb_servico;
SELECT * FROM tb_servico WHERE data_servico = CURRENT_DATE;   -- hoje
SELECT * FROM tb_servico WHERE data_servico >= DATE '2026-11-01';
```

---

## 8. Ordem em que você escreve

```
SELECT   colunas
FROM     tabela
JOIN     outra ON ...
WHERE    filtro de linhas
GROUP BY colunas de agrupamento
HAVING   filtro de grupos
ORDER BY ordenação
LIMIT    quantidade
```

Dica para pensar: o banco **lê** nesta ordem: `FROM` / `JOIN` → `WHERE` → `GROUP BY` → `HAVING` → `SELECT` → `ORDER BY` → `LIMIT`.

---

## 9. Específicos do console do H2

```sql
SHOW TABLES;                                  -- lista as tabelas
SHOW COLUMNS FROM tb_cliente;                 -- colunas de uma tabela
SELECT * FROM INFORMATION_SCHEMA.TABLES;      -- catálogo do banco
```

- O H2 mostra os nomes em **MAIÚSCULAS** (`TB_BOBINA`), mas `SELECT * FROM tb_bobina` funciona também.
- Para rodar só um trecho, selecione e use **Run Selected**.

---

## 10. Quando der erro

| Mensagem | Provável causa |
|---|---|
| `Table "X" not found` | nome da tabela errado, ou o banco reiniciou e os dados sumiram |
| `Column "X" not found` | nome da coluna errado, confira com `SHOW COLUMNS FROM ...` |
| `must be in the GROUP BY list` | coluna no `SELECT` fora do `GROUP BY` |
| `Syntax error ... expected` | faltou vírgula, aspas ou palavra-chave: olhe o ponto que a mensagem indica |
| `Referential integrity constraint violation` | quebrou uma FK (apagar algo que outra tabela ainda usa, ou inserir um id que não existe) |

---

## 11. Diferenças entre os bancos (para o futuro)

| Assunto | H2 | MySQL | PostgreSQL |
|---|---|---|---|
| Id automático | `AUTO_INCREMENT` ou `GENERATED ... AS IDENTITY` | `AUTO_INCREMENT` | `GENERATED ... AS IDENTITY` ou `SERIAL` |
| Limitar linhas | `LIMIT n` | `LIMIT n` | `LIMIT n` |
| Listar tabelas | `SHOW TABLES` | `SHOW TABLES` | `\dt` (no psql) |
| Nomes sem aspas | viram MAIÚSCULAS | mantém | viram minúsculas |

A parte principal (`SELECT`, `WHERE`, `JOIN`, `GROUP BY`) é igual nos três. O que muda são detalhes de tipos e de comandos administrativos.
