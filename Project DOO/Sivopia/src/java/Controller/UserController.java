package Controller;

import DAO.UserDAO;
import POJO.User;
import Tools.Encrypted;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserController extends HttpServlet {

    HttpSession session = null;
    UserDAO userDAO = null;
    User u = null;
    

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
            // catch a proposito -----
            // si se descomenta comentar el String remember despues del if de abajo
            /*
            String remember = request.getParameter("loginUserRemember");
            
            if (!remember.isEmpty())
                remember.isEmpty();
             */
            
            // clase para encriptar y desencriptar
            Encrypted sec = new Encrypted();

            if (request.getParameter("loginLogin") != null) {
                // click en login
                String userName = request.getParameter("loginUser");
                String password = request.getParameter("loginPassword");
                String remember = request.getParameter("loginUserRemember");

                // validamos que no esten vacias las cajas de texto
                if (!userName.isEmpty() && !password.isEmpty()) {

                    userDAO = new UserDAO();
                    u = userDAO.read(userName);

                    if (u != null) {
                        // desencriptamos la contraseña
                        String passwordDesencrypted = sec.decrypt(u.getPassword());
                        if (passwordDesencrypted.equals(password)) {
                            
                            // guardamos el usuario en la session
                            session = request.getSession(true);
                            session.setAttribute("user", u);

                            if (remember != null) {
                                // creamos cookie para recordar usuario
                                Cookie userCookie = new Cookie("userRemember", u.getUserName());
                                userCookie.setMaxAge(60 * 60 * 720); // 720 horas
                                response.addCookie(userCookie);

                            } else {
                                // si existe la cookie la eliminamos para dejar de recordar usuario
                                Cookie userCookie = getCookie("userRemember", request);

                                if (userCookie != null) {
                                    userCookie.setMaxAge(0);
                                    response.addCookie(userCookie);

                                }
                            }
                            response.sendRedirect("index.jsp");
                            return;
                        } else {
                            request.setAttribute("mensaje", "Usuario y/o contraseña incorrectos");
                        }
                    } else {
                        request.setAttribute("mensaje", "Usuario y/o contraseña incorrectos");
                    }
                } else {
                    request.setAttribute("mensaje", "Hay campos vacios");
                }
            } else {
                // click en registrarse
                String userName = request.getParameter("signupUser");
                String password = request.getParameter("signupPassword");
                String confirmPassword = request.getParameter("signupConfirmPassword");

                // validamos que no esten vacias las cajas de texto
                if (!userName.isEmpty()
                        && !password.isEmpty()
                        && !confirmPassword.isEmpty()) {
                    
                    // validamos que las contraseñas coincidan
                    if (password.equals(confirmPassword)) {

                        userDAO = new UserDAO();
                        u = userDAO.read(userName);

                        if (u == null) {
                            u = new User();
                            u.setUserName(userName);
                            // encriptamos la contraseña
                            u.setPassword(sec.encrypt(password));
                            u.setAlreadyVote(false);
                            userDAO.setUser(u);
                            if (userDAO.create()) {
                                request.setAttribute("mensaje", "Usuario creado correctamente");
                            } else {
                                request.setAttribute("mensaje", "Hubo un error al crear el usuario");
                            }
                        } else {
                            request.setAttribute("mensaje", "Ya existe un usuario con el mismo nombre");
                        }
                    } else {
                        request.setAttribute("mensaje", "Las contraseñas no coinciden");
                    }
                } else {
                    request.setAttribute("mensaje", "Hay campos vacios");
                }
            }
        } catch (Exception ex) {
            // redirecciona a pagina generica para mostrar error
            request.setAttribute("error", "Error del servidor " + ex.getMessage());
            request.getRequestDispatcher("ups.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
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

    public Cookie getCookie(String key, HttpServletRequest request) {
        Cookie cook = null;

        Cookie cookies[] = request.getCookies();
        if (cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cook = cookie;
                    break;
                }
            }
        }

        return cook;
    }
}
