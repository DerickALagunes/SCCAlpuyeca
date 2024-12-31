<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="modal fade" id="seguridadModal" tabindex="-1" aria-labelledby="seguridadModalLabel" aria-hidden="true" style="z-index: 1070">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <!-- Encabezado del modal -->
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title" id="seguridadModalLabel"><i class="bi bi-exclamation-triangle-fill"></i>¿Estás Seguro?<i class="bi bi-exclamation-triangle-fill"></i></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Cuerpo del modal -->
            <div class="modal-body text-black text-center">
                <p class="fs-5 fw-bold">
                    ¿Estás seguro que quieres eliminar este registro?
                </p>
                <p class="fs-6 fw-bolder">
                    Esta acción no se puede deshacer
                </p>
            </div>

            <!-- Footer con botones -->
            <div class="modal-footer d-flex justify-content-between">
                <button type="button" id="btnCancelar2" class="btn btn-secondary" data-bs-dismiss="modal">No, Cancelar <i class="bi bi-door-open-fill"></i></button>
                <button type="button" class="btn btn-danger" id="btnConfirma">Si, Eliminar <i class="bi bi-trash-fill"></i></button>
            </div>
        </div>
    </div>
</div>
