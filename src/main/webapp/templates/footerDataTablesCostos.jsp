<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<footer class="footer">
    <p class="mt-1 mb-1 text-muted">©SIMOR</p>
</footer>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.7.0.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.js" ></script>
<script src="${pageContext.request.contextPath}/assets/js/datatables.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/dataTables.bootstrap5.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/es-MX.json"></script>
<!-- Script para mostrar el modal automáticamente si el mensaje existe -->
<script>
    document.addEventListener('DOMContentLoaded', () => {

        if ($.fn.DataTable.isDataTable('#tabla_pedidos')) {
            $('#tabla_costos').DataTable().destroy();
        }

        <c:if test="${not empty mensaje}">
            var mensajeModal = new bootstrap.Modal(document.getElementById('mensajeModal'));
            mensajeModal.show();
        </c:if>

        // Inicializar DataTable y almacenar la instancia
        const table = new DataTable(document.getElementById('tabla_costos'), {
            scrollX: true, // Habilitar desplazamiento horizontal
            fixedHeader: false, // Asegúrate de desactivar encabezados fijos
            autoWidth: false,
            language: {
                url: '${pageContext.request.contextPath}/assets/js/es-MX.json'
            },
            ajax: {
                url: '${pageContext.request.contextPath}/ver_costos',
                type: 'GET'
            },
            processing: true,
            serverSide: true,
            columns: [
                {data: 'materia',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }},

                {
                    data: 'costo',
                    render: function(data, type, row) {
                        // Determinar si se debe ocultar el precio o mostrarlo
                        <%
                            boolean ocultarPrecio = false;
                            String sesion = (String) request.getSession().getAttribute("tipoSesion");
                            if (sesion != null && "user".equals(sesion)) {
                                ocultarPrecio = true;
                            }
                        %>
                        var ocultarPrecio = <%= ocultarPrecio %>;

                        if (ocultarPrecio) {
                            return '<span class="text-danger">Oculto</span>';
                        } else {
                            return data ? data : '<span class="text-danger">No registrado</span>';
                        }
                    }
                },

                {data: 'clienteEmpresa',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrado</span>';
                    }},
                {data: 'encargado',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrado</span>';
                    }},
                {data: 'atiendeYCobra',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrado</span>';
                    }}
            ]
        });

        // Agregar evento click a las filas
        $('#tabla_costos tbody').on('click', 'tr', function() {
            let data = table.row(this).data(); // Suponiendo que cada registro tiene un atributo data-id
            // Puedes almacenar el ID del registro para su uso posterior
            $('#opcionesModal').data('data', data);
            // Mostrar el modal
            $('#opcionesModal').modal('show');
        });

    });
</script>
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
