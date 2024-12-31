<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />

<main>
    <c:if test="${not empty error}">
        <h1>El servidor tuvo un error interno</h1>
        <p>Por favor, llama al administrador de la página y proporciónale el siguiente mensaje:</p>
        <br>
        <div style="color: red; font-weight: bold;">
                ${error}
        </div>
    </c:if>
</main>

<jsp:include page="templates/footer.jsp" />
