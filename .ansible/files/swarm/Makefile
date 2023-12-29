start:
	docker stack deploy -c docker-compose.yml apsso

stop:
	docker stack rm apsso

update:
	$(MAKE) stop
	echo $(GITHUB_TOKEN) | docker login ghcr.io --username andrepenteado --password-stdin
	docker image pull traefik
	docker image pull postgres:16
	docker image pull ghcr.io/andrepenteado/apsso/login
	docker image pull ghcr.io/andrepenteado/apsso/controle
	docker image pull ghcr.io/andrepenteado/apsso/portal
	docker logout ghcr.io
	$(MAKE) start
