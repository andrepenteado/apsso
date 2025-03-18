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
  <link rel="icon" href="assets/imagens/favicon.png" type="image/x-icon">
  <link rel="shortcut icon" href="assets/imagens/favicon.png" type="image/x-icon">
  <title>:: Alterar Senha ::</title>
  <link rel="stylesheet" type="text/css" href="assets/css/login.min.css">
  <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
</head>
<body style="background-color: dimgray">
<!-- Login 3 - Bootstrap Brain Component -->
<section class="p-3 p-md-4 p-xl-5">
  <div class="container">
    <div class="row">
      <div class="col-12 col-md-6 bsb-tpl-bg-lotion">
        <div class="p-3 p-md-4 p-xl-5">
          <div class="row">
            <div class="col-12">
              <div class="mb-5">
                <h3>Entre com a nova senha</h3>
              </div>
            </div>
          </div>
          <c:if test="${mensagemErro != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="margin-top: -35px">
              <strong>Atenção!</strong> ${mensagemErro}
            </div>
          </c:if>
          <form method="POST" action="<c:url value='/gravar-alterar-senha'/>">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="row mb-3">
              <div class="form-group col-12 col-md-6">
                <label>Senha</label>
                <input type="password" name="nome" class="form-control">
              </div>
              <div class="form-group col-12 col-md-6">
                <label>Confirme sua senha</label>
                <input type="password" name="nome" class="form-control">
              </div>
            </div>
            <div class="row mb-3">
              <div class="col-12 text-center">
                <button class="btn btn-primary">Gravar</button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div class="col-12 col-md-6 bsb-tpl-bg-platinum">
        <div class="d-flex flex-column justify-content-between h-100 p-3 p-md-4 p-xl-5">
          <h3 class="m-0 text-center">Alteração de senha</h3>
          <c:if test="${empty logotipo}">
            <img class="img-fluid rounded mx-auto my-4" loading="lazy" src="assets/imagens/logo-apcode.png" width="300" alt="APcode Logo">
          </c:if>
          <c:if test="${not empty logotipo}">
            <img class="img-fluid rounded mx-auto my-4" loading="lazy" src="${logotipo.base64}" width="300" alt="Logotipo empresa">
          </c:if>
          <p class="mb-0 text-center">É um usuário cadastrado? <a href="<c:url value='/login'/>" class="link-secondary text-decoration-none">Faça aqui seu login</a></p>
        </div>
      </div>
    </div>
  </div>
</section>
</body>
</html>
