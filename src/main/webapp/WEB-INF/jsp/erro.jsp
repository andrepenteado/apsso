<%@page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
<head>
  <title>Erro no processamento</title>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/js/all.js"></link>
</head>
<body>

<div style="background-color: #337ab7; overflow:hidden; position: fixed; top: 0; left: 0; width: 100%">
  <p style="float: left; font-size: 24px; font-weight: bold; color: #fff; margin-top: 10px; height: 31px;">&nbsp;&nbsp;Portal de Sistemas</p>
</div>

<div style="text-align: center; margin-top: 100px;">
  <i class="fas fa-exclamation-triangle fa-4x"></i>
  <h2>Erro no processamento</h2>
  <p>Sua requisição não foi processada corretamente pelo servidor.</p>
  <br/>
  <a href="<%=request.getContextPath()%>/" class="btn btn-primary"><i class="fa fa-home"></i> Página Inicial</a>
</div>

<!--
    <%if (exception != null) exception.printStackTrace(new java.io.PrintWriter(out));%>
-->
</body>
</html>