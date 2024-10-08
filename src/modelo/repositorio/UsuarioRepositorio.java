package modelo.repositorio;

import modelo.dominio.Usuario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {

    private List<Usuario> listaDeUsuarios;

    public UsuarioRepositorio()
    {
        Usuario usuario1 = new Usuario("juana@mail.com",
                "94289908a16a748e4ff0d375ddd8789de354f8e597e58a2c5852c7434e58374b", "Juana",
                "SiempreViva 321", "1234123412341234L"); //pass des-hasheado es 123juana
        Usuario usuario2 = new Usuario("pedro@mail.com", "456pedro", "Pedro",
                "Principal 456", "5678567856785678L");
        Usuario usuario3 = new Usuario("ana@mail.com", "789ana", "Ana", "De Tierra 987",
                "1234567890123456L");

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
