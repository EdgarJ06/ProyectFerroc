package Controller;

import Model.Cargo;
import Model.CargoDAO;
import Model.Usuario;
import Model.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eroda
 */
//Se define la direcion del srvet para poder manipular mejor las acciones
@WebServlet(name = "srvUsuario", urlPatterns = {"/srvUsuario"})
public class srvUsuario extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        //se declaran las acciones necesarias para poder funcionar el login
        try {
            if (accion != null) {
                switch (accion) {
                    case "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        CerrarSession(request, response);
                    case "listarUsuarios":
                        ListarUsuarios(request, response);
                        break;
                    case "nuevo":
                        PresentarFormulario(request, response);
                        break;
                    case "registrar":
                        RegistrarUsuario(request, response);
                        break;
                    default:
                        response.sendRedirect("Login.jsp");
                }
            } else {
                response.sendRedirect("Login.jsp");
            }
        } catch (Exception ex) {
            try {
                HttpSession sesion = request.getSession();
                this.getServletConfig().getServletContext().getRequestDispatcher("/Mensaje.jsp").forward(request, response);
                sesion.setAttribute("msaje", ex);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
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

    //metodo para verificar el rol que contiene el usuario
    private void verificar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession sesion;
        UsuarioDAO udao;
        Usuario usuario;
        usuario = this.obtenerUsuario(request);
        udao = new UsuarioDAO();
        usuario = udao.identificar(usuario);
        if (usuario != null && usuario.getCargo().getNombreCargo().equals("ADMINISTRADOR")) {
            sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            sesion.setAttribute("msaje", "BIENVENIDOS AL SISTEMA");
            this.getServletConfig().getServletContext().getRequestDispatcher("/View/index.jsp").forward(request, response);

        } else if (usuario != null && usuario.getCargo().getNombreCargo().equals("VENDEDOR")) {
            sesion = request.getSession();
            sesion.setAttribute("vendedor", usuario);
            sesion.setAttribute("msaje", "BIENVENIDOS AL SISTEMA");
            this.getServletConfig().getServletContext().getRequestDispatcher("/View/ViewVendedor.jsp").forward(request, response);

        } else {
            request.setAttribute("msaje", "CREDENCIALES INCORRECTAS");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    //metodo para cerrar session si el usuario ingresado es incorrecto
    private void CerrarSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession sesion = request.getSession();
        sesion.setAttribute("usuario", null);
        sesion.invalidate();
        response.sendRedirect("Login.jsp");
    }

    //metodo necesario para obtener el usuario ingresado desde la vista del Login
    private Usuario obtenerUsuario(HttpServletRequest request) {
        Usuario u = new Usuario();
        u.setNombreUsuario(request.getParameter("txtUsu"));
        u.setPassword(request.getParameter("txtPass"));
        return u;
    }

    private void ListarUsuarios(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usus = null;
        try {
            usus = dao.listarUsuarios();
            request.setAttribute("usuarios", usus);

        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
        } finally {
            dao = null;
        }
        try {
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/View/Usuarios.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se puedo realizar la petición" + ex.getMessage());
        }
    }

    private void PresentarFormulario(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.cargarCargos(request);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/View/NuevoUsuario.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo cargar la vista");
        }
    }

    //METODO PARA PODER LISTAR LOS CARGOS QUE TENEMOS EN LA BASE DE DATOS
    private void cargarCargos(HttpServletRequest request) {
        CargoDAO dao = new CargoDAO();
        List<Cargo> car = null;
        try {
            car = dao.listarCargos();
            request.setAttribute("cargos", car);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo cargar los cargos :( " + e.getMessage());
        } 
    }

    //METODO PARA PODER REGISTRAR UN NUEVO USUARIO DESDE LA WEB
    private void RegistrarUsuario(HttpServletRequest request, HttpServletResponse response) {

        UsuarioDAO daoUsu;
        Usuario usu = null;
        Cargo carg;
        if (request.getParameter("txtNombre") != null
                && request.getParameter("txtClave") != null
                && request.getParameter("cboCargo") != null) {
            usu = new Usuario();
            usu.setNombreUsuario(request.getParameter("txtNombre"));
            usu.setPassword(request.getParameter("txtClave"));
            carg = new Cargo();
            carg.setId_cargo(Integer.parseInt(request.getParameter("cboCargo")));
            usu.setCargo(carg);
            if (request.getParameter("chkEstado") != null) {
                usu.setEstado(true);
            } else {
                usu.setEstado(false);
            }
            daoUsu = new UsuarioDAO();

            try {
                daoUsu = new UsuarioDAO();
                daoUsu.registrarUsuarios(usu);
                response.sendRedirect("srvUsuario?accion=listarUsuarios");
            } catch (Exception e) {
                request.setAttribute("msje",
                        "No se pudo registrar el usuario" + e.getMessage());
                request.setAttribute("usuario", usu);
                this.PresentarFormulario(request, response);
                //this.ListarUsuarios(request, response);
            }
        }
    }
}

///no registra el nuevo usuario
