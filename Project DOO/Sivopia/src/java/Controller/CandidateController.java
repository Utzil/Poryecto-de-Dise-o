package Controller;

import DAO.CandidateDAO;
import DAO.UserDAO;
import POJO.Candidate;
import POJO.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author Uziel
 * 
 */
public class CandidateController extends HttpServlet {

    User userLogged = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // obtener usuario logeado
            Object userObj = request.getSession().getAttribute("user");
            if (userObj != null) {
                userLogged = (User) userObj;
            }
            
            // votar por candidato
            String candidateToVote = request.getParameter("candidateToVote");

            // validamos el click y que haya un usuario logeado
            if (!candidateToVote.equals("") && userLogged != null) {
                // click en votar por candidato
                UserDAO userDAO = new UserDAO();
                CandidateDAO candidateDAO = new CandidateDAO();
                int idCandidate = Integer.parseInt(candidateToVote);
                Candidate candidate = candidateDAO.read(idCandidate);

                if (candidate != null) {

                    candidate.setNumVotes(candidate.getNumVotes() + 1);
                    candidateDAO.setCandidate(candidate);

                    userLogged.setAlreadyVote(true);
                    userDAO.setUser(userLogged);

                    if (candidateDAO.update()) {
                        if (userDAO.update()) {
                            response.sendRedirect("index.jsp");
                            return;
                        } else {
                            request.setAttribute("mensaje", "No se pudo realizar la votación, intente nuevamente");
                        }
                    } else {
                        request.setAttribute("mensaje", "No se pudo realizar la votación, intente nuevamente");
                    }
                } else {
                    request.setAttribute("mensaje", "No se pudo realizar la votación, intente nuevamente");
                }
            }

            // cerrar sesión
            if (request.getParameter("signOut") != null) {
                request.getSession().invalidate();
                response.sendRedirect("login.jsp");
                return;
            }

        } catch (Exception ex) {
            // redirecciona a pagina generica para mostrar error
            request.setAttribute("error", "Error del servidor " + ex.getMessage());
            request.getRequestDispatcher("ups.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
