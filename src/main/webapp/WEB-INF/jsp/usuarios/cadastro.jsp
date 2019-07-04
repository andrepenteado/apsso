<%--
  User: André Penteado
  Date: 27/08/18
  Time: 10:02
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/erro"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="font-awesome,jquery.validation,jquery.inputmask,jquery.datetimepicker"/>

<c:set var="actionGravar"><c:url value="/usuarios/gravar"/></c:set>

<html>
<head>
  <title>Cadastro de Usuário</title>
  <meta name="header" content="Cadastro"/>
  <meta name="previouspage" content="<li class='breadcrumb-item'><a href='<c:url value="/usuarios"/>'>Usuários</a></li>" />
</head>
<body>
  <script type="text/javascript">
      $(document).ready(function() {
          var form = $("#usuario").validate({
              rules : {
                  login : { required : true },
                  txt_confirme_senha : { equalTo : "#txt_nova_senha" },
                  nome : { required : true }
              }
          });
          $("#data_expiracao").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
          $("#txt_data_expiracao").inputmask("99/99/9999");
          $("#login").focus();
      });
  </script>

  <form:form action="${actionGravar}" modelAttribute="usuario">
    <form:hidden path="id"/>
    <form:hidden path="senha"/>
    <div class="col-12 col-md-6 offset-md-3">
      <div class="card">
        <div class="card-body border border-primary rounded shadow-lg">
          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="usuario"/></jsp:include>
          <h3 class="card-title">Cadastro de Usuário</h3>
          <hr>
          <div class="form-group">
            <label>Login</label>
            <form:input class="form-control" path="login"/>
          </div>
          <div class="form-group">
            <label for="txt_nova_senha">Senha</label>
            <input type="password" class="form-control" name="txt_nova_senha" id="txt_nova_senha"/>
            <small class="form-text text-muted">Deixe em branco caso não queira mudar sua senha.</small>
          </div>
          <div class="form-group">
            <label for="txt_confirme_senha">Confirme sua senha</label>
            <input type="password" class="form-control" name="txt_confirme_senha" id="txt_confirme_senha">
          </div>
          <div class="form-group">
            <label for="nome">Nome completo</label>
            <form:input class="form-control" path="nome"/>
          </div>
          <div class="form-group">
            <label for="nome">Data Expiração</label>
            <div class="input-group date" id="data_expiracao" data-target-input="nearest">
              <form:input class="form-control datetimepicker-input" data-target="#data_expiracao" path="expiracao"/>
              <div class="input-group-append" data-target="#data_expiracao" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
              </div>
            </div>
          </div>
          <div class="form-check form-check-inline">
            <form:checkboxes path="perfis" items="${listaPerfis}" cssClass="form-check-input"></form:checkboxes>
          </div>
          <p class="text-center">
            <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Gravar</button>
          </p>
        </div>
      </div>
    </div>
  </form:form>
  <br/>
</body>
</html>