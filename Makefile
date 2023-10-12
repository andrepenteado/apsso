VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file pom.xml)

build-login:
	docker build -f .docker/Dockerfile -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/login
	docker push ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP)
	docker logout ghcr.io

build-login-pipeline:
	mvn -U clean package --projects services,login
	docker build -f .docker/Dockerfile.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/login
	docker push ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP)
	docker logout ghcr.io

build-controle:
	docker build -f .docker/Dockerfile.controle -t ghcr.io/andrepenteado/apsso/controle -t ghcr.io/andrepenteado/apsso/controle:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/controle
	docker push ghcr.io/andrepenteado/apsso/controle:$(VERSAO_APP)
	docker logout ghcr.io

build-controle-pipeline:
	npm --prefix ./controle/src/main/angular run build --omit=dev -- "--base-href=/controle/" "-c=production"
	mvn -U clean package --projects services,controle -DskipTests
	docker build -f .docker/Dockerfile.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle -t ghcr.io/andrepenteado/apsso/controle:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/controle
	docker push ghcr.io/andrepenteado/apsso/controle:$(VERSAO_APP)
	docker logout ghcr.io

build-portal:
	docker build -f .docker/Dockerfile.portal -t ghcr.io/andrepenteado/apsso/portal -t ghcr.io/andrepenteado/apsso/portal:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/portal
	docker push ghcr.io/andrepenteado/apsso/portal:$(VERSAO_APP)
	docker logout ghcr.io

build-portal-pipeline:
	npm --prefix ./portal/src/main/angular run build --omit=dev -- "--base-href=/portal/" "-c=production"
	mvn -U clean package --projects services,portal -DskipTests
	docker build -f .docker/Dockerfile.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal -t ghcr.io/andrepenteado/apsso/portal:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/portal
	docker push ghcr.io/andrepenteado/apsso/portal:$(VERSAO_APP)
	docker logout ghcr.io

start:
	docker compose -f .docker/docker-compose.yml up -d

stop:
	docker compose -f .docker/docker-compose.yml down

log:
	docker compose -f .docker/docker-compose.yml logs -f

update:
	$(MAKE) stop
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker image pull postgres:15.2
	docker image pull ghcr.io/andrepenteado/apsso/login
	docker image pull ghcr.io/andrepenteado/apsso/controle
	docker logout ghcr.io
	$(MAKE) start

start-backend-dev:
	docker compose -f .docker/postgresql.yml up -d
	mvn -f controle/pom.xml clean spring-boot:run -Dspring-boot.run.profiles=dev
