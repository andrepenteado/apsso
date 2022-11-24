VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file pom.xml)

build-image:
	docker build -f Dockerfile -t ghcr.io/andrepenteado/ap-sso/apsso -t ghcr.io/andrepenteado/ap-sso/apsso:$(VERSAO_APP) .
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker push ghcr.io/andrepenteado/ap-sso/apsso
	docker push ghcr.io/andrepenteado/ap-sso/apsso:$(VERSAO_APP)
	docker logout ghcr.io

start:
	docker compose -f src/main/docker/docker-compose.yml up -d

stop:
	docker compose -f src/main/docker/docker-compose.yml down
