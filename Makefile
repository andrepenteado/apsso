VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file ./backend/pom.xml)

build-all:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
	cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
	cd ./frontend/equipe/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package -DskipTests
	docker buildx build -f .deploy/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/equipe-backend -t ghcr.io/andrepenteado/apsso/equipe-backend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend -t ghcr.io/andrepenteado/apsso/equipe-frontend:$(VERSAO_APP) --push .
	cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker buildx build -f .deploy/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev --push .
	cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker buildx build -f .deploy/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev --push .
	cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker buildx build -f .deploy/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev --push .
	docker logout ghcr.io

build-login:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	mvn -U -f ./backend/pom.xml clean package --projects services,login
	docker buildx build -f .deploy/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) --push .
	docker logout ghcr.io

build-controle:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package --projects services,controle -DskipTests
	docker buildx build -f .deploy/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP) --push .
	cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker buildx build -f .deploy/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev --push .
	docker logout ghcr.io

build-equipe:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/equipe/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package --projects services,equipe -DskipTests
	docker buildx build -f .deploy/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/equipe-backend -t ghcr.io/andrepenteado/apsso/equipe-backend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend -t ghcr.io/andrepenteado/apsso/equipe-frontend:$(VERSAO_APP) --push .
	cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker buildx build -f .deploy/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev --push .
	docker logout ghcr.io

build-portal:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
	mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
	docker buildx build -f .deploy/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP) --push .
	docker buildx build -f .deploy/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP) --push .
	cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
	docker buildx build -f .deploy/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev --push .
	docker logout ghcr.io

# mvn clean spring-boot:run -D spring-boot.run.profiles=dev -D spring-boot.run.jvmArguments="-DAUTHORIZATION_SERVER_URL=http://login:30000 -Dserver.port=30002"
