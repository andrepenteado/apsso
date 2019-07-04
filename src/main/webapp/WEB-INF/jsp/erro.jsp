<%@page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
<head>
  <title>Erro no processamento</title>
  <link href="/sso/assets/css/web-assets.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="/sso/webjars/font-awesome/5.7.2/css/all.min.css"></link>
  <link rel="stylesheet" href="/sso/webjars/bootstrap/4.3.1/css/bootstrap.min.css"></link>
  <script src="/sso/webjars/jquery/3.3.1-1/jquery.min.js"></script>
<body>

<!-- Invísivel em telas pequenas -->
<div class="d-none d-sm-block">
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb fixed-top" style="margin-top: 56px">
      <li class="breadcrumb-item active" aria-current="page">Erro no Processamento</li>
    </ol>
  </nav>
</div>

<nav class="navbar navbar-expand-md navbar-dark bg-primary fixed-top mb-4">
  <a class="navbar-brand" href="#"><strong>Portal de Sistemas</strong></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
</nav>

<main role="main" class="container">
  <div class="d-sm-none" style="margin-top: 70px;"></div>
  <div class="d-none d-sm-block" style="margin-top: 120px;"></div>
  <div class="text-center">
    <i class="fas fa-exclamation-triangle fa-4x"></i>
    <h2>Erro no processamento</h2>
    <p>Sua requisição não foi processada corretamente pelo servidor</p>
    <br/>
    <a href="<%=request.getContextPath()%>/" class="btn btn-primary"><i class="fa fa-home"></i> Página Inicial</a>
  </div>
</main> <!-- /container -->

<br><br>

<!--
    <%if (exception != null) exception.printStackTrace(new java.io.PrintWriter(out));%>
-->

<script src="/sso/webjars/popper.js/1.14.3/umd/popper.js"></script>
<script src="/sso/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>