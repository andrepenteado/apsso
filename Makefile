VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file pom.xml)

build-image:
	docker build -f .docker/Dockerfile -t ghcr.io/andrepenteado/apsso/apsso -t ghcr.io/andrepenteado/apsso/apsso:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/apsso/apsso
	docker push ghcr.io/andrepenteado/apsso/apsso:$(VERSAO_APP)
	docker logout ghcr.io

start:
	docker compose -f .docker/docker-compose.yml up -d

stop:
	docker compose -f .docker/docker-compose.yml down
