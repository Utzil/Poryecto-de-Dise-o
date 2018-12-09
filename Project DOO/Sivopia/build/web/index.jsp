<%@page import="POJO.User"%>
<%@page import="POJO.Candidate"%>
<%@page import="java.util.List"%>
<%@page import="DAO.CandidateDAO"%>

<%
    User userLogged = null;
    boolean isAlreadyVote = false;
    Object userObject = request.getSession().getAttribute("user");

    if (userObject != null) {
        userLogged = (User) userObject;
        isAlreadyVote = userLogged.isAlreadyVote();
    }

    CandidateDAO candidateDAO = new CandidateDAO();
    List<Candidate> listCandidates = candidateDAO.readAll();
    List<Candidate> listCandidatesHighestNumVotes = candidateDAO.highestNumVotes();
    int numTotalVotes = candidateDAO.getTotalVotes();

    Candidate candidateHighestNumVotes = null;

    if (listCandidatesHighestNumVotes.size() == 1) {
        candidateHighestNumVotes = listCandidatesHighestNumVotes.get(0);
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio - Sivopia</title>
        <link href="resources/css/struct.css" rel="stylesheet" type="text/css"/>
        <link href="resources/css/index.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <form name="frm" action="CandidateController" class="wrapper" method="post">
            <div class="mnu text-right">
                <%
                    if (userLogged != null) {
                %>
                <span class="mr-2 username"><%=userLogged.getUserName()%></span>
                <input class="ml-auto signout" type="submit" name="signOut" value="Cerrar sesion"/>
                <%
                } else {
                %>
                <a href="login.jsp" class="logIn">Iniciar Sesión</a>
                <%
                    }
                %>
            </div>
            <div class="info-candidates">
                <div class="candidates">
                    <h2 class="sub-title">Candidatos de la votación</h2>
                    <input type="hidden" name="candidateToVote" value="" />
                    <table>
                        <tr>
                            <th>Candidatos a la Mejor Película 2018</th>
                                <%
                                    if (userLogged != null) {
                                        if (isAlreadyVote) {
                                %>
                            <th>Número de votos</th>
                            <th>Porcentaje</th>
                                <%
                                } else {
                                %>
                            <th>Seleccione</th>
                                <%
                                        }
                                    }
                                %>
                        </tr>
                        <%
                            if (!listCandidates.isEmpty()) {
                                for (Candidate c : listCandidates) {
                                    String nameComplete = c.getFirstName() + " " + c.getMiddleName() + " " + c.getLastName() + " " + c.getMotherLastName();
                                    String numVotes = String.valueOf(c.getNumVotes());
                                    int percent = (c.getNumVotes() * 100) / numTotalVotes;
                                    String idCandidate = String.valueOf(c.getIdCandidate());
                        %>
                        <tr>
                            <td>
                                <%=nameComplete%>
                            </td>
                            <%
                                if (userLogged != null) {
                                    if (isAlreadyVote) {
                            %>
                            <td>
                                <%=numVotes%>
                            </td>
                            <td>
                                <%=percent%>%
                            </td>
                            <%
                            } else {
                            %>
                            <td>
                                <input 
                                    type="submit" 
                                    name="vote" 
                                    value="Votar por <%=c.getFirstName()%>"
                                    onclick="{
                                                document.frm.candidateToVote.value = <%=idCandidate%>;
                                                document.frm.submit();
                                            }"
                                    />
                            </td>      
                            <%
                                    }
                                }
                            %>  
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="2">No hay candidatos disponibles</td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </div>
                <%
                    if (userLogged != null) {
                        if (isAlreadyVote) {
                %>
                <div class="winner">
                    <h2 class="sub-title">Candidato con mayor votación</h2>
                    <div class="winner-wrapper">
                        <%
                            if (candidateHighestNumVotes != null) {
                                String nameComplete = candidateHighestNumVotes.getFirstName() + " "
                                        + candidateHighestNumVotes.getMiddleName() + " "
                                        + candidateHighestNumVotes.getLastName() + " "
                                        + candidateHighestNumVotes.getMotherLastName();
                        %>
                        <span class="candidate-name">
                            <%=nameComplete%>
                        </span>
                        <%
                        } else {
                        %>
                        <span class="candidates-names">
                            <%
                                if (!listCandidatesHighestNumVotes.isEmpty()) {
                                    for (Candidate c : listCandidatesHighestNumVotes) {
                                        String nameComplete = c.getFirstName() + " "
                                                + c.getMiddleName() + " "
                                                + c.getLastName() + " "
                                                + c.getMotherLastName();
                            %>
                            <span><%=nameComplete%></span>
                            <%
                                }
                            %>
                        </span>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </form>

        <script src="resources/js/index.js" type="text/javascript"></script>
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