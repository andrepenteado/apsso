<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/erro"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>

<dandelion:bundle includes="font-awesome"/>

<sec:authorize access="isAuthenticated()">
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle active" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
      <i class="fas fa-user"></i>
    </a>
    <div class="dropdown-menu dropdown-menu-right">
      <a class="dropdown-item">Usuário: <strong><sec:authentication property="name"/></strong></a>
      <a class="dropdown-item">Perfil: <strong><sec:authentication property="authorities"/></strong></a>
      <a class="dropdown-item">IP: <strong><sec:authentication property="details.remoteAddress"/></strong></a>
      <div class="dropdown-divider"></div>
      <a class="dropdown-item" href="<c:url value="/usuarios/meusdados"/>"><i class="fas fa-user-edit"></i> Meus Dados</a>
      <div class="dropdown-divider"></div>
      <a class="dropdown-item" href="<c:url value="/logout"/>"><i class="fas fa-door-open"></i> Sair</a>
    </div>
  </li>
</sec:authorize>