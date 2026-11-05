# APRENDIZADOS: FlexoEstoque

Diário de estudo. Uma entrada por dia de estudo, a mais recente no topo.
Regra: escreva com minhas palavras. Se não consegue explicar, ainda não aprendeu.

---

## Índice de conceitos
<!-- Atualize sempre que aprender um conceito novo. Vira sua cola para entrevista. -->

Conceito |	Explicação em 1 frase (minhas palavras) |	Onde aparece no projeto	| Data
@transactional | Se uma transação da erro é feito Rollback(desfaz a transação), caso não tenha erro temos o commit (envio) a transação completa sem dados quebrados - BobinaService.java - 03/11/26


---

## data · Ticket: ·

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
