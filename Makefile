VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file ./backend/pom.xml)

build-all:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	npm --prefix ./frontend/controle run build --omit=dev -- "-c=production"
	npm --prefix ./frontend/portal run build --omit=dev -- "-c=production"
	mvn -U -f ./backend/pom.xml clean package -DskipTests
	docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.backend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle/backend -t ghcr.io/andrepenteado/apsso/controle/backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle/frontend -t ghcr.io/andrepenteado/apsso/controle/frontend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.backend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal/backend -t ghcr.io/andrepenteado/apsso/portal/backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal/frontend -t ghcr.io/andrepenteado/apsso/portal/frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/login
	docker push ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle/backend
	docker push ghcr.io/andrepenteado/apsso/controle/backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle/frontend
	docker push ghcr.io/andrepenteado/apsso/controle/frontend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal/backend
	docker push ghcr.io/andrepenteado/apsso/portal/backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal/frontend
	docker push ghcr.io/andrepenteado/apsso/portal/frontend:$(VERSAO_APP)
	docker logout ghcr.io

build-login:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	mvn -U -f ./backend/pom.xml clean package --projects services,login
	docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/login
	docker push ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP)
	docker logout ghcr.io

build-controle:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	npm --prefix ./frontend/controle run build --omit=dev -- "-c=production"
	mvn -U -f ./backend/pom.xml clean package --projects services,controle -DskipTests
	docker build -f .docker/Dockerfile.backend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle/backend -t ghcr.io/andrepenteado/apsso/controle/backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle/frontend -t ghcr.io/andrepenteado/apsso/controle/frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/controle/backend
	docker push ghcr.io/andrepenteado/apsso/controle/backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle/frontend
	docker push ghcr.io/andrepenteado/apsso/controle/frontend:$(VERSAO_APP)
	docker logout ghcr.io

build-portal:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	npm --prefix ./frontend/portal run build --omit=dev -- "-c=production"
	mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
	docker build -f .docker/Dockerfile.backend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal/backend -t ghcr.io/andrepenteado/apsso/portal/backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal/frontend -t ghcr.io/andrepenteado/apsso/portal/frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/portal/backend
	docker push ghcr.io/andrepenteado/apsso/portal/backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal/frontend
	docker push ghcr.io/andrepenteado/apsso/portal/frontend:$(VERSAO_APP)
	docker logout ghcr.io

start:
	docker compose -f .ansible/files/docker-compose.yml up -d

stop:
	docker compose -f .ansible/files/docker-compose.yml down

log:
	docker compose -f .ansible/files/docker-compose.yml logs -f

update:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	$(MAKE) stop
	docker image pull postgres:16
	docker image pull ghcr.io/andrepenteado/apsso/login
	docker image pull ghcr.io/andrepenteado/apsso/controle/backend
	docker image pull ghcr.io/andrepenteado/apsso/controle/frontend
	docker image pull ghcr.io/andrepenteado/apsso/portal/backend
	docker image pull ghcr.io/andrepenteado/apsso/portal/frontend
	docker logout ghcr.io
	$(MAKE) start

start-backend-dev:
	docker compose -f .docker/postgresql.yml up -d
	mvn -f ./backend/controle/pom.xml clean spring-boot:run -Dspring-boot.run.profiles=dev
