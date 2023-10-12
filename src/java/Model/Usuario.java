package Model;

/**
 *
 * @author eroda
 */
public class Usuario {

    private int id_usuario;
    private String nombreUsuario;
    private String password;
    private boolean estado;
    private Cargo cargo;

    public Usuario() {

    }

    public Usuario(int id_usuario, String nombreUsuario, String password, boolean estado, Cargo cargo) {
        this.id_usuario = id_usuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.estado = estado;
        this.cargo = cargo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
