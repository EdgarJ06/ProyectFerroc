package configBD;

/**
 *
 * @author eroda
 */
public class ParametrosConexion {
    
    private String nombre;
    private Object valor;
    private boolean entrada;
    private int tipo;

    public ParametrosConexion() {
        this.entrada = true;
    }

    public ParametrosConexion(String nombre, Object valor, boolean entrada, int tipo) {
        this.nombre = nombre;
        this.valor = valor;
        this.entrada = entrada;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
