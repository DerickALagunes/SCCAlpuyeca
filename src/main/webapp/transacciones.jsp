<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />

<style>
    .dataTables_wrapper {
        overflow-x: auto;
    }
</style>

<main>
    <div class="container-fluid" style="padding-top:60px; text-align: center">
        <div class="row justify-content-center">
            <div class="col-10 dataTables_wrapper pb-5">

                <div id="filterIndicator" class="mt-2">
                    <span class="fs-5 fw-bolder"><i class="bi bi-filter"></i> Filtros aplicados:</span>
                    <span id="activeFilters" class="text-muted">Ninguno</span>
                </div>

                <table id="tabla_transacciones" class="table table-striped table-hover align-middle display nowrap" style="width:100%">
                    <thead>
                    <tr>
                        <th>GESTOR</th>
                        <th>RAZÓN SOCIAL</th>
                        <th>PLACA</th>
                        <th>SERIE</th>
                        <th>MATERIA</th>
                        <th>VERIFICENTRO</th>
                        <th>PRECIO</th>
                        <th>TIPO DE PAGO</th>
                        <th>NÚMERO DE NOTA</th>
                        <th>COTIZACION</th>
                        <th>FECHA DE FOLIO</th>
                        <th>FOLIO</th>
                        <th>CUENTA DE DEPOSITO</th>
                        <th>NÚMERO DE FACTURA</th>
                        <th>PAGADO</th>
                        <th>PENDIENTE</th>
                        <th>FECHA DE PEDIDO</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>GESTOR</th>
                        <th>RAZÓN SOCIAL</th>
                        <th>PLACA</th>
                        <th>SERIE</th>
                        <th>MATERIA</th>
                        <th>VERIFICENTRO</th>
                        <th>PRECIO</th>
                        <th>TIPO DE PAGO</th>
                        <th>NÚMERO DE NOTA</th>
                        <th>COTIZACION</th>
                        <th>FECHA DE FOLIO</th>
                        <th>FOLIO</th>
                        <th>CUENTA DE DEPOSITO</th>
                        <th>NÚMERO DE FACTURA</th>
                        <th>PAGADO</th>
                        <th>PENDIENTE</th>
                        <th>FECHA DE PEDIDO</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</main>
<jsp:include page="templates/modalDesicion.jsp" />

<script>
    document.addEventListener('DOMContentLoaded', function() {
        //Botones del modal de desiciones;
        $('#btnActualizar').on('click', function () {
            let data = $('#opcionesModal').data('data'); //Objeto JSON
            //console.log(data); // Inspeccionar los datos en la consola
            if (data && data.idTransaccion) {
                window.location.href = '${pageContext.request.contextPath}/actualizar_transaccion?id=' + data.idTransaccion;
            } else {
                console.error('La fila seleccionada no contiene la propiedad idTransaccion');
            }
            // Cerrar el modal
            $('#opcionesModal').modal('hide');
        });

        $('#btnEliminar').on('click', function () {
            let data = $('#opcionesModal').data('data'); //Objeto JSON
            // Puedes almacenar el ID del registro para su uso posterior
            $('#seguridadModal').data('data', data);
            // Cerrar el modal
            $('#opcionesModal').modal('hide');
            // Mostrar el modal
            $('#seguridadModal').modal('show');
        });

        $('#btnConfirma').on('click', function () {
            let data = $('#seguridadModal').data('data'); //Objeto JSON

            //Mandar al servlet de eliminacion
            //console.log(data); // Inspeccionar los datos en la consola
            if (data && data.idTransaccion) {
                window.location.href = '${pageContext.request.contextPath}/eliminar_transaccion?id=' + data.idTransaccion;
            } else {
                console.error('La fila seleccionada no contiene la propiedad idCosto');
            }

            // Cerrar el modal
            $('#seguridadModal').modal('hide');
        });
    });
</script>
<jsp:include page="templates/footerDataTables.jsp" />
