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

        if ($.fn.DataTable.isDataTable('#tabla_transacciones')) {
            $('#tabla_transacciones').DataTable().destroy();
        }

        <c:if test="${not empty mensaje}">
            var mensajeModal = new bootstrap.Modal(document.getElementById('mensajeModal'));
            mensajeModal.show();
        </c:if>

        // Inicializar DataTable y almacenar la instancia
        const table = new DataTable(document.getElementById('tabla_transacciones'), {

            initComplete: function() {
                this.api().columns().every(function() {
                    var column = this;
                    var select = $('<select class="form-control form-control-sm"><option value="">Todos</option></select>')
                        .appendTo($(column.footer()).empty())
                        .on('change', function() {
                            // Actualizar el valor del filtro y enviar la nueva solicitud AJAX
                            table.column(column.index()).search($(this).val()).draw();

                            // Actualizar el indicador de filtros
                            updateFilterIndicator();
                        });

                    column.data().unique().sort().each(function(d, j) {
                        select.append('<option value="' + d + '">' + d + '</option>');
                    });
                });
            },
            scrollX: true, // Habilitar desplazamiento horizontal
            fixedHeader: false, // Asegúrate de desactivar encabezados fijos
            autoWidth: false,
            language: {
                url: '${pageContext.request.contextPath}/assets/js/es-MX.json'
            },
            ajax: {
                url: '${pageContext.request.contextPath}/ver_transacciones',
                type: 'GET',
                data: function(d) {
                    d.columns.forEach((column, index) => {
                        column.searchValue = table.column(index).search();
                    });
                }
            },
            processing: true,
            serverSide: true,
            columns: [
                {data: 'cliente.gestor',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrado</span>';
                    }},
                {data: 'cliente.razonSocial',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }},
                {data: 'verificacion.vehiculo.placa',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }},
                {data: 'verificacion.vehiculo.serie',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }},
                {data: 'verificacion.materia',
                    render: function(data, type, row) {
                        return data ? data != "NULL" ? data : '<span class="text-danger">No registrada</span>' : '<span class="text-danger">No registrada</span>';
                    }},
                {data: 'verificacion.verificentro',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrado</span>';
                    }},


                {
                    data: 'verificacion.precio',
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
                
                {data: 'tipoPago',
                    render: function(data, type, row) {
                        return data ? data != "NULL" ? data : '<span class="text-danger">No registrado</span>' : '<span class="text-danger">No registrado</span>';
                    }},
                {data: 'numeroNota',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }},
                {data: 'cotizacion',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }},
                {data: 'verificacion.fechaFolio',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }
                },
                {data: 'verificacion.folio'},
                {data: 'cuentaDeposito',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }
                },
                {data: 'numeroFactura',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }
                },
                {data: 'pagado'},
                {
                    data: null, // No viene directamente del servidor
                    render: function(data, type, row) {
                        // Verificar el valor de la columna 'pagado'
                        if (row.pagado === 'SI') {
                            return '<span>NO</span>';
                        } else if (row.pagado === 'NO') {
                            return '<span>SI</span>';
                        } else {
                            return '<span>Desconocido</span>';
                        }
                    }
                },
                {data: 'fechaPedido',
                    render: function(data, type, row) {
                        return data ? data : '<span class="text-danger">No registrada</span>';
                    }
                }
            ]
        });

        // Agregar evento click a las filas
        $('#tabla_transacciones tbody').on('click', 'tr', function() {
            let data = table.row(this).data(); // Suponiendo que cada registro tiene un atributo data-id
            // Puedes almacenar el ID del registro para su uso posterior
            $('#opcionesModal').data('data', data);
            // Mostrar el modal
            $('#opcionesModal').modal('show');
        });

        function updateFilterIndicator() {
            var activeFilters = [];
            table.columns().every(function() {
                var column = this;
                var value = $('select', column.footer()).val();
                if (value) {
                    var headerText = $(column.header()).text().trim(); // Obtener texto del encabezado
                    activeFilters.push(headerText + " : "+ value);
                }
            });

            $('#activeFilters').text(
                activeFilters.length > 0 ? activeFilters.join(', ') : 'Ninguno'
            );
        }

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
