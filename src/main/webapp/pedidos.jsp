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
    /* Estilo para la imagen difuminada */
    #preview_image.blurred {
        filter: blur(5px);
        transition: filter 0.3s ease; /* Transición suave */
    }
</style>

<main>
    <div class="container-fluid" style="padding-top:60px; text-align: center">
        <div class="row justify-content-center">

            <div class="col-12 col-md-5">

                <form id="pedido_form" method="post" action="pedido" enctype="multipart/form-data">
                    <div class="section-border">
                        <div class="section-title">Registro de un pedido</div>
                        <p class="disabled text-center text-body-secondary mb-3" style="color: dimgrey">Todos los campos marcados con <i class="text-danger">*</i> son obligatorios. Sin embargo, los datos pueden modificarse después.</p>
                        <br>
                        <input type="hidden" id="id_pedido" name="id_pedido" value="0" />

                        <div class="row">

                            <div class="col-6 mb-3 elemento">
                                <label for="fecha_envio" class="form-label">Fecha de Envio <i class="text-danger">*</i></label>
                                <input type="date" class="form-control" name="fecha_envio" id="fecha_envio"
                                       aria-describedby="fecha_envio_ayuda" required>
                                <div id="fecha_envio_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la fecha del folio de esta verficación.</div>
                            </div>

                            <div class="col-6 mb-3 elemento">
                                <label for="numero_guia" class="form-label">Número de Guía</label>
                                <input type="text" class="form-control" name="numero_guia" id="numero_guia"
                                       aria-describedby="numero_guia_ayuda" maxlength="50">
                                <div id="numero_guia_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de guía del pedido (Si aplica).</div>
                            </div>

                        </div>
                        <div class="row">

                            <div class="col-6 mb-3">
                                <div class="elemento">
                                    <label for="recibio" class="form-label">Persona que Recibió</label>
                                    <input type="text" class="form-control" name="recibio" id="recibio"
                                           aria-describedby="recibio_ayuda" maxlength="100">
                                    <div id="recibio_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre de la persona que recibió el pedido (Si aplica).</div>
                                </div>
                                <br>
                                <div class="elemento">
                                    <label for="foto" class="form-label">Foto</label>
                                    <input type="file" class="form-control custom-file-button" name="foto" id="foto" accept="image/png, image/jpeg">
                                    <div id="foto_ayuda" class="form-text ayuda">Carga aquí el archivo .png o .jpeg con la foto del pedido.</div>
                                </div>
                            </div>

                            <div class="col-6 mb-3 elemento">
                                <!-- Componente de vista previa de la imagen -->
                                <div id="foto_preview" class="mt-3">
                                    <img id="preview_image" src="" alt="Vista previa de la foto" class="img-thumbnail img-fluid blurred" style="display: none;">
                                </div>
                            </div>

                            <div class="offset-4 col-4 mb-3">
                                <br>
                                <button type="submit" class="btn btn-primary">Registrar Pedido</button>
                            </div>

                        </div>
                    </div>
                    <br>
                </form>
            </div>


            <div class="col-10 dataTables_wrapper pb-5">

                <table id="tabla_pedidos" class="table table-striped table-hover align-middle display nowrap" style="width:100%">
                    <thead>
                    <tr>
                        <th>FECHA DE ENVIO</th>
                        <th>NÚMERO DE GUÍA</th>
                        <th>PERSONA QUE RECIBIÓ</th>
                        <th>FOTO</th>
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
        const fechaInput = document.getElementById('fecha_envio');
        const today = new Date().toISOString().split('T')[0]; // Obtiene la fecha en formato YYYY-MM-DD
        fechaInput.value = today;


        // Mostrar vista previa de la foto cuando el usuario seleccione una imagen
        $('#foto').on('change', function(event) {
            const file = event.target.files[0]; // Obtener el archivo seleccionado
            const reader = new FileReader();

            if (file) {
                // Aplicar el filtro de difuminado a la imagen antes de mostrarla
                $('#preview_image').addClass('blurred'); // Agregar clase para difuminar la imagen

                // Cargar la imagen seleccionada
                reader.onload = function(e) {
                    // Establecer la fuente de la imagen
                    $('#preview_image').attr('src', e.target.result);

                    // Mostrar la imagen y remover el difuminado una vez que la imagen se ha cargado
                    $('#preview_image').on('load', function() {
                        $('#preview_image').removeClass('blurred'); // Remover el difuminado
                    });

                    // Asegurarse de que la imagen se muestre
                    $('#preview_image').show();
                };

                // Leer el archivo como URL de datos (base64)
                reader.readAsDataURL(file);
            } else {
                // Si no se selecciona ningún archivo, ocultar la imagen de vista previa
                $('#preview_image').hide();
            }
        });

        //Botones del modal de desiciones;
        $('#btnActualizar').on('click', function () {
            let data = $('#opcionesModal').data('data'); //Objeto JSON
            //console.log(data); // Inspeccionar los datos en la consola
            if (data && data.idPedido) {
                // Asignar los valores de los datos a los campos del formulario
                $('#id_pedido').val(data.idPedido); // Asignar ID de pedido al campo oculto

                let fechaEnvio = data.fechaEnvio; // Ejemplo: "dic 30, 2024"

                // Tabla de meses en español
                const meses = {
                    ene: "01",
                    feb: "02",
                    mar: "03",
                    abr: "04",
                    may: "05",
                    jun: "06",
                    jul: "07",
                    ago: "08",
                    sep: "09",
                    oct: "10",
                    nov: "11",
                    dic: "12"
                };

                // Función para convertir el formato
                function convertirFecha(fecha) {
                    if (!fecha || typeof fecha !== "string") {
                        throw new Error("Fecha no válida o vacía.");
                    }

                    // Dividir la fecha y verificar partes
                    let partes = fecha.split(" ");
                    if (partes.length !== 3) {
                        throw new Error("Formato de fecha incorrecto: " + fecha);
                    }

                    let mes = partes[0];
                    let dia = partes[1].replace(",", ""); // Eliminar coma del día si está presente
                    let anio = partes[2];
                    let mesNumero = meses[mes.toLowerCase()]; // Convertir el mes a número

                    if (!mesNumero) {
                        throw new Error("Mes no válido: " + mes);
                    }

                    // Asegurarse de que el día sea un número válido
                    if (isNaN(dia) || dia < 1 || dia > 31) {
                        throw new Error("Día no válido: " + dia);
                    }

                    // Formatear el día con ceros a la izquierda
                    if (dia.length === 1) {
                        dia = "0" + dia;
                    }

                    // Retornar en formato YYYY-MM-DD
                    return anio + "-" + mesNumero + "-" + dia;
                }

                // Convertir la fecha y asignar al input
                try {
                    let fechaFormateada = convertirFecha(fechaEnvio);
                    $('#fecha_envio').val(fechaFormateada);
                } catch (error) {
                    console.error("Error al convertir la fecha:", error.message);
                }

                $('#numero_guia').val(data.numeroGuia); // Asignar el número de guía (ajusta según el campo correcto en tus datos)
                $('#recibio').val(data.recibio); // Asignar la persona que recibió (ajusta según el campo correcto en tus datos)

                // Si tienes un campo de foto, no puedes cargar la foto directamente en el input, pero puedes mostrarla en una vista previa
                if (data.foto) {
                    // Mostrar la foto en una etiqueta de imagen, si tienes un campo para la vista previa
                    $('#preview_image').attr('src', data.foto);  // Cambiar el src de la imagen
                    $('#preview_image').css('display', 'block');  // Asegurarse de que la imagen se muestre
                    $('#foto').val('');  // Reiniciar el valor del campo file
                }
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
            if (data && data.idPedido) {
                window.location.href = '${pageContext.request.contextPath}/eliminar_pedido?id=' + data.idPedido;
            } else {
                console.error('La fila seleccionada no contiene la propiedad idPedido');
            }

            // Cerrar el modal
            $('#seguridadModal').modal('hide');
        });

        //Usar para limpiar los inputs
        $('#btnCancelar').on('click', function () {
            $('#id_pedido').val("0"); // Asignar ID de pedido al campo oculto
            $('#fecha_envio').val(today);
            $('#numero_guia').val(""); // Asignar el número de guía (ajusta según el campo correcto en tus datos)
            $('#recibio').val("");
            $('#preview_image').attr('src', "");  // Cambiar el src de la imagen
            $('#preview_image').css('display', 'none');  // Asegurarse de que la imagen se muestre
            $('#foto').val('');  // Reiniciar el valor del campo file
        });

        //Usar para limpiar los inputs
        $('#btnCancelar2').on('click', function () {
            $('#id_pedido').val("0"); // Asignar ID de pedido al campo oculto
            $('#fecha_envio').val(today);
            $('#numero_guia').val(""); // Asignar el número de guía (ajusta según el campo correcto en tus datos)
            $('#recibio').val("");
            $('#preview_image').attr('src', "");  // Cambiar el src de la imagen
            $('#preview_image').css('display', 'none');  // Asegurarse de que la imagen se muestre
            $('#foto').val('');  // Reiniciar el valor del campo file
        });
    });
</script>
<jsp:include page="templates/footerDataTablesPedidos.jsp" />