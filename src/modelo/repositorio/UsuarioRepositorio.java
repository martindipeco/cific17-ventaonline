package modelo.repositorio;

import modelo.dominio.Usuario;
import modelo.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements IUsuarioRepositorio{

    @Override
    public List<Usuario> getListaDeUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("mail"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("num_tarjeta")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario buscarPorEmail(String mail) {
        Usuario usuario = null;
        String query = "SELECT * FROM usuarios WHERE mail = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mail);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getString("mail"),
                            rs.getString("password"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("num_tarjeta")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public void agregarUsuario(Usuario usuario) {
        String query = "INSERT INTO usuarios (mail, password, nombre, direccion, num_tarjeta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, usuario.getMail());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getDireccion());
            pstmt.setString(5, usuario.getNumTarjeta());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
