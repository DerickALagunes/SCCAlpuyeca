<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<footer class="footer">
    <p class="mt-1 mb-1 text-muted">©SIMOR</p>
</footer>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.js" ></script>
<!-- Script para mostrar el modal automáticamente si el mensaje existe -->
<c:if test="${not empty mensaje}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var mensajeModal = new bootstrap.Modal(document.getElementById('mensajeModal'));
            mensajeModal.show();
        });
    </script>
</c:if>

<!-- Modal de Bootstrap -->
<div class="modal fade" id="mensajeModal" tabindex="-1" aria-labelledby="mensajeModalLabel" aria-hidden="true" style="z-index: 1060">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <c:choose>
                <c:when test="${mensaje.tipo == 'SUCCESS'}">
                    <div class="modal-header bg-success text-white">
                        <h5 class="modal-title" id="mensajeModalLabel">Éxito <i class="bi-check-circle-fill"></i></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                </c:when>
                <c:when test="${mensaje.tipo == 'ERROR'}">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title" id="mensajeModalLabel">Error <i class="bi-exclamation-triangle-fill"></i></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="modal-header bg-info text-white">
                        <h5 class="modal-title" id="mensajeModalLabel">Información <i class="bi-info-circle-fill"></i></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="modal-body text-black text-center">
                <c:forEach var="texto" items="${mensaje.texto}">
                    <span class="fs-6 fw-bolder">
                            ${texto}
                    </span></br>
                </c:forEach>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>
<%
    request.getSession().removeAttribute("mensaje");
%>
</body>
</html>
