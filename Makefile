build-push:
	./mvnw clean package
	docker build -f src/main/docker/Dockerfile -t registry.gitlab.com/andrepenteado/ap-sso -t registry.gitlab.com/andrepenteado/ap-sso:`mvn help:evaluate -Dexpression=project.version -q -DforceStdout` .
	docker login registry.gitlab.com
	docker push registry.gitlab.com/andrepenteado/ap-sso
	docker push registry.gitlab.com/andrepenteado/ap-sso:`mvn  help:evaluate -Dexpression=project.version -q -DforceStdout`
	#docker logout registry.gitlab.com

run:
	docker login registry.gitlab.com
	docker pull registry.gitlab.com/andrepenteado/ap-sso
	#docker logout registry.gitlab.com
	docker-compose -f src/main/docker/docker-compose.yml up

run-cluster:
	docker login registry.gitlab.com
	docker pull registry.gitlab.com/andrepenteado/ap-sso
	#docker logout registry.gitlab.com
	docker swarm init
	docker network create --driver overlay --opt encrypt rede-cluster-interna
	docker network create --driver overlay --opt encrypt rede-cluster-externa
	docker stack deploy -c src/main/docker/docker-compose.yml apsso
	docker service logs -f apsso_apsso-webapp
	docker swarm leave --force

run-dev:
	./mvnw clean package
	docker-compose -f src/main/docker/docker-compose-dev.yml up

run-db:
	docker-compose -f src/main/docker/docker-compose.yml up apsso-dbserver
