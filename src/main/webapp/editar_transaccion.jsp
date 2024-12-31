<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />

<style>
  .elemento .ayuda {
    color: white;
    transition: color 0.5s ease; /* Agrega una transición suave */
  }
  .elemento:hover .ayuda {
    color: dimgrey;
    transition: color 0.5s ease; /* Agrega una transición suave */
  }
</style>

<main>
  <div class="container-fluid" style="padding-top:60px; text-align: center">
    <div class="row justify-content-center">
      <div class="col-12 col-md-5">

        <form method="post" action="actualizar_transaccion" enctype="multipart/form-data">
          <!-- Campo oculto para el ID de la transacción -->
          <input type="hidden" name="id_transaccion" value="${transaccion.idTransaccion}" />

          <div class="section-border">
            <div class="section-title">Actualización de una transacción</div>
            <p class="disabled text-center text-body-secondary mb-3" style="color: dimgrey">Todos los campos marcados con <i class="text-danger">*</i> son obligatorios. Sin embargo, los datos pueden modificarse después.</p>
            <br>

            <!-- Nuevo Cliente -->
            <div class="row">
              <div class="section-title">Datos del Cliente</div>

              <div class="col-6 mb-3 elemento">
                <label for="gestor" class="form-label">Gestor</label>
                <input type="text" class="form-control" name="gestor" id="gestor" maxlength="255" value="${transaccion.cliente.gestor}" aria-describedby="gestor_ayuda">
                <div id="gestor_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre del gestor del cliente.</div>
              </div>

              <div class="col-6 mb-3 elemento">
                <label for="razon_social" class="form-label">Razón Social</label>
                <input type="text" class="form-control" name="razon_social" id="razon_social" maxlength="255" value="${transaccion.cliente.razonSocial}" aria-describedby="razon_social_ayuda">
                <div id="razon_social_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> la razón social del cliente.</div>
              </div>
            </div>

            <!-- Selección de vehículo -->
            <div class="row">
              <div class="section-title">Datos del Vehículo</div>

              <div class="col-6 mb-3 elemento">
                <label for="placa" class="form-label">Placa</label>
                <input type="text" class="form-control" name="placa" id="placa" maxlength="50" value="${transaccion.verificacion.vehiculo.placa}" aria-describedby="placa_ayuda">
                <div id="placa_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> la placa del vehículo.</div>
              </div>

              <div class="col-6 mb-3 elemento">
                <label for="serie" class="form-label">Número de Serie</label>
                <input type="text" class="form-control" name="serie" id="serie" maxlength="255" value="${transaccion.verificacion.vehiculo.serie}" aria-describedby="serie_ayuda">
                <div id="serie_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de serie del vehículo.</div>
              </div>
            </div>

            <!-- Ingreso de información de la Verificación -->
            <div class="row">
              <div class="section-title">Datos de la Verificación</div>

              <div class="col-4 mb-3 elemento">
                <label for="materia" class="form-label">Materia<i class="text-danger">*</i></label>
                <select class="form-select" id="materia" name="materia" aria-describedby="materia_ayuda" required>
                  <option value="" disabled>Seleccione...</option>
                  <option value="HUMO" ${transaccion.verificacion.materia == 'HUMO' ? 'selected' : ''}>Humo</option>
                  <option value="MOTRIZ" ${transaccion.verificacion.materia == 'MOTRIZ' ? 'selected' : ''}>Motriz</option>
                  <option value="ARRASTRE" ${transaccion.verificacion.materia == 'ARRASTRE' ? 'selected' : ''}>Arrastre</option>
                  <option value="GASOLINA" ${transaccion.verificacion.materia == 'GASOLINA' ? 'selected' : ''}>Gasolina</option>
                </select>
                <div id="materia_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la materia de esta verificación.</div>
              </div>

              <div class="col-8 mb-3 elemento">
                <label for="verificentro" class="form-label">Verificentro</label>
                <input type="text" class="form-control" name="verificentro" id="verificentro" maxlength="255" value="${transaccion.verificacion.verificentro}" aria-describedby="verificentro_ayuda">
                <div id="verificentro_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre del verificentro.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="folio" class="form-label">Folio</label>
                <input type="text" class="form-control" name="folio" id="folio"
                       aria-describedby="folio_ayuda" value="${transaccion.verificacion.folio}">
                <div id="folio_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el folio de esta verficación.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="fecha_folio" class="form-label">Fecha del Folio<i class="text-danger">*</i></label>
                <input type="date" class="form-control" name="fecha_folio" id="fecha_folio"
                       aria-describedby="fecha_folio_ayuda" required value="${transaccion.verificacion.fechaFolio}">
                <div id="fecha_folio_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la fecha del folio de esta verficación.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="precio" class="form-label">Precio</label>
                <c:choose>
                  <c:when test="${sessionScope.tipoSesion == 'admin'}">
                    <input type="number" step="0.01" max="999999999999.99" min="0" class="form-control"
                           name="precio" id="precio" aria-describedby="precio_ayuda"
                           value="${transaccion.verificacion.precio}">
                  </c:when>
                  <c:otherwise>
                    <input type="number" step="0.01" max="999999999999.99" min="0" class="form-control text-muted"
                           name="precioO" id="precioO" aria-describedby="precio_ayuda" disabled
                           readonly style="color: grey;"
                           value="">
                    <input type="hidden" id="precio" name="precio" value="${transaccion.verificacion.precio}">
                  </c:otherwise>
                </c:choose>
                <div id="precio_ayuda" class="form-text ayuda">
                  Escribe aquí <i class="bi-arrow-up"></i> el precio de esta verificación (solo números).
                </div>
              </div>

            </div>

            <!-- Ingreso de información de la Transacción -->
            <div class="row">
              <div class="section-title">Datos de la Transacción</div>

              <div class="col-4 mb-3 elemento">
                <label for="tipo_pago" class="form-label">Tipo de Pago<i class="text-danger">*</i></label>
                <select class="form-select" id="tipo_pago" name="tipo_pago" aria-describedby="tipo_pago_ayuda" required>
                  <option value="" disabled>Seleccione...</option>
                  <option value="FACTURA" ${transaccion.tipoPago == 'FACTURA' ? 'selected' : ''}>Factura</option>
                  <option value="EFECTIVO" ${transaccion.tipoPago == 'EFECTIVO' ? 'selected' : ''}>Efectivo</option>
                  <option value="DEPOSITO" ${transaccion.tipoPago == 'DEPOSITO' ? 'selected' : ''}>Deposito</option>
                  <option value="NULL" ${transaccion.tipoPago == 'NULL' ? 'selected' : ''}>Otro</option>
                </select>
                <div id="tipo_pago_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> el tipo de pago.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="cuenta_deposito" class="form-label">Cuenta de deposito</label>
                <input type="text" class="form-control" name="cuenta_deposito" id="cuenta_deposito"
                       aria-describedby="cuenta_deposito_ayuda" value="${transaccion.cuentaDeposito}">
                <div id="cuenta_deposito_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> la cuenta de deposito.</div>
              </div>

              <div class="col-4 mb-3">
                <label class="form-label">¿Transacción Pagada?<i class="text-danger">*</i></label>
                <div class="form-check">
                  <input
                          class="form-check-input"
                          type="radio"
                          name="estado_pago"
                          id="pagada"
                          value="PAGADA"
                          <c:if test="${transaccion.pagado == 'SI'}">checked</c:if>
                          required>
                  <label class="form-check-label" for="pagada">
                    Sí, está pagada
                  </label>
                </div>
                <div class="form-check">
                  <input
                          class="form-check-input"
                          type="radio"
                          name="estado_pago"
                          id="no_pagada"
                          value="NO_PAGADA"
                          <c:if test="${transaccion.pagado == 'NO'}">checked</c:if>>
                  <label class="form-check-label" for="no_pagada">
                    No, no está pagada
                  </label>
                </div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="numero_nota" class="form-label">Número de nota</label>
                <input type="text" class="form-control" name="numero_nota" id="numero_nota"
                       aria-describedby="numero_nota_ayuda" maxlength="50" value="${transaccion.numeroNota}">
                <div id="numero_nota_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de nota (Si aplica).</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="numero_factura" class="form-label">Número de factura</label>
                <input type="text" class="form-control" name="numero_factura" id="numero_factura"
                       aria-describedby="numero_factura_ayuda" maxlength="50" value="${transaccion.numeroFactura}">
                <div id="numero_factura_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de factura (Si aplica).</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="numero_cotizacion" class="form-label">Número de cotización</label>
                <input type="text" class="form-control" name="numero_cotizacion" id="numero_cotizacion"
                       aria-describedby="numero_cotizacion_ayuda" maxlength="50" value="${transaccion.cotizacion}">
                <div id="numero_cotizacion_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de cotización (Si aplica).</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="atiende_y_cobra" class="form-label">Atiende y Cobra</label>
                <input type="text" class="form-control" name="atiende_y_cobra" id="atiende_y_cobra"
                       aria-describedby="atiende_y_cobra_ayuda" maxlength="100" value="${transaccion.atiendeYCobra}">
                <div id="atiende_y_cobra_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre de quien atiende y cobra.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="fecha_pedido" class="form-label">Fecha de Pedido<i class="text-danger">*</i></label>
                <input type="date" class="form-control" name="fecha_pedido" id="fecha_pedido"
                       aria-describedby="fecha_pedido_ayuda" required value="${transaccion.fechaPedido}">
                <div id="fecha_pedido_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la fecha del pedido.</div>
              </div>

              <div class="col-4 mb-3">
                <br>
                <button type="submit" class="btn btn-primary">Actualizar Transacción</button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</main>
<jsp:include page="templates/footer.jsp" />