<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>SIVEMOR</title>
    <meta name="description" content="Sistema de control de cobros Alpuyeca">
    <meta name="author" content="Derick Axel Lagunes Ramirez & Nancy Victoria Guadarrama Alvarez">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/icon/font/fonts/bootstrap-icons.woff" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/icon/font/fonts/bootstrap-icons.woff2" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/icon/font/bootstrap-icons.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/datatables.min.css" />
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/logo.png" type="image/png">
    <style>
        .section-border {
            border: 1px solid #ced4da; /* Color del borde */
            border-radius: 5px; /* Esquinas redondeadas */
            padding: 15px; /* Espaciado interno */
            margin-bottom: 20px; /* Espaciado inferior */
        }
        .section-title {
            font-size: 1.25rem; /* Tamaño de fuente del título */
            font-weight: bold; /* Texto en negrita */
            margin-bottom: 15px; /* Espaciado inferior del título */
            text-align: center;
        }
        .subsection-title {
            font-weight: bold; /* Texto en negrita */
            margin-bottom: 5px; /* Espaciado inferior del título */
            text-align: center;
        }
        /* Chrome, Safari, Edge, Opera */
        input::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        /* Firefox */
        input[type=number] {
            -moz-appearance: textfield;
        }

        /* Estilo base */
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* Asegura que el cuerpo cubra toda la altura de la pantalla */
        }

        /* Estilo del contenido principal */
        main {
            flex: 1; /* Expande el contenido principal para ocupar el espacio disponible */
        }

        /* Estilo del footer */
        footer {
            position: fixed; /* Fija el footer en la pantalla */
            bottom: 0; /* Ubica el footer al fondo de la pantalla */
            left: 0;
            width: 100%; /* Asegura que ocupe todo el ancho */
            background-color: #333; /* Color de fondo */
            color: white; /* Color del texto */
            text-align: center; /* Centra el texto */
            padding: 1px 0; /* Espaciado interno */
            box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.2); /* Sombra opcional */
            background-color: RGB(33, 37, 41);
        }
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
                Sistema de Control de Cobros Alpuyeca
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/index.jsp">Inicio</a>
                </div>
            </div>
        </div>
    </nav>
</header>
