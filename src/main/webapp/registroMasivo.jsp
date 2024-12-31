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

   .zoom {
     transition: transform 0.3s ease;
   }
  .zoom.active {
    transform: scale(2); /* Ajusta el nivel de zoom */
  }
</style>

<main>
  <div class="container-fluid" style="padding-top:60px; text-align: center">
    <div class="row justify-content-center">
      <div class="col-12 col-md-5">

        <form method="post" action="excel" enctype="multipart/form-data">

          <div class="section-border">
            <div class="section-title">Registro masivo con Excel</div>
            <p class="disabled text-center text-body-secondary mb-3" style="color: dimgrey">
              <span class="fs-5 fw-bolder">Instrucciones: </span>
              Sube un archivo excel con la siguiente estructura:
            </p>
            <img  src="assets/img/ejemploExcel.png" alt="Caracteristicas del archivo excel para que funcione la carga masiva."
                  class="img-fluid img-thumbnail zoom"
                  id="zoomImage" style="cursor: pointer;"/>
            <br><br>
            <p>Descargue una plantilla de ejemplo con el siguiente botón:</p>
            <a href="assets/files/ejemplo.xlsx" class="btn btn-success" download>
              ejemplo.xls <i class="bi bi-file-earmark-excel"></i>
            </a>
            <br><br><br>

            <!-- Boton de carga masiva -->
            <div class="row">
              <div class="section-title">Carga masiva</div>
              <p>Si esta listo para la carga masiva utilice el siguiente botón para cargar su archivo y posteriormente de click en "Cargar" (Recuerda que este proceso podria tardar un tiempo)</p>
              <br><br>

              <div class="col-6 mb-3 elemento">
                <label for="archivo" class="form-label">Archivo Excel</label>
                <input type="file" class="form-control custom-file-button" name="archivo" id="archivo"
                       aria-describedby="archivo_ayuda" accept=".xls,.xlsx">
                <div id="archivo_ayuda" class="form-text ayuda">Carga aqui <i class="bi-arrow-up"></i> el archivo .xlsx con la información.</div>
              </div>

              <div class="col-6 mb-3 elemento">
                <label for="submit" class="form-label">Comenzar la carga</label>
                <br>
                <button aria-describedby="submit_ayuda" id="submit" name="submit" type="submit" class="btn btn-primary">Cargar <i class="bi bi-file-earmark-excel"></i></button>
                <div id="submit_ayuda" class="form-text ayuda">Da click aqui <i class="bi-arrow-up"></i> cuando estes listo para iniciar el proceso.</div>
              </div>

            </div>
          </div>

        </form>
        <br><br><br><br>
      </div>
    </div>
  </div>
</main>

<script>
  document.getElementById('submit').addEventListener('click', function (event) {
    const archivoInput = document.getElementById('archivo');
    if (!archivoInput.value) {
      event.preventDefault();
      alert('Por favor, selecciona un archivo antes de cargar.');
    }
  });
</script>

<script>
  const form = document.querySelector('form');
  form.addEventListener('submit', function () {
    document.getElementById('submit').innerHTML = 'Cargando... <span class="spinner-border spinner-border-sm"></span>';
    document.getElementById('submit').disabled = true;
  });
</script>

<script>
  const img = document.getElementById('zoomImage');
  img.addEventListener('click', () => {
    img.classList.toggle('active');
  });
</script>
<jsp:include page="templates/footer.jsp" />
