package modelo.repositorio;

import modelo.dominio.Usuario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements IUsuarioRepositorio {

    private List<Usuario> listaDeUsuarios;

    public UsuarioRepositorio() {
        listaDeUsuarios = new ArrayList<>();
        agregarUsuariosIniciales();
    }

    private void agregarUsuariosIniciales() {
        listaDeUsuarios.add(new Usuario("juana@mail.com",
                "94289908a16a748e4ff0d375ddd8789de354f8e597e58a2c5852c7434e58374b",
                "Juana", "SiempreViva 321", "1234123412341234L"));  // pass is 123juana
        listaDeUsuarios.add(new Usuario("pedro@mail.com", "456pedro", "Pedro",
                "Principal 456", "5678567856785678L"));
        listaDeUsuarios.add(new Usuario("ana@mail.com", "789ana", "Ana",
                "De Tierra 987", "1234567890123456L"));
    }

    @Override
    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    @Override
    public Usuario buscarPorEmail(String mail) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getMail().equals(mail)) {
                return usuario;
            }
        }
        return null;
    }
}
