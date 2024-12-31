<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="templates/header.jsp" />
<style>
    .responsive-img{
        max-width: 100%;
        height: auto;
    }
</style>
<main class="d-flex justify-content-center align-items-center vh-100 bg-light pb-5">
    <div class="container-fluid text-center">
        <div class="row">
            <div class="d-none d-sm-block offset-md-1 col-md-4 align-content-center">
                <h1>Bienvenido al Sistema de Control de Cobros Alpuyeca</h1><br/>
                <img class="img-fluid" src="assets/img/logo.png" alt="logo">
                <p class="lead">Aplicación para el control de cobros</p>
            </div>
    <c:if test="${not empty sesion}">
    <div class="col-sm-12 col-md-6 align-content-center">
        <div class="row">
            <div class="col-6">
                <h1 class="h3 mb-3 font-weight-normal">Usuario: ${sesion.nombre_usuario}</h1>
            </div>
            <div class="col-6">
                <a class="btn btn-danger" href="login"><i class="bi bi-door-open-fill"></i> Cerrar sesión</a>
            </div>
        </div>

        <div class="row">
        <c:forEach items="${operaciones}" var="o">
            <div class="col-4">
                <a href="${o.operacion}">
                <div class="card shadow-sm">
                    <img class="bd-placeholder-img card-img-top responsive-img"
                         src="${o.media}"
                         alt="Imagen representativa"
                    />

                    <div class="card-body">
                        <p class="text-break"><strong>${o.nombre}</strong></p>
                    </div>
                </div>
                </a>
            </div>
        </c:forEach>
        </div>
    </div>
</c:if>

            <c:if test="${empty sesion}">
            <div class="col-sm-12 col-md-6 align-content-center">
                <form class="form-signin" action="login" method="post">
                    <img class="mb-4" src="assets/img/login.png" alt="login" width="350" height="200">
                    <h1 class="h3 mb-3 font-weight-normal">Inicia sesión:</h1>
                    <label for="inputEmail" class="sr-only">Usuario:</label>
                    <input type="text" id="inputEmail" class="form-control" placeholder="Nombre de usuario" required="" autofocus="" name="email">
                    <label for="inputPassword" class="sr-only">Contraseña:</label>
                    <input type="password" id="inputPassword" class="form-control" placeholder="Contraseña" required="" name="pass">
                    <br/>
                    <c:if test="${not empty mensaje}">
                        <div class="offset-3 col-6 alert alert-danger alert-dismissible fade show" role="alert">
                            <strong><i class="bi bi-exclamation-triangle-fill"></i> Intenta de nuevo <i class="bi bi-arrow-up"></i></strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <!-- <a class="link-info">¿Has olvidado tu contraseña?</a><br><br> -->
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Iniciar sesión</button>
                </form>
            </div>
</c:if>
        </div>

    </div>
</main>

<jsp:include page="templates/footer.jsp" />