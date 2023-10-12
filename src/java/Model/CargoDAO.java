package Model;

import configBD.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO extends Conexion {

    public List<Cargo> listarCargos() throws Exception {
        List<Cargo> cargos;
        Cargo car;
        ResultSet rs = null;
        String sql = "SELECT C.IDCARGO, C.NOMBRE_CARGO, C.ESTADO FROM CARGO C "
                + "ORDER BY C.IDCARGO";

        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            cargos = new ArrayList<>();
            while (rs.next() == true) {
                car = new Cargo();
                car.setId_cargo(rs.getInt("IDCARGO"));
                car.setNombreCargo(rs.getString("NOMBRE_CARGO"));
                car.setEstado(rs.getBoolean("ESTADO"));
                cargos.add(car);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }
        return cargos;
    }
}
