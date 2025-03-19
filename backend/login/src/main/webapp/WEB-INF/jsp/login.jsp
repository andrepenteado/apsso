<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/erro" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="APcode: Portal de single sign on para acessos de usuários e sistemas">
  <meta name="keywords" content="apcode, portal, acessos, sistemas, usuarios, login, java, jsp, bootstrap, angular">
  <meta name="author" content="pixelstrap">
  <link rel="icon" href="<%=request.getContextPath()%>/assets/imagens/favicon.png" type="image/x-icon">
  <link rel="shortcut icon" href="<%=request.getContextPath()%>/assets/imagens/favicon.png" type="image/x-icon">
  <title>:: Login ::</title>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/login.min.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css">
</head>
<body style="background-color: dimgray">
<!-- Login 3 - Bootstrap Brain Component -->
<section class="p-3 p-md-4 p-xl-5">
  <div class="container">
    <div class="row">
      <div class="col-12 col-md-6 bsb-tpl-bg-platinum">
        <div class="d-flex flex-column justify-content-between h-100 p-3 p-md-4 p-xl-5">
          <h3 class="m-0">Seja bem-vindo!</h3>
          <c:if test="${empty logotipo}">
            <img class="img-fluid rounded mx-auto my-4" loading="lazy" src=""<%=request.getContextPath()%>/assets/imagens/logo-apcode.png" width="300" alt="APcode Logo">
          </c:if>
          <c:if test="${not empty logotipo}">
            <img class="img-fluid rounded mx-auto my-4" loading="lazy" src="${logotipo.base64}" width="300" alt="APcode Logo">
          </c:if>
          <p class="mb-0 text-center">Não tem usuário cadastrado? <a href="<c:url value='novo-usuario'/>" class="link-secondary text-decoration-none">Crie seu login agora</a></p>
        </div>
      </div>
      <div class="col-12 col-md-6 bsb-tpl-bg-lotion">
        <div class="p-3 p-md-4 p-xl-5">
          <div class="row">
            <div class="col-12">
              <div class="mb-5">
                <h3>Digite seu usuário e senha para continuar</h3>
              </div>
            </div>
          </div>
          <c:if test="${param.error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="margin-top: -35px">
              <strong>Atenção!</strong> Usuário e/ou senha inválidos
            </div>
          </c:if>
          <form method="POST" action="<c:url value='/login'/>">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="row gy-3 gy-md-4 overflow-hidden">
              <div class="col-12">
                <div class="input-group">
                  <span class="input-group-text">
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-person-fill-lock" viewBox="0 0 16 16">
                      <path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0m-9 8c0 1 1 1 1 1h5v-1a2 2 0 0 1 .01-.2 4.49 4.49 0 0 1 1.534-3.693Q8.844 9.002 8 9c-5 0-6 3-6 4m7 0a1 1 0 0 1 1-1v-1a2 2 0 1 1 4 0v1a1 1 0 0 1 1 1v2a1 1 0 0 1-1 1h-4a1 1 0 0 1-1-1zm3-3a1 1 0 0 0-1 1v1h2v-1a1 1 0 0 0-1-1"/>
                    </svg>
                  </span>
                  <div class="form-floating">
                    <input type="text" class="form-control" name="username" id="username" placeholder="Digite seu usuário">
                    <label for="username">Digite seu usuário</label>
                  </div>
                </div>
              </div>
              <div class="col-12">
                <div class="input-group">
                  <span class="input-group-text">
                    <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-unlock-fill" viewBox="0 0 16 16">
                      <path d="M11 1a2 2 0 0 0-2 2v4a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2h5V3a3 3 0 0 1 6 0v4a.5.5 0 0 1-1 0V3a2 2 0 0 0-2-2"/>
                    </svg>
                  </span>
                  <div class="form-floating">
                    <input type="password" class="form-control" name="password" id="password" placeholder="Entre com sua senha">
                    <label for="password">Entre com a senha</label>
                  </div>
                </div>
              </div>
              <div class="col-12">
                <div class="d-grid">
                  <button class="btn bsb-btn-xl btn-primary btn-lg" type="submit">Entrar</button>
                </div>
              </div>
            </div>
          </form>
          <div class="row">
            <div class="col-12">
              <hr class="mt-5 mb-4 border-secondary-subtle">
              <div class="text-end">
                <a href="<c:url value='/esqueci-minha-senha'/>" class="link-secondary text-decoration-none">Esqueci minha senha</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<script>
  document.getElementById("username").focus();
</script>
</body>
</html>
