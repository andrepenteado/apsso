param(
    [Parameter()]
    [string]$exec
)

switch($exec) {
    "build-login" {
        $VERSAO = mvn help:evaluate `-Dexpression=project.version -q `-DforceStdout
        docker build -f .docker/Dockerfile -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        Get-Content $GITHUB_TOKEN | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker logout ghcr.io
    }
    "build-login-pipeline" {
        $VERSAO = mvn help:evaluate `-Dexpression=project.version -q `-DforceStdout
        mvn -U clean package --projects login
        docker build -f .docker/Dockerfile.pipeline -t ghcr.io/andrepenteado/apsso/login -t ghcr.io/andrepenteado/apsso/login:$VERSAO .
        Get-Content $GITHUB_TOKEN | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/login
        docker push ghcr.io/andrepenteado/apsso/login:$VERSAO
        docker logout ghcr.io
    }
    "build-controle" {
        $VERSAO = mvn help:evaluate `-Dexpression = project.version -q `-DforceStdout
        docker build -f .docker/Dockerfile.controle -t ghcr.io/andrepenteado/apsso/controle -t ghcr.io/andrepenteado/apsso/controle:$VERSAO .
        Get-Content $GITHUB_TOKEN | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/controle
        docker push ghcr.io/andrepenteado/apsso/controle:$VERSAO
        docker logout ghcr.io
    }
    "build-controle-pipeline" {
        $VERSAO = mvn help:evaluate `-Dexpression = project.version -q `-DforceStdout
        npm --prefix ./controle/src/main/angular run build --omit=dev -- "--base-href=/controle/"
        mvn -U clean package --projects services,controle -DskipTests
        docker build -f .docker/Dockerfile.controle.pipeline -t ghcr.io/andrepenteado/apsso/controle -t ghcr.io/andrepenteado/apsso/controle:$VERSAO .
        Get-Content $GITHUB_TOKEN | docker login ghcr.io --username andrepenteado --password-stdin
        docker push ghcr.io/andrepenteado/apsso/controle
        docker push ghcr.io/andrepenteado/apsso/controle:$VERSAO
        docker logout ghcr.io
    }
    "start" {
        docker compose -f .docker/docker-compose.yml up -d
    }
    "stop" {
        docker compose -f .docker/docker-compose.yml down
    }
    "log" {
        docker compose -f .docker/docker-compose.yml logs -f
    }
    "update" {
        docker compose -f .docker/docker-compose.yml down
        Get-Content $GITHUB_TOKEN | docker login ghcr.io --username andrepenteado --password-stdin
        docker image pull postgres:15.2
        docker image pull ghcr.io/andrepenteado/apsso/login
        docker image pull ghcr.io/andrepenteado/apsso/controle
        docker logout ghcr.io
        docker compose -f .docker/docker-compose.yml up -d
    }
    "start-backend-dev" {
        docker compose -f .docker/postgresql.yml up -d
        mvn -f controle/pom.xml clean spring-boot:run -Dspring-boot.run.profiles=dev
    }
    Default {
        "`nInforme o parâmetro: -exec <target>`n"
    }
}
