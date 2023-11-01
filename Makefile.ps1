param(
    [Parameter()]
    [string]$exec
)

switch($exec) {
    "build-all" {
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        mvn -U clean package -DskipTests
        docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        docker build -f .docker/Dockerfile.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle -t ghcr.io/andrepenteado/apsso/controle:$VERSAO .
        docker build -f .docker/Dockerfile.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal -t ghcr.io/andrepenteado/apsso/portal:$VERSAO .
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/controle
        docker push ghcr.io/andrepenteado/apsso/controle:$VERSAO
        docker push ghcr.io/andrepenteado/apsso/portal
        docker push ghcr.io/andrepenteado/apsso/portal:$VERSAO
        docker logout ghcr.io
    }
    "build-login" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        mvn -U clean package --projects services,login
        docker build -f .docker/Dockerfile.login.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker logout ghcr.io
    }
    "build-controle" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        npm --prefix ./controle/src/main/angular run build --omit=dev -- "-c=production"
        mvn -U clean package --projects services,controle -DskipTests
        docker build -f .docker/Dockerfile.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle -t ghcr.io/andrepenteado/apsso/controle:$VERSAO .
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/controle
        docker push ghcr.io/andrepenteado/apsso/controle:$VERSAO
        docker logout ghcr.io
    }
    "build-portal" {
        $VERSAO = mvn help:evaluate '-Dexpression=project.version' '-q' '-DforceStdout'
        npm --prefix ./portal/src/main/angular run build --omit=dev -- "-c=production"
        mvn -U clean package --projects services,portal -DskipTests
        docker build -f .docker/Dockerfile.portal.pipeline -t ghcr.io/andrepenteado/apsso/portal -t ghcr.io/andrepenteado/apsso/portal:$VERSAO .
        Get-Content 'C:\Users\André Penteado\Documents\Particular\token-github.txt' | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/portal
        docker push ghcr.io/andrepenteado/apsso/portal:$VERSAO
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
        docker image pull ghcr.io/andrepenteado/apsso/controle
        docker image pull ghcr.io/andrepenteado/apsso/portal
        docker logout ghcr.io
        docker compose -f .ansible/files/docker-compose.yml up -d
    }
    "start-backend-dev" {
        docker compose -f .docker/postgresql.yml up -d
        mvn -f controle/pom.xml clean spring-boot:run -Dspring-boot.run.profiles=dev
    }
    Default {
        "`nInforme o parâmetro: -exec <target>`n"
    }
}
