param(
    [Parameter()]
    [string]$exec
)

switch($exec) {
    "build-all" {
#        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
	    npm --prefix ./frontend/controle run build --omit=dev -- "-c=production"
	    npm --prefix ./frontend/portal run build --omit=dev -- "-c=production"
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        mvn -U clean package -DskipTests -f ./backend/pom.xml
        docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        docker build -f .docker/Dockerfile.backend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO .
        docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO .
        docker build -f .docker/Dockerfile.backend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO .
        docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO .
#        docker push ghcr.io/andrepenteado/apsso/login
#        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
#        docker push ghcr.io/andrepenteado/apsso/controle-backend
#        docker push ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO
#        docker push ghcr.io/andrepenteado/apsso/controle-frontend
#        docker push ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO
#        docker push ghcr.io/andrepenteado/apsso/portal-backend
#        docker push ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO
#        docker push ghcr.io/andrepenteado/apsso/portal-frontend
#        docker push ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO
        npm run ng build -- --configuration=development --prefix .\frontend\controle\
        docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
#        docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
        npm run ng build -- --configuration=development --prefix .\frontend\portal\
        docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
#        docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
        docker logout ghcr.io
    }
    "build-login" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        mvn -U -f ./backend/pom.xml clean package --projects services,login
        docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker logout ghcr.io
    }
    "build-controle" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        npm --prefix ./frontend/controle run build --omit=dev -- "-c=production"
        mvn -U -f ./backend/pom.xml clean package --projects services,controle -DskipTests
        docker build -f .docker/Dockerfile.backend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO .
        docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO .
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/controle-backend
        docker push ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/controle-frontend
        docker push ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO
        npm run ng build -- --configuration=development --prefix .\frontend\controle\
        docker build -f .docker/Dockerfile.frontend.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
        docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
        docker logout ghcr.io
    }
    "build-portal" {
        $VERSAO = mvn -f ./backend/pom.xml help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        npm --prefix ./frontend/portal run build --omit=dev -- "-c=production"
        mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
        docker build -f .docker/Dockerfile.backend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO .
        docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO .
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/portal-backend
        docker push ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/portal-frontend
        docker push ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO
        npm run ng build -- --configuration=development --prefix .\frontend\portal\
        docker build -f .docker/Dockerfile.frontend.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
        docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
        docker logout ghcr.io
    }
    "start" {
        docker compose -f .ansible/files/docker-compose.yml up -d
    }
    "stop" {
        docker compose -f .ansible/files/docker-compose.yml down
    }
    "log" {
        docker compose -f .ansible/files/docker-compose.yml logs -f
    }
    "update" {
        docker compose -f .ansible/files/docker-compose.yml down
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker image pull postgres:16
        docker image pull ghcr.io/andrepenteado/apsso/login
        docker image pull ghcr.io/andrepenteado/apsso/controle-backend
        docker image pull ghcr.io/andrepenteado/apsso/controle-frontend
        docker image pull ghcr.io/andrepenteado/apsso/controle-frontend:dev
        docker image pull ghcr.io/andrepenteado/apsso/portal-backend
        docker image pull ghcr.io/andrepenteado/apsso/portal-frontend
        docker image pull ghcr.io/andrepenteado/apsso/portal-frontend:dev
        docker logout ghcr.io
        docker compose -f .ansible/files/docker-compose.yml up -d
    }
    "start-backend-dev" {
        docker compose -f .docker/postgresql.yml up -d
        mvn -f ./backend/controle/pom.xml clean spring-boot:run -Dspring-boot.run.profiles=dev
    }
    Default {
        "`nInforme o parâmetro: -exec <target>`n"
    }
}
