<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ups! - Sivopia</title>
        <link href="resources/css/struct.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/ups.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="content-error">
            <h1 class="title">Ups!</h1>
            <h2 class="sub-title">Al parecer hubo un error...</h2>
            <div class="error">
                <%
                    Object error = request.getAttribute("error");
                    String messageError = "";

                    if (error != null) {
                        messageError = (String) error;
                %>
                <span>Detalle del error:</span>
                <p class="error-message">
                    <%= messageError%>
                </p>
                <%
                    }
                %>
            </div>
            <a href="index.jsp">Regresar a la página de inicio</a>
        </div>
        <script src="resources/js/ups.js" type="text/javascript"></script>
    </body>
</html>
