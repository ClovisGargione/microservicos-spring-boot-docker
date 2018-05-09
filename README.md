# Microserviços com Spring Boot e Docker

Sistema desenvolvido em uma arquitetura de microserviços, em linguagem Java para ambiente em nuvem, utilizando tecnologia RESTFull para comunicação entre os módulos e o framework Spring para implementação.
O sistema foi desenvolvido utilizando a IDE Sprint Tool Suite, aplicando os recursos de cloud oferecidos pelo Spring. 

## Arquitetura

São utilizados três serviços oferecidos pelo Spring, para garantir a alta disponibilidade e centralizar o acesso dos serviços distribuídos dentro da arquitetura de microserviços.
•	Config Server – Responsável por distribuir configurações entre os nós do cluster, também é possível criar um webhook no repositório git para disparar um gatilho que irá atualizar os nós do cluster com as alterações commitadas no repositório;
•	Eureka Server – Responsável por registrar os serviços da aplicação;
•	Zuul Gateway – Faz o roteamento entre os serviços disponíveis;
Para disponibilizar as aplicações abaixo do Zuul Gateway basta definir as configurações no arquivo properties(ou yml) da aplicação. Para o acesso deve ser utilizada a url do ZuulGateway mais o caminho da aplicação que deseja acessar.
Ex: http://zuulgateway:8080/admin/dashboard
      http://zuulgateway:8080/secure/auth
No exemplo estamos acessando duas aplicações separadas pelo mesmo host. Também é possível subir réplicas das aplicações, todas ficarão abaixo do ZuulGateway e terão o seu roteamento gerenciado automaticamente. As aplicações estão “visíveis” por conta do servidor Eureka, onde as aplicações são registradas.

## Deploy

Ao fazer deploy deve ser seguida a sequência, iniciando pelo ConfigServer, EurekaServer e por último (antes de subir as suas aplicações ex: Admin...) o ZuulGateway. Após esta estrutura estiver no ar, podemos acessar o EurekaServer no browser e ver os serviços online em http://localhost:8761.

## Monitoramento da aplicação

Podemos monitorar a aplicação através de um painel disponibilizado pelo Spring, onde será exibido todas as aplicações do seu sistema (se estiverem configuradas para se registrar no painel de monitoramento), também podemos verificar a “saúde” da aplicação e outras informações como uso de memória ou conexões com banco de dados. Este painel de monitoramento estará disponível em http://localhost:8181, ou a porta que você definir.

## Hystrix

Desenvolvida pela netflix e já faz parte do ecosistema Spring, o Hystrix é uma biblioteca de tolerância a falhas e latência projetada para isolar pontos de acesso a sistemas remotos, serviços e bibliotecas de terceiros, interromper falhas em cascata e permitir a resiliência em sistemas distribuídos complexos onde a falha é inevitável.
Esta biblioteca nos oferece um dashboard onde podemos monitorar as requisições feitas no nosso sistema em tempo real. Para acessar este monitor é necessário subir uma aplicação com as configurações necessárias para realizar o monitoramento das requisições, este é o nosso HystrixServer. 
O nosso client é a aplicação onde temos nossos serviços REST. Para monitorar as requisições é necessário adicionar a anotação @HystrixCommand em cima do método que será monitorado.
Acesse o painel de monitoramento em http://localhost:8989/hystrix e informe a url onde o HystrixServer está rondando. Ex: http://turbine-hostname:port/turbine.stream.

## Docker

Também é possível rodar nossa aplicação em containers no Docker. Para maior entendimento sobre o Docker acesse https://imasters.com.br/desenvolvimento/software-livre/uma-analise-sobre-o-docker/?trace=1519021197, como este assunto é muito extenso não vamos entrar muito em detalhes.
Podemos configurar um Cluster no Docker com toda nossa arquitetura, com fácil escalabilidade e um monitor web para acompanhar nossa aplicação. No diretório raíz tem um arquivo com vários exemplos e comandos para utilizar o Docker, caso queria se aprofundar um pouco mais.

## Montagem de ambiente

Para colocar todo nosso sistema no ar em cluster, é necessário seguir alguns passos de configuração. Este tutorial é um exemplo de configuração em ambiente local.

## Docker swarm

Iremos utilizar os recursos do Docker swarm em nosso cluster, assim conseguiremos gerenciar toda a aplicação, iniciar ou parar os nossos serviços que estão rodando nos containers. Na arquitetura do swarm temos um nó chamado ‘manager’ responsável por gerenciar os outros nós de trabalho chamados de ‘worker’, mas isso não quer dizer que o manager não possa rodar algum módulo da nossa aplicação, isso quem decidirá será o próprio swarm.
Inicialmente precisamos criar as máquinas Docker que representam os nós do nosso cluster.

## Máquinas Docker 

Abra as instâncias do power shell que farão referência a cada nó do cluster, como queremos garantir a alta disponibilidade, então criaremos dois manager’s e três workers. 
Criando as máquinas Docker – Em cada instância do power shell execute o comando docker-machine create nome-maquina,  onde ‘nome-maquina’ deve ser definido o nome da máquina, exemplo: docker-machine create manager, docker-machine create manager1
Para verificar se as máquinas estão rodando, execute o comando docker-machine ls. Depois que as máquinas estiverem rodando em cada nó do cluster é necessário executar o comando docker-machine env nome-maquina e em seguida execute o comando que o power shell irá indicar. 

