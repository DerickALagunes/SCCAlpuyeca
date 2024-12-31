<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />

<main style="text-align: center; padding: 50px 20px;">
    <h1 style="font-size: 3em; color: #d9534f;">404 - Página no encontrada</h1>
    <p style="font-size: 1.2em; color: #5a5a5a;">
        Lo sentimos, no pudimos encontrar la página que estás buscando.
    </p>
    <img src="assets/img/404.png" alt="Error 404" style="max-width: 100%; height: auto; margin: 20px 0;">
    <p style="font-size: 1em; color: #5a5a5a;">
        Por favor, revisa la URL o regresa a la página principal.
    </p>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary" style="margin-top: 20px;">Ir a la página principal</a>
</main>

<jsp:include page="templates/footer.jsp" />
