<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/erro"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>

<dandelion:bundle includes="font-awesome5"/>

<sec:authorize access="hasAuthority(T(com.gitlab.andrepenteado.portal.KGlobal).K_PERFIL_ADMINISTRADOR)">
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle active" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
      <i class="fas fa-desktop"></i> Configurações
    </a>
    <div class="dropdown-menu">
      <a class="dropdown-item" href="<c:url value="/usuarios"/>"><i class="fas fa-users"></i> Usuários do Sistema</a>
    </div>
  </li>
</sec:authorize>