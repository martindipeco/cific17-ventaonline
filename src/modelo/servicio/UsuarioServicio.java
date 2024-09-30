package modelo.servicio;

import modelo.dominio.Usuario;
import modelo.repositorio.UsuarioRepositorio;

public class UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    //cuando se inicia el servicio, se instancia el repo
    public UsuarioServicio()
    {
        this.usuarioRepositorio = new UsuarioRepositorio();
    }

    public Usuario buscarUsuario(String mail, String password)
    {
        for(Usuario user : usuarioRepositorio.getListaDeUsuarios())
        {
            if(user.getMail().equals(mail) && user.getPassword().equals(password))
            {
                return user;
            }
        }
        return null;
    }
}
