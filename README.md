![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![image](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![image](https://img.shields.io/badge/Ansible-000000?style=for-the-badge&logo=ansible&logoColor=white)
![image](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

# Portal de Sistemas
Micro-serviço de um portal de single sign on para cadastro e autenticação
de sistemas e usuários

## Módulos
O projeto é dividido entre os seguintes módulos do _Maven_:

* **Login:** Responsável por autenticar usuários e sistemas. Implementa um 
_AuthorizationServer_ usando bibliotecas do _Spring Boot_.

* **Controle:** Construído usando como API de backend o _Spring Boot_ e
no frontend _Angular_. Responsável por fazer os cadastros de usuários e
sistemas.

* **Portal:** Aplicação web em _Angular_ responsável em navegar pelos
sistemas existentes.

* **Services:** Biblioteca de serviços compartilhados entre os módulos

* **PostgreSQL:** Os dados são armazenados em um banco _PostgreSQL_.

## Execução
O sistema é executado a partir container, inclusive seu banco de dados.
Por isso é necessário ter uma instalação de docker no servidor.

### Docker Compose
A maneira mais simples é utilizando o comando `docker compose`. 
A partir do diretório raíz do projeto, basta executar:

```shell
docker compose -f .docker/docker-compose.yml up -d
```
### Deploy com Ansible
Em um ambiente de produção, é possível automatizar o processo de deploy com _Ansible_

#### Docker Swarm
Em um servidor com `docker swarm`, além dos componentes do projeto,
também é feito deploy do proxy reverso _Traefik_ para acesso às APIs.

A partir do diretório raíz, acesse a pasta `.ansible/` e configure a pasta
de instalação editando o arquivo `docker-swarm.yml`. Feito isso, basta executar:

```shell
ansible-playbook docker-swarm.yml
```

#### Kubernetes
Em uma máquina onde é possível acessar um servidor com `kubernetes`, basta executar: 

```shell
ansible-playbook kubernetes.yml
```
