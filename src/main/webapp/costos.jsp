<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />

<style>
    .custom-file-button input[type=file]{
        margin-left: -2px !important;
    }
    .custom-file-button input[type=file]::-webkit-file-upload-button {
        display: none;
    }
    .custom-file-button input[type=file]::file-selector-button {
        display: none;
    }
    .custom-file-button:hover label {
        background-color: #dde0e3;
        cursor: pointer;
    }
    .elemento .ayuda {
        color: white;
        transition: color 0.5s ease; /* Agrega una transición suave */
    }
    .elemento:hover .ayuda {
        color: dimgrey;
        transition: color 0.5s ease; /* Agrega una transición suave */
    }
    .dataTables_wrapper {
        overflow-x: auto;
    }
</style>

<main>
    <div class="container-fluid" style="padding-top:60px; text-align: center">
        <div class="row justify-content-center">

            <div class="col-12 col-md-5">

                <form id="costos_form" method="post" action="costo" enctype="multipart/form-data">
                    <div class="section-border">
                        <div class="section-title">Registro de Costos</div>
                        <p class="disabled text-center text-body-secondary mb-3" style="color: dimgrey">Todos los campos marcados con <i class="text-danger">*</i> son obligatorios. Sin embargo, los datos pueden modificarse después.</p>
                        <br>
                        <input type="hidden" id="id_costo" name="id_costo" value="0" />

                        <div class="row">

                            <div class="col-6 mb-3 elemento">
                                <label for="materia" class="form-label">Materia<i class="text-danger">*</i></label>
                                <select class="form-select" id="materia" name="materia" aria-describedby="materia_ayuda" required>
                                    <option value="" disabled selected>Seleccione...</option>
                                    <option value="HUMO">Humo</option>
                                    <option value="MOTRIZ">Motriz</option>
                                    <option value="ARRASTRE">Arrastre</option>
                                    <option value="GASOLINA">Gasolina</option>
                                </select>
                                <div id="materia_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la materia de este costo.</div>
                            </div>

                            <div class="col-6 mb-3 elemento">
                                <label for="costo" class="form-label">Costo</label>
                                <c:choose>
                                    <c:when test="${sessionScope.tipoSesion == 'admin'}">
                                        <input type="number" step="0.01" max="9999999999.99" min="0" class="form-control" name="costo" id="costo"
                                               aria-describedby="costo_ayuda">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="number" step="0.01" max="999999999999.99" min="0" class="form-control text-muted"
                                               name="costoO" id="costoO" aria-describedby="costo_ayuda" disabled
                                               readonly style="color: grey;"
                                               value="">
                                        <input type="hidden" id="costo" name="costo" value="0.0">
                                    </c:otherwise>
                                </c:choose>
                                <div id="costo_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el costo (solo numeros).</div>
                            </div>

                            <div class="col-6 mb-3 elemento">
                                <label for="cliente_empresa" class="form-label">Empresa Cliente</label>
                                <input type="text" class="form-control" name="cliente_empresa" id="cliente_empresa"
                                       aria-describedby="cliente_empresa_ayuda" maxlength="150">
                                <div id="cliente_empresa_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre del cliente.</div>
                            </div>

                            <div class="col-6 mb-3 elemento">
                                <label for="encargado" class="form-label">Encargado</label>
                                <input type="text" class="form-control" name="encargado" id="encargado"
                                       aria-describedby="encargado_ayuda" maxlength="150">
                                <div id="encargado_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre del encargado.</div>
                            </div>

                            <div class="col-6 mb-3 elemento">
                                <label for="atiende_y_cobra" class="form-label">Persona que Atiende y Cobra</label>
                                <input type="text" class="form-control" name="atiende_y_cobra" id="atiende_y_cobra"
                                       aria-describedby="atiende_y_cobra_ayuda" maxlength="150">
                                <div id="atiende_y_cobra_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre de la persona que atiende y cobra.</div>
                            </div>

                            <div class="offset-4 col-4 mb-3">
                                <br>
                                <button type="submit" class="btn btn-primary">Registrar Costo</button>
                            </div>

                        </div>
                    </div>
                    <br>
                </form>
            </div>

            <div class="col-10 dataTables_wrapper pb-5">
                <table id="tabla_costos" class="table table-striped table-hover align-middle display nowrap" style="width:100%">
                    <thead>
                    <tr>
                        <th>MATERIA</th>
                        <th>COSTO</th>
                        <th>EMPRESA CLIENTE</th>
                        <th>ENCARGADO</th>
                        <th>ATIENDE Y COBRA</th>
                    </tr>
                    </thead>
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
            if (data && data.idCosto) {
                $('#id_costo').val(data.idCosto);
                $('#materia').val(data.materia);
                $('#costo').val(data.costo);
                $('#cliente_empresa').val(data.clienteEmpresa);
                $('#encargado').val(data.encargado);
                $('#atiende_y_cobra').val(data.atiendeYCobra);
            } else {
                console.error('La fila seleccionada no contiene la propiedad idPedido');
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
            if (data && data.idCosto) {
                window.location.href = '${pageContext.request.contextPath}/eliminar_costo?id=' + data.idCosto;
            } else {
                console.error('La fila seleccionada no contiene la propiedad idCosto');
            }

            // Cerrar el modal
            $('#seguridadModal').modal('hide');
        });

        //Usar para limpiar los inputs
        $('#btnCancelar').on('click', function () {
            $('#id_costo').val("0"); // Asignar ID de pedido al campo oculto
            $('#materia').val("");
            $('#costo').val("");
            $('#cliente_empresa').val("");
            $('#encargado').val("");
            $('#atiende_y_cobra').val("");  // Reiniciar el valor del campo file
        });

        //Usar para limpiar los inputs
        $('#btnCancelar2').on('click', function () {
            $('#id_costo').val("0"); // Asignar ID de pedido al campo oculto
            $('#materia').val("");
            $('#costo').val("");
            $('#cliente_empresa').val("");
            $('#encargado').val("");
            $('#atiende_y_cobra').val("");  // Reiniciar el valor del campo file
        });
    });
</script>
<jsp:include page="templates/footerDataTablesCostos.jsp" />