param(
    [Parameter()]
    [string]$exec
)

switch($exec) {
    "build-all" {
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
        cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
        cd ./frontend/equipe/ && ng build --configuration=production --output-path=dist/production && cd ../..
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        mvn -U clean package -DskipTests -f ./backend/pom.xml
        docker buildx build -f .deploy/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/equipe-backend -t ghcr.io/andrepenteado/apsso/equipe-backend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend -t ghcr.io/andrepenteado/apsso/equipe-frontend:$VERSAO --push .
        cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker buildx build -f .deploy/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev --push .
        cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker buildx build -f .deploy/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev --push .
        cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker buildx build -f .deploy/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev --push .
        docker logout ghcr.io
    }
    "build-login" {
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        mvn -U -f ./backend/pom.xml clean package --projects services,login
        docker buildx build -f .deploy/dockerfiles/login -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO --push .
        docker logout ghcr.io
    }
    "build-controle" {
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        cd ./frontend/controle/ && ng build --configuration=production --output-path=dist/production && cd ../..
        mvn -U -f ./backend/pom.xml clean package --projects services,controle -DskipTests
        docker buildx build -f .deploy/dockerfiles/backend.controle -t ghcr.io/andrepenteado/apsso/controle-backend -t ghcr.io/andrepenteado/apsso/controle-backend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/frontend.controle -t ghcr.io/andrepenteado/apsso/controle-frontend -t ghcr.io/andrepenteado/apsso/controle-frontend:$VERSAO --push .
        cd ./frontend/controle/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker buildx build -f .deploy/dockerfiles/frontend.controle --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/controle-frontend:dev --push .
        docker logout ghcr.io
    }
    "build-portal" {
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        $VERSAO = mvn -f ./backend/pom.xml help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        cd ./frontend/portal/ && ng build --configuration=production --output-path=dist/production && cd ../..
        mvn -U -f ./backend/pom.xml clean package --projects services,portal -DskipTests
        docker buildx build -f .deploy/dockerfiles/backend.portal -t ghcr.io/andrepenteado/apsso/portal-backend -t ghcr.io/andrepenteado/apsso/portal-backend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/frontend.portal -t ghcr.io/andrepenteado/apsso/portal-frontend -t ghcr.io/andrepenteado/apsso/portal-frontend:$VERSAO --push .
        cd ./frontend/portal/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker buildx build -f .deploy/dockerfiles/frontend.portal --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/portal-frontend:dev --push .
        docker logout ghcr.io
    }
    "build-equipe" {
        Get-Content 'C:\Users\andrepenteado\ownCloud\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout' '-f' './backend/pom.xml'
        cd ./frontend/equipe/ && ng build --configuration=production --output-path=dist/production && cd ../..
        mvn -U -f ./backend/pom.xml clean package --projects services,equipe -DskipTests
        docker buildx build -f .deploy/dockerfiles/backend.equipe -t ghcr.io/andrepenteado/apsso/controle-equipe -t ghcr.io/andrepenteado/apsso/equipe-backend:$VERSAO --push .
        docker buildx build -f .deploy/dockerfiles/frontend.equipe -t ghcr.io/andrepenteado/apsso/controle-equipe -t ghcr.io/andrepenteado/apsso/equipe-frontend:$VERSAO --push .
        cd ./frontend/equipe/ && ng build --configuration=localhost --output-path=dist/localhost && cd ../..
        docker buildx build -f .deploy/dockerfiles/frontend.equipe --build-arg AMBIENTE=localhost -t ghcr.io/andrepenteado/apsso/equipe-frontend:dev --push .
        docker logout ghcr.io
    }
    "start" {
        docker compose -f .deploy/composes/docker-compose.yml up -d
    }
    "stop" {
        docker compose -f .deploy/composes/docker-compose.yml down
    }
    "log" {
        docker compose -f .deploy/composes/docker-compose.yml logs -f
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
        docker image pull ghcr.io/andrepenteado/apsso/equipe-backend
        docker image pull ghcr.io/andrepenteado/apsso/equipe-frontend
        docker image pull ghcr.io/andrepenteado/apsso/equipe-frontend:dev
        docker logout ghcr.io
    }
    Default {
        "`nInforme o parâmetro: -exec <target>`n"
    }
}
