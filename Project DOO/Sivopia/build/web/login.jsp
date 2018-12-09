<%@page import="POJO.User"%>
<%@page import="java.net.HttpCookie"%>

<%
    Object userObject = request.getSession().getAttribute("user");

    if (userObject != null) {
        response.sendRedirect("index.jsp");
        return;
    }

    Cookie cookies[] = null;
    Cookie userCookie = null;
    String userRemember = "";
    cookies = request.getCookies();

    if (cookies.length > 0) {
        for (Cookie cook : cookies) {
            if (cook.getName().equals("userRemember")) {
                userCookie = cook;
                break;
            }
        }
    }

    if (userCookie != null) {
        userRemember = (String) userCookie.getValue();
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión - Sivopia</title>
        <link href="resources/css/struct.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/login.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="wrapper">
            <form action="UserController" method="post" id="frm" class="frm-account">
                <div class="options-account">
                    <a id="openLogin" href="#" class="op-account op-account-active">
                        iniciar sesión
                    </a>
                    <a id="openSignup" href="#" class="op-account">
                        registrarse
                    </a>
                </div>
                <div class="frm-account-wrapper">
                    <div class="login">
                        <div class="login-wrapper">
                            <img src="resources/images/user-default.png" alt="login" width="80" />
                            <span class="text-center">inicia sesión para <b class="vote">votar</b> ahora</span>
                            <%
                                if (userCookie != null) {
                            %>
                            <input 
                                name="loginUser" 
                                type="text" 
                                value="<%=userRemember%>" 
                                placeholder="nombre de usuario" /> 
                            <input name="loginPassword" type="password" value="" placeholder="contraseña" />
                            <div class="checkbox">
                                <input name="loginUserRemember" type="checkbox" checked value="yes" />
                                <span>recordar usuario</span>
                            </div>
                            <%
                            } else {
                            %>
                            <input name="loginUser" type="text" value="" placeholder="nombre de usuario" /> 
                            <input name="loginPassword" type="password" value="" placeholder="contraseña" />
                            <div class="checkbox">
                                <input name="loginUserRemember" type="checkbox" value="yes" />
                                <span>recordar usuario</span>
                            </div>
                            <%
                                }
                            %>
                            <input name="loginLogin" type="submit" value="iniciar sesión" />
                        </div>
                    </div>
                    <div class="signup d-none">
                        <div class="signup-wrapper">
                            <span class="sub-title">registrarse</span>
                            <input name="signupUser" type="text" value="" placeholder="nombre de usuario" /> 
                            <input name="signupPassword" type="password" value="" placeholder="contraseña" />
                            <input name="signupConfirmPassword" type="password" value="" placeholder="confirme la contraseña" />
                            <input name="signupSignup" type="submit" value="registrarse" />
                        </div>
                    </div>
            </form>
        </div>
        <script src="resources/js/login.js" type="text/javascript"></script>
        <%
            Object mensaje = request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
        <script type="text/javascript">
            window.onload = alert("<%= mensaje%>");
        </script> 
        <%
            }
        %>
    </body>
</html>
