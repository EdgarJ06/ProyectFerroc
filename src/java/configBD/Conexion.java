package configBD;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author eroda
 */
public class Conexion {
    
    private Connection conexion;
    private boolean TransaccionIniciada;
    
    protected Connection getConexion(){
        return conexion;
    }
    
    protected void conectar(boolean wTransaccion) throws Exception{
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setServerName("localhost");
        ds.setPort(3306);
        ds.setDatabaseName("ferrocon");
        conexion = ds.getConnection("root", "");
        
        if (wTransaccion == true) {
            this.conexion.setAutoCommit(false);
            this.TransaccionIniciada = true;
        } else {
            this.conexion.setAutoCommit(true);
            this.TransaccionIniciada = false;
        }
    }
    
    protected void cerrar(boolean wEstado) throws Exception{
        if (this.conexion != null) {
            if (this.TransaccionIniciada == true) {
                try {
                    if (wEstado == true) {
                        this.conexion.commit();
                    }else{
                        this.conexion.rollback();
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                this.conexion.close();
            } catch (Exception e) {
            }
        }
        this.conexion = null;
    }
    
    protected void ejecutarOrden(String wSQL)throws Exception{
        Statement st;
        
        if (this.conexion != null) {
            st = this.conexion.createStatement();
            st.executeQuery(wSQL);
        }
    }
    
    protected ResultSet ejecutarOrdenDatos(String wSQL)throws Exception{
        Statement st;
        ResultSet rs = null;
        
        if (this.conexion != null) {
            st = this.conexion.createStatement();
            rs = st.executeQuery(wSQL);
        }
        
        return rs;
    }
    
    protected Object ejecutarProcedimiento(String wProcedimiento, List<ParametrosConexion> wParametros) throws Exception{
        CallableStatement cs;
        Object Valor = null;
        String parNombre = "";
        
        try {
            cs = this.getConexion().prepareCall(wProcedimiento);
            if (wParametros != null) {
                for (ParametrosConexion par : wParametros) {
                    if (par.isEntrada() == true) {
                        cs.setObject(par.getNombre(), par.getValor());
                    }else{
                        parNombre = par.getNombre();
                        cs.registerOutParameter(par.getNombre(), par.getTipo());
                    }
                }
            }
            cs.executeUpdate();
            if (parNombre.length() > 0) {
                Valor = cs.getObject(parNombre);
            }
        } catch (Exception e) {
            throw e;
        }finally{
            cs = null;
        }
        
        return Valor;
    }
    
    protected ResultSet ejecutarProcedimientoDatos(String wProcedimiento, List<ParametrosConexion> wParametros) throws Exception{
        CallableStatement cs;
        ResultSet rs = null;
        
        try {
            cs = this.getConexion().prepareCall(wProcedimiento);
            if (wParametros != null) {
                for (ParametrosConexion par : wParametros) {
                    if (par.isEntrada() == true) {
                        cs.setObject(par.getNombre(), par.getValor());
                    }else{
                    }
                }
            }
            rs = cs.executeQuery();
        } catch (Exception e) {
            throw e;
        }finally{
            cs = null;
        }
        return rs;
    }
    
    
    public Connection Conexion2(){
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/ferrocon",
                    "root",
                    "");
            System.out.println("Base de datos conectada");
        } catch (Exception e) {
            System.out.println("Error de Conexion: "+e);
        }
        return cn;
    }
    
}
