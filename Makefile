VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file pom.xml)

build-login:
	docker build -f .docker/Dockerfile -t ghcr.io/andrepenteado/apsso/apsso -t ghcr.io/andrepenteado/apsso/apsso:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/apsso
	docker push ghcr.io/andrepenteado/apsso/apsso:$(VERSAO_APP)
	docker logout ghcr.io

build-login-pipeline:
	mvn -U clean package --projects login
	docker build -f .docker/Dockerfile.pipeline -t ghcr.io/andrepenteado/apsso/apsso -t ghcr.io/andrepenteado/apsso/apsso:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/apsso
	docker push ghcr.io/andrepenteado/apsso/apsso:$(VERSAO_APP)
	docker logout ghcr.io

build-backend:
	docker build -f .docker/Dockerfile.backend -t ghcr.io/andrepenteado/apsso/apsso-backend -t ghcr.io/andrepenteado/apsso/apsso-backend:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/apsso-backend
	docker push ghcr.io/andrepenteado/apsso/apsso-backend:$(VERSAO_APP)
	docker logout ghcr.io

build-backend-pipeline:
	npm --prefix ./frontend run build --omit=dev
	mvn -U clean package --projects backend -DskipTests
	docker build -f .docker/Dockerfile.backend.pipeline -t ghcr.io/andrepenteado/apsso/apsso-backend -t ghcr.io/andrepenteado/apsso/apsso-backend:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/apsso-backend
	docker push ghcr.io/andrepenteado/apsso/apsso-backend:$(VERSAO_APP)
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
	docker image pull ghcr.io/andrepenteado/apsso/apsso
	docker image pull ghcr.io/andrepenteado/apsso/apsso-backend
	docker logout ghcr.io
	$(MAKE) start

start-backend-dev:
	docker compose -f .docker/postgresql.yml up -d
	mvn -f backend/pom.xml clean spring-boot:run -Dspring-boot.run.profiles=dev