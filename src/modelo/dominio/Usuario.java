package modelo.dominio;

public class Usuario {

    private String mail;
    private String password;

    private String nombre;
    private String direccion;
    private String numTarjeta;

    //constructor para visitantes
    public Usuario() {}

    public Usuario(String mail, String password, String nombre, String direccion, String numTarjeta) {
        this.mail = mail;
        this.password = password;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numTarjeta = numTarjeta;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return mail;
    }
}
