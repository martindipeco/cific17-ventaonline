package modelo.servicio;

import modelo.dominio.Usuario;
import modelo.repositorio.IUsuarioRepositorio;
import modelo.repositorio.UsuarioRepositorio;

public class UsuarioServicio {

    private IUsuarioRepositorio usuarioRepositorio;

    //cuando se inicia el servicio, se instancia el repo
    public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio)
    {
        this.usuarioRepositorio = new UsuarioRepositorio();
    }

    public Usuario buscarUsuario(String mail)
    {
        return usuarioRepositorio.buscarPorEmail(mail);
    }

    public Usuario logUsuario(String mail, String password)
    {
        Usuario user = usuarioRepositorio.buscarPorEmail(mail);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
