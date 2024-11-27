VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file ./backend/pom.xml)

build-all:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
	cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package -DskipTests
	docker build -f .docker/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/equipe-backend -t ghcr.io/andrepenteado/apsso/equipe-backend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend -t ghcr.io/andrepenteado/apsso/equipe-frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/login
	docker push ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle-backend
	docker push ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle-frontend
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal-backend
	docker push ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal-frontend
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/equipe-backend
	docker push ghcr.io/andrepenteado/apsso/equipe-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/equipe-frontend
	docker push ghcr.io/andrepenteado/apsso/equipe-frontend:$(VERSAO_APP)
	cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker build -f .docker/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
	cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker build -f .docker/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
	cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker build -f .docker/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/equipe-frontend:dev
	docker logout ghcr.io

build-login:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	mvn -U -f ./backend/pom.xml clean package --projects services,login
	docker build -f .docker/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/login
	docker push ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP)
	docker logout ghcr.io

build-controle:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package --projects services,controle -DskipTests
	docker build -f .docker/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/controle-backend
	docker push ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle-frontend
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP)
	cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker build -f .docker/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
	docker logout ghcr.io

build-equipe:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/equipe/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package --projects services,equipe -DskipTests
	docker build -f .docker/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/equipe-backend -t ghcr.io/andrepenteado/apsso/equipe-backend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend -t ghcr.io/andrepenteado/apsso/equipe-frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/equipe-backend
	docker push ghcr.io/andrepenteado/apsso/equipe-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/equipe-frontend
	docker push ghcr.io/andrepenteado/apsso/equipe-frontend:$(VERSAO_APP)
	cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker build -f .docker/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/equipe-frontend:dev
	docker logout ghcr.io

build-portal:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
	docker build -f .docker/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP) .
	docker build -f .docker/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/portal-backend
	docker push ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal-frontend
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP)
	cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker build -f .docker/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
	docker logout ghcr.io

start:
	docker compose -f .docker/composes/docker-compose.yml up -d

stop:
	docker compose -f .docker/composes/docker-compose.yml down

log:
	docker compose -f .docker/composes/docker-compose.yml logs -f

update:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	$(MAKE) stop
	docker image pull postgres:16
	docker image pull ghcr.io/andrepenteado/apsso/login
	docker image pull ghcr.io/andrepenteado/apsso/controle-backend
	docker image pull ghcr.io/andrepenteado/apsso/controle-frontend
	docker image pull ghcr.io/andrepenteado/apsso/controle-frontend:dev
	docker image pull ghcr.io/andrepenteado/apsso/portal-backend
	docker image pull ghcr.io/andrepenteado/apsso/portal-frontend
	docker image pull ghcr.io/andrepenteado/apsso/portal-frontend:dev
	docker image pull ghcr.io/andrepenteado/apsso/equipe-backend
	docker image pull ghcr.io/andrepenteado/apsso/equipe-frontend
	docker image pull ghcr.io/andrepenteado/apsso/equipe-frontend:dev
	docker logout ghcr.io
	$(MAKE) start

# mvn clean spring-boot:run -D spring-boot.run.profiles=dev -D spring-boot.run.jvmArguments="-DAUTHORIZATION_SERVER_URL=http://login:30000 -Dserver.port=30002"
