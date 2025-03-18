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
  <title>:: Atenção ::</title>
  <link rel="stylesheet" type="text/css" href="assets/css/login.min.css">
  <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
</head>
<body style="background-color: dimgray">
<section class="p-3 p-md-4 p-xl-5">
  <div class="container">
    <div class="row">
      <div class="col-12 col-md-12 bsb-tpl-bg-platinum">
        <div class="d-flex flex-column justify-content-between h-100 p-3 p-md-4 p-xl-5">
          <h3 class="m-0 text-center">${mensagemInfo}</h3>
          <br><br>
          <p class="mb-0 text-center">Para voltar ao login, <a href="<c:url value='/login'/>" class="link-secondary text-decoration-none">clique aqui</a></p>
        </div>
      </div>
    </div>
  </div>
</section>
</body>
</html>
