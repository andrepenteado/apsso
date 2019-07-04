<%--
  User: André Penteado
  Date: 25/08/18
  Time: 14:04
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/erro" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dandelion:bundle includes="datatables.extended,floating.button"/>

<c:set var="linkController"><c:url value="/usuarios"/></c:set>

<html>
<head>
  <meta name="header" content="Usuários"/>
  <title>Usuários</title>
</head>
<body>

  <%@include file="/layouts/modal-confirmacoes.jsp"%>
  <%@include file="/layouts/modal-mensagens.jsp"%>

  <a href="${linkController}/incluir" class="float-button"><span class="fas fa-plus fa-lg"></span></a>

  <datatables:table data="${listagemUsuarios}" row="usuario" id="GridDatatable">
    <datatables:column title="Nome" property="nome"/>
    <datatables:column title="Login" property="login"/>
    <datatables:column title="Expiração" sortType="date-uk" cssCellClass="text-center">
      <fmt:parseDate value="${usuario.expiracao}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
      <fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
    </datatables:column>
    <datatables:column title="Opções" filterable="false" searchable="false" sortable="false" cssCellClass="text-center text-nowrap" cssStyle="width: 1%">
      <a href="${linkController}/editar/${usuario.id}" class="btn btn-success btn-sm" title="Editar"><i class='fas fa-pencil-alt'></i></a>
      <a href="#" class="btn btn-danger btn-sm" title="Excluir" onclick="confirmarExclusao('Deseja realmente excluir o usuário ${usuario.nome}?', '${linkController}/excluir/${usuario.id}'); return false;">
        <i class='fas fa-trash-alt'></i>
      </a>
    </datatables:column>
    <datatables:extraJs bundles="datatables.layout1.config" placeholder="before_start_document_ready" />
  </datatables:table>

</body>
</html>