# Fazer o deploy do login para depois fazer o deploy dos outros serviços.
# E antes de expor o serviço, verificar se o deploy foi concluído.

deploy-login:
	kubectl apply -f 00-namespace.yml         \
	              -f 01-secret.yml            \
				  -f 02-deployment-login.yml

expor-login:
	kubectl apply -f 03-ingress-login.yml

deploy:
	kubectl apply -f 04-deployment-backend.yml
	kubectl apply -f 04-deployment-frontend.yml

expor:
	kubectl apply -f 05-ingress.yml

delete:
	kubectl delete namespace apsso

log-portal:
	kubectl logs -n apsso service/apsso-portal-backend -f

log-controle:
	kubectl logs -n apsso service/apsso-controle-backend -f

log-equipe:
	kubectl logs -n apsso service/apsso-equipe-backend -f

log-login:
	kubectl logs -n apsso service/apsso-login -f

update-portal:
	kubectl rollout restart deployment apsso-portal-backend -n apsso
	kubectl rollout restart deployment apsso-portal-frontend -n apsso
	kubectl rollout status deployment apsso-portal-backend -n apsso
	kubectl rollout status deployment apsso-portal-frontend -n apsso
	kubectl rollout history deployment apsso-portal-backend -n apsso
	kubectl rollout history deployment apsso-portal-frontend -n apsso

update-controle:
	kubectl rollout restart deployment apsso-controle-backend -n apsso
	kubectl rollout restart deployment apsso-controle-frontend -n apsso
	kubectl rollout status deployment apsso-controle-backend -n apsso
	kubectl rollout status deployment apsso-controle-frontend -n apsso
	kubectl rollout history deployment apsso-controle-backend -n apsso
	kubectl rollout history deployment apsso-controle-frontend -n apsso

update-equipe:
	kubectl rollout restart deployment apsso-equipe-backend -n apsso
	kubectl rollout restart deployment apsso-equipe-frontend -n apsso
	kubectl rollout status deployment apsso-equipe-backend -n apsso
	kubectl rollout status deployment apsso-equipe-frontend -n apsso
	kubectl rollout history deployment apsso-equipe-backend -n apsso
	kubectl rollout history deployment apsso-equipe-frontend -n apsso

update-login:
	kubectl rollout restart deployment apsso-login -n apsso
	kubectl rollout status deployment apsso-login -n apsso
	kubectl rollout history deployment apsso-login -n apsso

get:
	kubectl get all -n apsso