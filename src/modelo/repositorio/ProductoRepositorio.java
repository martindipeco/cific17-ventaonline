package modelo.repositorio;

import modelo.dominio.EnumCategoria;
import modelo.dominio.Producto;
import modelo.dominio.Usuario;
import modelo.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio implements IProductoRepositorio {
    @Override
    public List<Producto> getListaDeProductos() {
        List<Producto> listaDeProductos = new ArrayList<>();
        String query = "SELECT * FROM productos";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("codigo_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getString("subcategoria"),
                        rs.getFloat("precio")
                );
                listaDeProductos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDeProductos;
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        Producto producto = null;
        String query = "SELECT * FROM productos WHERE codigo_producto = ?";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(codigo));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(
                            rs.getInt("codigo_producto"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getString("subcategoria"),
                            rs.getFloat("precio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {

        List<Producto> listaDeProductos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE LOWER(nombre) LIKE LOWER('%?%')"; //COLLATE = case insensitive

        try (Connection conn = DatabaseUtil.getConnection();

             PreparedStatement pstmt = conn.prepareStatement(query)) {

             pstmt.setString(1, nombre);

            try(ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next()) {
                    Producto producto = new Producto(
                            rs.getInt("codigo_producto"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getString("subcategoria"),
                            rs.getFloat("precio")
                    );
                    listaDeProductos.add(producto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeProductos;
    }

    @Override
    public List<Producto> buscarPorCategoria(EnumCategoria categoria) {
        String categoriaStr = String.valueOf(categoria);
        List<Producto> listaDeProductos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE categoria = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

             pstmt.setString(1, categoriaStr);

             try(ResultSet rs = pstmt.executeQuery())
             {
                 while (rs.next()) {
                     Producto producto = new Producto(
                             rs.getInt("codigo_producto"),
                             rs.getString("nombre"),
                             rs.getString("categoria"),
                             rs.getString("subcategoria"),
                             rs.getFloat("precio")
                     );
                     listaDeProductos.add(producto);
                 }
             }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeProductos;
    }

    @Override
    public List<Producto> buscarPorRangoPrecio(float min, float max) {
        List<Producto> listaDeProductos = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE precio >= ? AND precio <= ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.valueOf(min));
            pstmt.setString(2, String.valueOf(max));

            try(ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next()) {
                    Producto producto = new Producto(
                            rs.getInt("codigo_producto"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getString("subcategoria"),
                            rs.getFloat("precio")
                    );
                    listaDeProductos.add(producto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeProductos;
    }
}
