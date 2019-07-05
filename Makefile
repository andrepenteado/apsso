build-push:
	./mvnw clean package
	docker build -f src/main/docker/Dockerfile -t registry.gitlab.com/andrepenteado/portal-sso-sistemas -t registry.gitlab.com/andrepenteado/portal-sso-sistemas:`mvn help:evaluate -Dexpression=project.version -q -DforceStdout` .
	docker login registry.gitlab.com
	docker push registry.gitlab.com/andrepenteado/portal-sso-sistemas
	docker push registry.gitlab.com/andrepenteado/portal-sso-sistemas:`mvn  help:evaluate -Dexpression=project.version -q -DforceStdout`
	docker logout registry.gitlab.com

run:
	docker login registry.gitlab.com
	docker pull registry.gitlab.com/andrepenteado/portal-sso-sistemas
	docker logout registry.gitlab.com
	docker-compose -f src/main/docker/docker-compose.yml up

run-dev:
	./mvnw clean package
	docker-compose -f src/main/docker/docker-compose-dev.yml up

run-db:
	docker-compose -f src/main/docker/docker-compose-db.yml up