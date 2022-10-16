<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/erro"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
  <title>Login</title>
  <meta name="header" content="Login" />
  <title>Página Inicial</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.6.1.slim.min.js" integrity="sha256-w8CvhFs7iHNVUtnSP0YKEg00p9Ih13rlL9zGqvLdePA=" crossorigin="anonymous"></script>
</head>

<body>
  <script type="text/javascript">
      $(document).ready(function() {
          var form = $("#form-login").validate({
              rules : {
                  username : { required : true },
                  password : { required : true }
              }
          });
          $("#username").focus();
      });
  </script>

  <br>
  <br>

  <form name="form-login" id="form-login" method="POST" action="<c:url value='/login'/>">

    <div class="col-12 col-md-6 offset-md-3">
      <div class="card">
        <div class="card-body border border-primary rounded shadow-lg">
          <c:if test="${param.error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <i class="fas fa-exclamation-triangle"></i> Usuário e/ou senha inválidos
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>
          <h3 class="card-title">Digite seu usuário e senha para entrar</h3>
          <hr>
          <div class="form-group">
            <div class="input-group input-group-lg">
              <input type="text" class="form-control" placeholder="Nome de usuário" name="username" id="username"/>
              <div class="input-group-prepend">
                <span class="input-group-text" style="width: 50px;"><i class="fas fa-user-circle"></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="input-group input-group-lg">
              <input type="password" class="form-control" placeholder="Senha" name="password" id="password"/>
              <div class="input-group-prepend">
                <span class="input-group-text" style="width: 50px;"><i class="fas fa-lock" ></i></span>
              </div>
            </div>
          </div>
          <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block btn-lg"><i class="fas fa-user"></i> Entrar</button>
          </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </div>
    </div>

  </form>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>

</body>

</html>
