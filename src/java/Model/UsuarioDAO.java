package Model;

import configBD.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eroda
 */
public class UsuarioDAO extends Conexion {

    //metodo para poder identificar el usuario al momento de loguearse
    public Usuario identificar(Usuario user) throws Exception {
        Usuario u = null;
        ResultSet rs = null;
        Statement st = null;
        Connection cn = null;
        Conexion c;

        //consulta para poder obtener el usuario y la contraseña en la base de datos
        String sql = "SELECT U.IDUSUARIO, C.NOMBRE_CARGO FROM usuario U "
                + "INNER JOIN cargo C ON U.IDUSUARIO = C.IDCARGO WHERE U.ESTADO = 1 "
                + "AND U.NOMBRE_USUARIO = '" + user.getNombreUsuario() + "'"
                + "AND U.PASSWORD = '" + user.getPassword() + "'";
        c = new Conexion();
        try {
            //se inicia el puerto para la conexion
            /*cn = c.Conexion2();
            st = cn.createStatement();
            rs = st.executeQuery(sql);*/
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);

            //se obtienen los registros necesarios al momento de hacer conexion con la base de datos 
            while (rs.next() == true) {
                //se crea una nueva instancia en la clase usuario(en donde se almacenan los datos recibidos)
                u = new Usuario();
                u.setId_usuario(rs.getInt("IDUSUARIO"));
                u.setNombreUsuario(user.getNombreUsuario());
                u.setCargo(new Cargo());
                u.getCargo().setNombreCargo(rs.getString("NOMBRE_CARGO"));
                u.setEstado(true);
            }
            rs.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {

            //se cierra las conexiones a la base de datos, sí en caso dados los parametros enviados son icorrectos
            if (rs != null && rs.isClosed() == false) {
                rs.close();
            }
            rs = null;

            if (st != null && st.isClosed() == false) {
                rs.close();
            }
            st = null;

            if (cn != null && cn.isClosed() == false) {
                cn.close();
            }
            cn = null;
        }
        return u;
    }

    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios;
        Usuario usu;
        ResultSet rs = null;
        String sql = "SELECT U.IDUSUARIO, U.NOMBRE_USUARIO, U.PASSWORD, U.ESTADO, C.NOMBRE_CARGO "
                + "FROM usuario U "
                + "INNER JOIN cargo C "
                + "ON C.IDCARGO = U.ID_CARGO;";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuarios = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setId_usuario(rs.getInt("IDUSUARIO"));
                usu.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                usu.setPassword(rs.getString("PASSWORD"));
                usu.setEstado(rs.getBoolean("ESTADO"));
                usu.setCargo(new Cargo());
                usu.getCargo().setNombreCargo(rs.getString("NOMBRE_CARGO"));
                usuarios.add(usu);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return usuarios;
    }

    //METODO PARA REGISTRAR UN NUEVO USUARIO
    public void registrarUsuarios(Usuario usu) throws Exception {
        String sql;
        sql = "INSERT INTO usuario (NOMBRE_USUARIO, PASSWORD, ESTADO, ID_CARGO)"
                + "VALUES ('"+usu.getNombreUsuario()+"','"
                + usu.getPassword()+"', "
                + (usu.isEstado() == true ? "1": "0")
                +", "+usu.getCargo().getId_cargo()+")";
        
        /*sql = "INSERT INTO usuario (NOMBRE_USUARIO, PASSWORD, ESTADO, ID_CARGO)"
                + "VALUES ("+usu.getNombreUsuario()+
                ", "+usu.getPassword()+
                ", "+1+
                ", "+usu.getCargo().getId_cargo()+")";*/
        
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(true);
        }
        
        catch (Exception e) {
            this.cerrar(false);
            throw e;
        }
    }

}
