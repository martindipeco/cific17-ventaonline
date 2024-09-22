package modelo.repositorio;

import modelo.dominio.Usuario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {

    private List<Usuario> listaDeUsuarios;

    public UsuarioRepositorio()
    {
        Usuario usuario1 = new Usuario("juana@mail.com", "123juana");
        Usuario usuario2 = new Usuario("pedro@mail.com", "456pedro");
        Usuario usuario3 = new Usuario("ana@mail.com", "789ana");

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        this.listaDeUsuarios = listaUsuarios;
    }

    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }
}
