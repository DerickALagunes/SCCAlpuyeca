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
</style>

<main>
  <div class="container-fluid" style="padding-top:60px; text-align: center">
    <div class="row justify-content-center">
      <div class="col-12 col-md-5">

        <form method="post" action="transaccion" enctype="multipart/form-data">


          <div class="section-border">
            <div class="section-title">Registro de una transacción</div>
            <p class="disabled text-center text-body-secondary mb-3" style="color: dimgrey">Todos los campos marcados con <i class="text-danger">*</i> son obligatorios. Sin embargo, los datos pueden modificarse después.</p>
            <br>

            <!-- Nuevo Cliente -->
            <div class="row">
              <div class="section-title">Datos del Cliente</div>

              <div class="col-6 mb-3 elemento">
                <label for="gestor" class="form-label">Gestor</label>
                <input type="text" class="form-control" name="gestor" id="gestor" maxlength="255"
                       aria-describedby="gestor_ayuda">
                <div id="gestor_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre del gestor del cliente.</div>
              </div>

              <div class="col-6 mb-3 elemento">
                <label for="razon_social" class="form-label">Razón Social</label>
                <input type="text" class="form-control" name="razon_social" id="razon_social" maxlength="255"
                       aria-describedby="razon_social_ayuda">
                <div id="razon_social_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> la razón social del cliente.</div>
              </div>

            </div>


            <!-- Selección de vehículo -->
            <div class="row">
              <div class="section-title">Datos del Vehículo</div>

              <div class="col-6 mb-3 elemento">
                <label for="placa" class="form-label">Placa</label>
                <input type="text" class="form-control" name="placa" id="placa" maxlength="50"
                       aria-describedby="gestor_ayuda">
                <div id="placa_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> la placa del vehículo.</div>
              </div>

              <div class="col-6 mb-3 elemento">
                <label for="serie" class="form-label">Número de Serie</label>
                <input type="text" class="form-control" name="serie" id="serie" maxlength="255"
                       aria-describedby="serie_ayuda">
                <div id="serie_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de serie del vehículo.</div>
              </div>

            </div>

            <!-- Ingreso de información de la Verificación -->
            <div class="row">
              <div class="section-title">Datos de la Verificación</div>

              <div class="col-4 mb-3 elemento">
                <label for="materia" class="form-label">Materia<i class="text-danger">*</i></label>
                <select class="form-select" id="materia" name="materia" aria-describedby="materia_ayuda" required>
                  <option value="" disabled selected>Seleccione...</option>
                  <option value="HUMO">Humo</option>
                  <option value="MOTRIZ">Motriz</option>
                  <option value="ARRASTRE">Arrastre</option>
                  <option value="GASOLINA">Gasolina</option>
                </select>
                <div id="materia_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la materia de esta verficación.</div>
              </div>

              <div class="col-8 mb-3 elemento">
                <label for="verificentro" class="form-label">Verificentro</label>
                <input type="text" class="form-control" name="verificentro" id="verificentro" maxlength="255"
                       aria-describedby="verificentro_ayuda">
                <div id="verificentro_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre del verificentro.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="folio" class="form-label">Folio</label>
                <input type="text" class="form-control" name="folio" id="folio"
                       aria-describedby="folio_ayuda">
                <div id="folio_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el folio de esta verficación.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="fecha_folio" class="form-label">Fecha del Folio<i class="text-danger">*</i></label>
                <input type="date" class="form-control" name="fecha_folio" id="fecha_folio"
                       aria-describedby="fecha_folio_ayuda" required>
                <div id="fecha_folio_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la fecha del folio de esta verficación.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="precio" class="form-label">Precio</label>
                <input type="number" step="0.01" max="999999999999.99" min="0" class="form-control" name="precio" id="precio"
                       aria-describedby="precio_ayuda">
                <div id="precio_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el precio de esta verficación (solo numeros).</div>
              </div>

            </div>

            <!-- Ingreso de información de la Transaccion -->
            <div class="row">
              <div class="section-title">Datos de la Transacción</div>

                <div class="col-4 mb-3 elemento">
                  <label for="tipo_pago" class="form-label">Tipo de Pago<i class="text-danger">*</i></label>
                  <select class="form-select" id="tipo_pago" name="tipo_pago" aria-describedby="tipo_pago_ayuda" required>
                    <option value="" disabled selected>Seleccione...</option>
                    <option value="FACTURA">Factura</option>
                    <option value="EFECTIVO">Efectivo</option>
                    <option value="DEPOSITO">Deposito</option>
                    <option value="NULL">Otro</option>
                  </select>
                  <div id="tipo_pago_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> el tipo de pago.</div>
                </div>

                <div class="col-4 mb-3 elemento">
                  <label for="cuenta_deposito" class="form-label">Cuenta de deposito</label>
                  <input type="text" class="form-control" name="cuenta_deposito" id="cuenta_deposito"
                         aria-describedby="cuenta_deposito_ayuda">
                  <div id="cuenta_deposito_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> la cuenta de deposito.</div>
                </div>

                <div class="col-4 mb-3">
                  <label class="form-label">¿Transacción Pagada?<i class="text-danger">*</i></label>
                  <div class="form-check">
                    <input class="form-check-input" type="radio" name="estado_pago" id="pagada" value="PAGADA" required>
                    <label class="form-check-label" for="pagada">
                      Sí, está pagada
                    </label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="radio" name="estado_pago" id="no_pagada" value="NO_PAGADA">
                    <label class="form-check-label" for="no_pagada">
                      No, no está pagada
                    </label>
                  </div>
                </div>

              <div class="col-4 mb-3 elemento">
                <label for="numero_nota" class="form-label">Número de nota</label>
                <input type="text" class="form-control" name="numero_nota" id="numero_nota"
                       aria-describedby="numero_nota_ayuda" maxlength="50">
                <div id="numero_nota_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de nota (Si aplica).</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="numero_factura" class="form-label">Número de factura</label>
                <input type="text" class="form-control" name="numero_factura" id="numero_factura"
                       aria-describedby="numero_factura_ayuda" maxlength="50">
                <div id="numero_factura_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de factura (Si aplica).</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="numero_cotizacion" class="form-label">Número de cotización</label>
                <input type="text" class="form-control" name="numero_cotizacion" id="numero_cotizacion"
                       aria-describedby="numero_cotizacion_ayuda" maxlength="50">
                <div id="numero_cotizacion_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el número de cotización (Si aplica).</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="atiende_y_cobra" class="form-label">Atiende y Cobra</label>
                <input type="text" class="form-control" name="atiende_y_cobra" id="atiende_y_cobra"
                       aria-describedby="atiende_y_cobra_ayuda" maxlength="100">
                <div id="atiende_y_cobra_ayuda" class="form-text ayuda">Escribe aquí <i class="bi-arrow-up"></i> el nombre de quien atiende y cobra.</div>
              </div>

              <div class="col-4 mb-3 elemento">
                <label for="fecha_pedido" class="form-label">Fecha de Pedido<i class="text-danger">*</i></label>
                <input type="date" class="form-control" name="fecha_pedido" id="fecha_pedido"
                       aria-describedby="fecha_pedido_ayuda" required>
                <div id="fecha_pedido_ayuda" class="form-text ayuda">Selecciona <i class="bi-arrow-up"></i> la fecha del pedido.</div>
              </div>

              <div class="col-4 mb-3">
                <br>
                <button type="submit" class="btn btn-primary">Registrar Transacción</button>
              </div>
            </div>
          </div>
          <br>
        </form>
      </div>
    </div>
  </div>
</main>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    const fechaInput = document.getElementById('fecha_pedido');
    const fechaInput2 = document.getElementById('fecha_folio');
    const today = new Date().toISOString().split('T')[0]; // Obtiene la fecha en formato YYYY-MM-DD
    fechaInput.value = today;
    fechaInput2.value = today;
  });
</script>

<jsp:include page="templates/footer.jsp" />
