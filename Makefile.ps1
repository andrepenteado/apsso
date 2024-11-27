param(
    [Parameter()]
    [string]$exec
)

switch($exec) {
    "build-all" {
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
        cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        mvn -U clean package -DskipTests -f ./backend/pom.xml
        docker build -f .docker/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        docker build -f .docker/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO .
        docker build -f .docker/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO .
        docker build -f .docker/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO .
        docker build -f .docker/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO .
        docker build -f .docker/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/controle-equipe -t ghcr.io/andrepenteado/apsso/equipe-backend:$VERSAO .
        docker build -f .docker/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/controle-equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend:$VERSAO .
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/controle-backend
        docker push ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/controle-frontend
        docker push ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/portal-backend
        docker push ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/portal-frontend
        docker push ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/equipe-backend
        docker push ghcr.io/andrepenteado/apsso/equipe-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/equipe-frontend
        docker push ghcr.io/andrepenteado/apsso/equipe-frontend:$VERSAO
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
    }
    "build-login" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        mvn -U -f ./backend/pom.xml clean package --projects services,login
        docker build -f .docker/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker logout ghcr.io
    }
    "build-controle" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
        mvn -U -f ./backend/pom.xml clean package --projects services,controle -DskipTests
        docker build -f .docker/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO .
        docker build -f .docker/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO .
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/controle-backend
        docker push ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/controle-frontend
        docker push ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO
        cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker build -f .docker/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev .
        docker push ghcr.io/andrepenteado/apsso/controle-frontend:dev
        docker logout ghcr.io
    }
    "build-portal" {
        $VERSAO = mvn -f ./backend/pom.xml help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
        mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
        docker build -f .docker/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO .
        docker build -f .docker/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO .
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/portal-backend
        docker push ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/portal-frontend
        docker push ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO
        cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker build -f .docker/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev .
        docker push ghcr.io/andrepenteado/apsso/portal-frontend:dev
        docker logout ghcr.io
    }
    "build-equipe" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        cd ./frontend/equipe/ && ng build --configuration=production --output-path=dist/production && cd ../..
        mvn -U -f ./backend/pom.xml clean package --projects services,equipe -DskipTests
        docker build -f .docker/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/controle-equipe -t ghcr.io/andrepenteado/apsso/equipe-backend:$VERSAO .
        docker build -f .docker/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/controle-equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend:$VERSAO .
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/equipe-backend
        docker push ghcr.io/andrepenteado/apsso/equipe-backend:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/equipe-frontend
        docker push ghcr.io/andrepenteado/apsso/equipe-frontend:$VERSAO
        cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker build -f .docker/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev .
        docker push ghcr.io/andrepenteado/apsso/equipe-frontend:dev
        docker logout ghcr.io
    }
    "start" {
        docker compose -f .docker/composes/docker-compose.yml up -d
    }
    "stop" {
        docker compose -f .docker/composes/docker-compose.yml down
    }
    "log" {
        docker compose -f .docker/composes/docker-compose.yml logs -f
    }
    "update" {
        docker compose -f .ansible/files/docker-compose.yml down
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker image pull postgres:16
        docker image pull ghcr.io/andrepenteado/apsso/login
        docker image pull ghcr.io/andrepenteado/apsso/controle-backend
        docker image pull ghcr.io/andrepenteado/apsso/controle-frontend
        docker image pull ghcr.io/andrepenteado/apsso/controle-frontend:dev
        docker image pull ghcr.io/andrepenteado/apsso/portal-backend
        docker image pull ghcr.io/andrepenteado/apsso/portal-frontend
        docker image pull ghcr.io/andrepenteado/apsso/portal-frontend:dev
        docker logout ghcr.io
    }
    Default {
        "`nInforme o parâmetro: -exec <target>`n"
    }
}