Exemplo: & "C:\Program Files\Docker\Docker\Resources\bin\docker-machine.exe" env manager | Invoke-Expression

Isso irá permitir executar os comandos na máquina Docker criada para cada nó.
Iniciando o swarm – No nosso nó principal, que será o manager deve ser executado o comando docker swarm init  --advertise-addr ip-da-maquina. Este comando iniciará nosso cluster, com este nó definido como manager e apresentará o comando para definir um worker no nosso cluster. Para adicionar mais um manager execute o comando docker swarm join-token manager, e em seguida execute o comando que será apresentado no nó que deseja definir como manager. Para definir os worker’s basta copiar e colar o comando seguindo o mesmo procedimento.

Antes de iniciar nossos serviços dentro dos nós do cluster, precisamos ter as imagens criadas no Docker para cada aplicação. Para criar as imagens é necessário criar o arquivo Dockerfile em cada aplicação. Existem exemplos no diretório citado acima. Dentro do diretório onde se encontra o arquivo Dockerfile e o jar da aplicação (pode ser criado com o  maven), execute o comando  docker build -t nome-imagem . (este último ponto faz parte do comando)
Verifique as imagens criadas no Docker com o comando docker images.

## Tag para imagens

Para o cluster, utilizando o swarm funcionar corretamente, precisamos definir “tag’s” que farão referência às nossas imagens. Mas por quê? Lembra que criamos algumas máquinas Docker para que o swarm distribua as aplicações no cluster?! Então, criamos todas as nossas imagens dentro da máquina definida como manager, sendo assim, elas ficam ‘visíveis’ apenas dentro dessa máquina. Tah, mas e aí? O que acontece? Quando o swarm tentar subir uma réplica ou uma aplicação em outra máquina que não tenha as imagens, irá ocorrer um erro porque a imagem não foi localizada e todas as aplicações acabarão subindo apenas no manager, a não ser que todas as máquinas possuam uma cópia das imagens. Mas não queremos duplicar imagens, por isso criaremos “tag’s” em um repositório local para que as nossas imagens fiquem visíveis em todos os nós do cluster.
Crie um serviço para registrar as imagens e tag’s com o comando docker service create --name registry --publish 5000:5000 registry:2. Pode copiar e colar sem medo, o Docker irá baixar a imagem e criar o serviço.
Execute o comando docker tag nome-imagem localhost:5000/nome-imagem para criar a tag, e então docker push localhost:5000/nome-imagem assim teremos a imagem referenciada no nosso repositório local. Ao criar os serviços sempre utilize a tag e não a imagem. Podemos verificar nossas tags no repositório acessando http://<ip-maquina>:5000/v2/_catalog.

## Network

Nós sabemos que os nossos microserviços precisam se comunicar entre si. Para isso iremos criar uma rede interna dentro do nosso cluster e ela será definida em nossos serviços para se comunicarem. Crie a rede interna com o comando Docker network create --driver overlay nome-rede. Verifique a rede criada com o comando docker network ls.

## Services

Agora iremos criar os nossos serviços no nosso cluster. Se tudo foi configurado corretamente até aqui não teremos problemas, pois o mais complicado já foi feito. Utilize o comando 
docker service create --replicas 1 --name nome-servico --reserve-memory=100mb --publish 8888:8888 --update-delay 10s --network nome-rede localhost:5000/nome-imagem 
Utilize a rede interna criada no Docker ao definir a network no serviço. Note que o último atributo do comando foi a tag da imagem que foi criada anteriormente. Podemos verificar os serviços que foram criados com o comando docker service ls. Também podemos verificar o status do nosso serviço ou em qual nó ele está executando com o comando docker service ps nome-servico.

## Portainer

Até agora executamos nossos comandos via linha, pelo power shell. O Portainer é um serviço que nos oferece um dashboard web, onde podemos ter uma visão de tudo o que está acontecendo com nosso cluster, verificar nossos serviços, imagens, subir ou derrubar réplicas entre outas opções úteis para gerenciar nossos containers. Com o comando docker service create --name portainer --publish 9000:9000 --constraint 'node.role == manager' --mount type=bind,src=//var/run/docker.sock,dst=/var/run/docker.sock portainer/portainer -H unix:///var/run/docker.sock, o serviço estará disponível e rodando no cluster, para verificar acesse http://<ip-maquina>:9000/#/init/admin.

Observação:  Esta arquitetura acaba utilizando muitos recursos e memória de uma máquina de desenvolvimento, diminuindo drasticamente a performance da sua máquina. Utilize esta arquitetura em um servidor onde consiga rodar a aplicação sem impacto no desenvolvimento. Para o desenvolvimento podemos iniciar apenas a aplicação que estamos trabalhando se ela não tiver nenhuma dependência, isso vale tanto utilizando o Docker quanto somente o Spring.

