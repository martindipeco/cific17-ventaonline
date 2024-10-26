package modelo.repositorio;

import modelo.dominio.EnumCategoria;
import modelo.dominio.Producto;
import modelo.dominio.Usuario;
import modelo.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void agregarProducto(int codigo, String nombre, EnumCategoria categoria, float precio) {

    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return null;
    }

    @Override
    public List<Producto> buscarPorCategoria(EnumCategoria categoria) {
        return null;
    }

    @Override
    public List<Producto> buscarPorRangoPrecio(float min, float max) {
        return null;
    }
}
