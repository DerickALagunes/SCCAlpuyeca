<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="modal fade" id="opcionesModal" tabindex="-1" aria-labelledby="opcionesModalLabel" aria-hidden="true" style="z-index: 1060">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <!-- Encabezado del modal -->
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title" id="opcionesModalLabel">Opciones de la Información</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <!-- Cuerpo del modal -->
            <div class="modal-body text-black text-center">
                <p class="fs-6 fw-bold">
                    ¿Qué acción deseas realizar con este registro?
                </p>
            </div>

            <!-- Footer con botones -->
            <div class="modal-footer d-flex justify-content-between">
                <button type="button" class="btn btn-success" id="btnActualizar">Actualizar <i class="bi bi-pencil-fill"></i></button>
                <button type="button" class="btn btn-danger" id="btnEliminar">Eliminar <i class="bi bi-trash-fill"></i></button>
                <button type="button" id="btnCancelar" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar <i class="bi bi-door-open-fill"></i></button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="modalSeguridad.jsp" />
