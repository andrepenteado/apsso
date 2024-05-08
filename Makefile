VERSAO_APP := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout --file ./backend/pom.xml)

build-all:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	npm --prefix ./frontend/controle run build --omit=dev -- "-c=production"
	npm --prefix ./frontend/portal run build --omit=dev -- "-c=production"
	mvn -U -f ./backend/pom.xml clean package -DskipTests
	docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.backend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.backend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP) .
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
	 npm run ng build -- --configuration=development --prefix .\frontend\controle\
	docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
	 npm run ng build -- --configuration=development --prefix .\frontend\portal\
	docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
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
	docker build -f .docker/Dockerfile.backend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/controle-backend
	docker push ghcr.io/andrepenteado/apsso/controle-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/controle-frontend
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:$(VERSAO_APP)
	 npm run ng build -- --configuration=development --prefix .\frontend\controle\
	docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
	docker logout ghcr.io

build-portal:
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	npm --prefix ./frontend/portal run build --omit=dev -- "-c=production"
	mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
	docker build -f .docker/Dockerfile.backend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP) .
	docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP) .
	docker push ghcr.io/andrepenteado/apsso/portal-backend
	docker push ghcr.io/andrepenteado/apsso/portal-backend:$(VERSAO_APP)
	docker push ghcr.io/andrepenteado/apsso/portal-frontend
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:$(VERSAO_APP)
	 npm run ng build -- --configuration=development --prefix .\frontend\portal\
	docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
	docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
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
	docker image pull ghcr.io/andrepenteado/apsso/controle-backend
	docker image pull ghcr.io/andrepenteado/apsso/controle-frontend
	docker image pull ghcr.io/andrepenteado/apsso/controle-frontend:dev
	docker image pull ghcr.io/andrepenteado/apsso/portal-backend
	docker image pull ghcr.io/andrepenteado/apsso/portal-frontend
	docker image pull ghcr.io/andrepenteado/apsso/portal-frontend:dev
	docker logout ghcr.io
	$(MAKE) start

# mvn clean spring-boot:run -D spring-boot.run.profiles=dev -D spring-boot.run.jvmArguments="-DAUTHORIZATION_SERVER_URL=http://login:30000 -Dserver.port=30002"
