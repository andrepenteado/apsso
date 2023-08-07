# AProove: Gestão de pacientes

Sistema de controle e registro de acesso na portaria do câmpus

## Guia de Comandos

Um guia de comandos para criar componentes para o frontend

### Módulos

Novo módulo

```
ng g m --routing <nome-do-módulo>
```

### Entidades

Entidades de dados

```
cd frontend/src/app/entities
ng g class --skip-tests <nome-da-entidade> 
```

### Serviços

Serviços de acesso a APIs

```
cd frontend/src/app/services
ng g service --skip-tests <nome-do-serviço> 
```

### Componentes complexos

Componentes complexos com separação entre controller e view

```
cd frontend/src/app/components
ng g c --skip-tests -s <nome-do-componente> 
```

### Componentes simples

Componentes simples do frontend

```
cd frontend/src/app/views
ng g c --skip-tests --flat -s -t <nome-do-componente>
```

## Executar em modo testes

Não esquecer da barra (/) final

```
ng build --watch --base-href /aproove/
```
