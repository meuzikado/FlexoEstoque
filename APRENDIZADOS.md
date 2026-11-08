# APRENDIZADOS: FlexoEstoque

Diário de estudo. Uma entrada por dia de estudo, a mais recente no topo.
Regra: escreva com minhas palavras. Se não consegue explicar, ainda não aprendeu.

---

## Índice de conceitos
<!-- Atualize sempre que aprender um conceito novo. Vira sua cola para entrevista. -->

Conceito |	Explicação em 1 frase (minhas palavras) |	Onde aparece no projeto	| Data
@transactional | Se uma transação da erro é feito Rollback(desfaz a transação), caso não tenha erro temos o commit (envio) a transação completa sem dados quebrados - BobinaService.java - 03/11/26

@RequestBody Pega o JSON enviado pelo cliente e converte dentro da classe JAVA. No caso que usamos foi a Entidade BOBINAS. - Aparece em BOBINACONTROLLER - 06/11/26

## 08/11/26 · FLX-02·

### O que eu fiz hoje
Hoje fizemos consultas dentro do banco. Total de metros retirados por tipo de material, quantidade de retiradas por material e media de metros por material. Consultas especificas para deixar o aprendizado sólido.

### O que eu aprendi
Hoje ligamos duas tabelas pela chave estrangeira usando JOIN, agrupamento de dados usando GROUP BY, SUM para somar valores, COUNT para contar linhas e AVG para media de retirada. Usamo o WHERE para filtar as bobinas com menos de 1000M. Como fizemos o filtro com menos de 1000 as bobinas de 1000 não entrariam no calculo, caso quisessemos usar seria <= para também atribuir ao calculo

o CREATE TABLE cria somente a tabela e os campos vazios para receber o insert. O hibernate cria a tabela com os campos e recebe cada requisição do json fazendo inserções com os dados.

o proprio hibernate decidiu os tipos de dados da aplicação. Largura que era double virou float(53) not null, o numero do lote era string e virou varchar(255) com até 255 carecteres

### Onde travei

- **Problema: Criar consultas no banco
- **Erro / mensagem:não tivemos
- **Meu chute:...

### Como resolvi

Usei o guia de SQL para criar os comandos certos para as consultas.

### Perguntas em aberto
Metragem_linear e peso_atual não tem not null, então no momento estão aceitando metragem vazia o que não pode acontecer e o numero do lote não é unique então as pessoas conseguem usar o mesmo lote 2 vezes.

### Próximo passo
iniciar o FLX-03.

<!-- ===== FIM DO MODELO ===== -->
---
## 07/11/26 · FLX-01 / FLX-02·

### O que eu fiz hoje
Hoje criamos o application.properties com endereço, usuario, senha. Configuramos de fato o banco e agora o spring usa as configurações do application.properties.

Cadastrei 3 bobinas e fiz 4 retiradas ligadas a OS's diferentes.

### O que eu aprendi
O hibernate faz a criação do dados dentro do banco, criação de tabelas, inserção de bobinas, criação das chaves estrangeiras. como ja deixamos o JPA todo especificado no projeto com as anotações (@) o hibernate já sabia o que usar e fazer.

Fiz mais de uma retirada na BOBINA_ID (Chave estrangeira que liga a entidade bobina) 1 e deu tudo certo, A função de atualizar metragem linear da bobina está funcionando corretamente e a chave estrangeira está funcionando perfeitamente.

A quantidade de bobinas utilizadas continuou sendo 1 quando fiz mais de uma retirada em uma bobina, se o relatorio final do dia for fazer a soma de quantas bobinas foram utilizadas ele vai dizer que foi 2 ao invés de uma, anotado para corrigir na semana 2.

### Onde travei

- **Problema:Conectar ao banco
- **Erro / mensagem: não lembro
- **Meu chute: ...

### Como resolvi
Vi que o nome do banco estava diferente do que coloquei no application.properties, fiz a troca e depois conectou normal.
### Perguntas em aberto
o peso_atual na tabela tb_bobina está como double o que está ocasionando num numero muito grande quando é feito o calculo depois da retirada de uma bobina.

