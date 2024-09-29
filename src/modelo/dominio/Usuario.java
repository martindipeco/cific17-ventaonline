package modelo.dominio;

import java.util.List;

public class Usuario {

    private String mail;
    private String password;

    //constructor para visitantes
    public Usuario() {}

    public Usuario(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return mail;
    }
}
