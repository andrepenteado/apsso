<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/erro"%>

<html>
<head>
  <title>Página Inicial</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.6.1.slim.min.js" integrity="sha256-w8CvhFs7iHNVUtnSP0YKEg00p9Ih13rlL9zGqvLdePA=" crossorigin="anonymous"></script>
</head>
<body>

<!-- Invísivel em telas pequenas -->
<div class="d-none d-sm-block">
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb fixed-top" style="margin-top: 56px">
      <li class="breadcrumb-item active" aria-current="page">Página Inicial</li>
    </ol>
  </nav>
</div>

<nav class="navbar navbar-expand-md navbar-dark bg-primary fixed-top mb-4">
  <a class="navbar-brand" href="#"><strong>Módulo de Controle</strong></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
</nav>

<main role="main" class="container">
  <div class="d-sm-none" style="margin-top: 70px;"></div>
  <div class="d-none d-sm-block" style="margin-top: 120px;"></div>
  <div class="text-center">
    <i class="fas fa-home fa-4x"></i>
    <h2>Página Inicial</h2>
    <p>Você está conectado em <strong><%=application.getServletContextName()%></strong> através do IP <strong><%=request.getRemoteAddr()%></strong></p>
  </div>
</main> <!-- /container -->

<br><br>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>

</body>
</html>
