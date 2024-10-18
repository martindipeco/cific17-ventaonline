package modelo.repositorio;

import modelo.dominio.Usuario;

import java.util.List;

public interface IUsuarioRepositorio {
    List<Usuario> getListaDeUsuarios();
    Usuario buscarPorEmail(String mail);
}
