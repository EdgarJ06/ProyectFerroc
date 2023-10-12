package configBD;

/**
 *
 * @author eroda
 */
public class PruebaConexion {


    public static void main(String[] args) {
        Conexion c = new Conexion();
        if (c.Conexion2()!= null) {
            System.out.println("Estas conectado a la base de datos!!");
        } else {
            System.out.println("No hubo conexion");
        }
        /*
        Conexion c = new Conexion();
        if (!c.conectar(true)) {
        } else {
            System.out.println("Conexion exitosa");
        }
*/
    }
}