hoje quando fazemos uma retirada parcial numa bobina conta como uma retirada completa e isso da erro. ex: uma bobina de 2000m é retirada 400m sobre 1600m mas para o sistema foi utilizada uma bobina completa. quantidadeBobinasUtilizadas conta a bobina toda e isso não está certo.
-

### Próximo passo
Fazer as consultas 2 (total de metros por tipo de material, com JOIN) e 3 (bobinas com menos de 1000 m) e salvar em docs/queries.sql.

<!-- ===== FIM DO MODELO ===== -->
## 06/11/26 · FLX-01 ·

### O que eu fiz hoje
Hoje fizemos a primeira Requisição HTTP: 
Usamos o Verbo POST para criar a primeira bobina e o Verbo GET para acessar a bobina dentro do banco.

### O que eu aprendi
@RequestBody Pega o JSON enviado pelo cliente e converte em um objeto da classe JAVA. No caso que usamos foi a Entidade BOBINAS. ex: O vendedor quando recebe o pedido do cliente e transforma aquele pedido em uma OS.

A Diferença entre os STATUS HTTP 200 que é quando temos sucesso numa operação e o STATUS 201 created que é quando criamos algo.

JSON x CAMPOS JAVA. O nome do java é o que usamos para criar as bobinas no postman as anotações são as colunas que vão aparecer no banco de dados.

### Onde travei
<!-- O problema, o erro (cole a linha do "Caused by" se houver) e o meu primeiro chute da causa. -->
- **Problema: quando cadastrei uma bobina deu certo mais quando reiniciei o servidor as bobinas sumiram
- **Erro / mensagem: []
- **Meu chute: O json está enviando resposta vazia apos o banco ser desligado, como o banco é em memoria quando é desligado perdemos tudo que foi feito.

### Como resolvi
a solução para esse problema por hora foi descobrir a causa e o motivo dos dados sumirem. 

### Perguntas em aberto
Porque quando criamos uma bobina usando o Verbo post o Status recebido foi 200 OK ao inves de 201 created?
-

### Próximo passo
Para fechar o FLX-01 (cerca de 30 minutos)

Desenhar o mapa de camadas no papel (as 4 linhas que temos) e fotografar.
Ajustar "Como resolvi" de hoje: escrever a causa real (H2 por autoconfiguração) e as duas provas (o log e o dependency:tree).
Responder por escrito, em 2 ou 3 frases cada, as perguntas-guia que sobraram: a FK e a regra de excluir bobina (hoje × deveria).
Pendências para não esquecer

Pergunta em aberto: por que o POST devolveu 200 e não 201? (já está anotada)
Pergunta em aberto: o escopo compile do H2 é bom ou ruim?
Observação sem correção: BobinaController vai direto ao Repository, e o POST não bloqueia bobina duplicada.
Depois, o FLX-02 (a partir daqui, é outro ticket)

Antes de olhar o banco, escrever à mão o CREATE TABLE que você acha que o Hibernate gera para Bobina. Só isso para começar.


<!-- ===== FIM DO MODELO ===== -->

## 04/11/26 · Ticket: FLX-01 ·

### O que eu fiz hoje
Criei a pasta Resources para definir o application.properties com as configurações do meu projeto.
### O que eu aprendi
Classpath - é a lista de classes e arquivos. o java procura essas classes e arquivos, o spring entende como foi montado e define o que fazer. Ex: Basicamente é a preparação para rodar a maquina, separamos tudo que vai ser preciso para na hora de rodar montar e não faltar nada para o serviço. o spring é o operador da maquina (que é o Java) que olha o que há nela e o que alterar.

Autoconfiguração - se o projeto foi montado com um ficha de prescrição tecnica o application.properties o spring vai seguir ela para configuração do projeto. se não temos o spring vai usar a configuração padrão dele. ex:O operado ver a bancada montada com os cliches, tinta, mas confere a ficha que define se o serviço vai ser CMYK ou Pantone pode estar marcando 4 cores mas so saberemos se vai ser CMYK ou pantone pela Ficha tecnica. sem sem ficha tecnica usariamos CMYK por ser o padrão dos serviços.

