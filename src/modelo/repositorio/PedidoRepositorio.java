package modelo.repositorio;

import modelo.dominio.*;
import modelo.utils.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoRepositorio implements IPedidoRepositorio{
    @Override
    public List<Pedido> getListaPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM pedidos";
        Pedido pedido = null;
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Map Pedido fields
                Long numPedido = rs.getLong("num_pedido");
                Float precioFinal = rs.getFloat("precio_final");
                float costoEnvio = rs.getFloat("costo_envio");
                float descuento = rs.getFloat("descuento");
                LocalDateTime fechaPedido = rs.getTimestamp("fecha_pedido").toLocalDateTime();
                boolean entregado = rs.getBoolean("entregado");
                LocalDateTime fechaEntregado = rs.getTimestamp("fecha_entregado") != null
                        ? rs.getTimestamp("fecha_entregado").toLocalDateTime()
                        : null;

                // Retrieve and set Carrito
                Carrito carrito = getCarritoForPedido(numPedido);

                // Create Pedido object
                pedido = new Pedido(numPedido, carrito, precioFinal, fechaPedido, entregado, fechaEntregado);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public void agregarPedido(Pedido pedido) {

    }

    private Carrito getCarritoForPedido(Long numPedido) {
        Usuario usuario = getUsuarioForPedido(numPedido);
        List<ItemCompra> items = getItemsForPedido(numPedido);
        return new Carrito(usuario, items);
    }

    private Usuario getUsuarioForPedido(Long numPedido) {
        String userQuery = "SELECT u.* FROM usuarios u INNER JOIN pedidos p ON u.mail = p.usuario_mail WHERE p.num_pedido = ?";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement userStmt = conn.prepareStatement(userQuery)) {

            userStmt.setLong(1, numPedido);
            ResultSet rs = userStmt.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getString("mail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ItemCompra> getItemsForPedido(Long numPedido) {
        List<ItemCompra> items = new ArrayList<>();
        String productQuery = "SELECT pr.* FROM productos pr INNER JOIN pedidos_productos pp ON pr.codigo_producto = pp.codigo_producto WHERE pp.num_pedido = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement productStmt = conn.prepareStatement(productQuery)) {

            productStmt.setLong(1, numPedido);
            ResultSet rs = productStmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("codigo_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getString("subcategoria"),
                        rs.getFloat("precio"),
                        rs.getInt("stock")
                );
                ItemCompra item = new ItemCompra(producto, rs.getInt("cantidad"));

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