DataSource - Ele guarda quais as URLS, Usuarios e senhas definidas pelo projeto na pasta resources dentro do application.properties. Quando o HIBERNATE precisa entrar em contato com o banco para gravar uma informação ou ler alguma informação o Hibernate pede uma conexão ao datasource. EX: é como o cadastro da gráfica no fornecedor de tinta. No cadastro estão o endereço do fornecedor (URL), o meu login e senha de cliente (usuário e senha) e o canal de pedido (o contato que me atende). Quando o impressor precisa de tinta (o Hibernate precisando gravar ou ler dados), ele não procura o fornecedor do zero: usa o cadastro, e o fornecedor (o banco) atende. O que vai no pedido (cor, quantidade de KG) é o dado gravado ou lido. O DataSource é o cadastro, não o pedido e não a tinta.

hikaripool - gerenciamento de conexões pre-estabelecidas com o banco. basicamente ele mantem as portas abertas para não termos que ficar abrindo varias portas de conexões com o banco. Ex: No caso da grafica os clichês do proximo serviço já colados e preparados para produção. Assim diminuindo o tempo de troca e aumentando a eficiencia.

convention over configuration - se não a nada configurado, o spring faz uma configuração por convenção (gerencia padrôes pre-definidos pelo spring). EX: um cliente tem um serviço que roda num papel padrão COUCHE, se não for especificado que o papel foi alterado para BOPP vamos seguir com o padrão definido.

dependência transitiva - é a dependencia da dependencia. é a dependencia filha. ex: quando compramos um porta-cliche ele vem com os rolamentos.

application.properties: Configurações da aplicação. ex:A ficha tecnica montada pelo impressor na hora de rodar o serviço pós aprovação do cliente.

h2 em produção é risco pq ao utilizar não temos noção quando erramos ou acertamos pois ele não apresenta erro. imagine fazer o trabalho de um dia inteiro, imaginando que está fazendo o certo e derrepente ter uma queda de energia e quando reanimar o banco nada do que você está mais ali? quando usamos banco real teriamos um erro que impediria de subir a applicação.

### Onde travei
<!-- O problema, o erro (cole a linha do "Caused by" se houver) e o meu primeiro chute da causa. -->
- **Problema: Banco h2 compilando
- **Erro / mensagem:Toda vez que iniciamos o projeto e vemos o json temos resposta vazia.
- **Meu chute:o que acontece é que o banco em memoria não apresenta erro de produção, nem salva nada depois de derrubar o projeto.

### Como resolvi
não fiz.
-

### Perguntas em aberto
nada por hora.
-

### Próximo passo
POST /api/bobinas, reiniciar, GET e anotar o que aconteceu.
Mapa de camadas.
Pergunta em aberto: escopo compile do H2.

<!-- ===== FIM DO MODELO ===== -->

## 03/11/26 · Ticket: ·

### O que eu fiz hoje

### O que eu aprendi
@transactional - Basicamente faz com que uma transação só seja concluida se nenhuma der erro, caso tenha erro durante a transação é feito rollback desfazendo tudo que foi feito. Ex: Iniciar uma maquina de impressão, se eu colo o clichê 1, colo o 2, mas o 3 está errado tudo que ja foi feito é desfeito e nenhuma transação é concluída, só é commitado se todas opereções em ordem 1,2,3 etc estiverem todas sem erro.

### Onde travei
<!-- O problema, o erro (cole a linha do "Caused by" se houver) e o meu primeiro chute da causa. -->
- **Problema:**
- **Erro / mensagem:**
- **Meu chute:**

### Como resolvi
<!-- O que realmente era e o que eu fiz. Se ainda não resolvi, escreva "em aberto". -->
-

### Perguntas em aberto
<!-- Dúvidas para levar ao mentor na próxima sessão. -->
-

### Próximo passo
-

<!-- ===== FIM DO MODELO ===== -->
