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
    public void agregarPedido(Pedido pedido)
    {
        String insertPedidoQuery = "INSERT INTO pedidos (usuario_mail, precio_final, costo_envio, descuento, " +
                "fecha_pedido, entregado, fecha_entregado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertItemQuery = "INSERT INTO pedidos_productos (num_pedido, codigo_producto, cantidad) VALUES (?, ?, ?)";
        String updateStockQuery = "UPDATE productos SET stock = stock - ? WHERE codigo_producto = ?";

        try(Connection conn = DatabaseUtil.getConnection())
        {
            conn.setAutoCommit(false);  // Begin transaction

            // Insert into pedidos table
            try (PreparedStatement pedidoStmt = conn.prepareStatement(insertPedidoQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stockStmt = conn.prepareStatement(updateStockQuery))
            {
                pedidoStmt.setString(1, pedido.getCarrito().getUsuario().getMail());
                pedidoStmt.setFloat(2, pedido.getPrecioFinal());
                pedidoStmt.setFloat(3, 0f);
                pedidoStmt.setFloat(4, 0f);
                pedidoStmt.setTimestamp(5, Timestamp.valueOf(pedido.getFechaPedido()));
                pedidoStmt.setBoolean(6, pedido.isEntregado());
                pedidoStmt.setTimestamp(7, pedido.getFechaEntregado() != null ?
                        Timestamp.valueOf(pedido.getFechaEntregado()) : null);

                pedidoStmt.executeUpdate();

                // Retrieve generated numPedido ID
                ResultSet generatedKeys = pedidoStmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    pedido.setNumPedido(generatedKeys.getLong(1)); // Set numPedido in Pedido instance
                } else {
                    throw new SQLException("Failed to retrieve the generated num_pedido ID.");
                }

                // Insert each ItemCompra into pedidos_productos table
                try (PreparedStatement itemStmt = conn.prepareStatement(insertItemQuery)) {
                    for (ItemCompra item : pedido.getCarrito().getListaItems()) {
                        // Insert into pedidos_productos
                        itemStmt.setLong(1, pedido.getNumPedido());
                        itemStmt.setInt(2, item.getProducto().getCodigoProducto());
                        itemStmt.setInt(3, item.getCantidad());
                        itemStmt.addBatch(); //to improve performance

                        // Update stock for each item
                        stockStmt.setInt(1, item.getCantidad());
                        stockStmt.setInt(2, item.getProducto().getCodigoProducto());
                        stockStmt.addBatch();
                    }
                    itemStmt.executeBatch(); // Execute all insertions for items
                    stockStmt.executeBatch(); // Execute all stock updates
                }
                conn.commit();  // Commit transaction if all inserts succeeded
            }
            catch (SQLException e)
            {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
            }
            finally {
                try {
                    conn.setAutoCommit(true); // Reset to default auto-commit mode
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pedido> getPedidosDeUsuario (Usuario usuario)
    {
        List<Pedido> pedidos = new ArrayList<>();

        String pedidoQuery = "SELECT * FROM pedidos WHERE usuario_mail = ? ORDER BY num_pedido DESC LIMIT 5";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pedidoStmt = conn.prepareStatement(pedidoQuery))
        {
            pedidoStmt.setString(1, usuario.getMail());
            ResultSet rs = pedidoStmt.executeQuery();

            while (rs.next())
            {
                Long numPedido = rs.getLong("num_pedido");
                Float precioFinal = rs.getFloat("precio_final");
                float costoEnvio = rs.getFloat("costo_envio");
                float descuento = rs.getFloat("descuento");
                LocalDateTime fechaPedido = rs.getTimestamp("fecha_pedido").toLocalDateTime();
                boolean entregado = rs.getBoolean("entregado");
                LocalDateTime fechaEntregado = rs.getTimestamp("fecha_entregado") != null
                        ? rs.getTimestamp("fecha_entregado").toLocalDateTime()
                        : null;

                // Retrieve Carrito (User and Items)
                Carrito carrito = getCarritoForPedido(numPedido, usuario);

                // Construct Pedido and add to list
                Pedido pedido = new Pedido(numPedido, carrito, precioFinal, fechaPedido, entregado, fechaEntregado);
                pedidos.add(pedido);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public Pedido getUltimoPedidoDeUsuario (Usuario usuario)
    {
        Pedido pedido = null;

        String pedidoQuery = "SELECT * FROM pedidos WHERE usuario_mail = ? ORDER BY num_pedido DESC LIMIT 1";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pedidoStmt = conn.prepareStatement(pedidoQuery))
        {
            pedidoStmt.setString(1, usuario.getMail());
            ResultSet rs = pedidoStmt.executeQuery();

            if (rs.next())
            {
                Long numPedido = rs.getLong("num_pedido");
                Float precioFinal = rs.getFloat("precio_final");
                float costoEnvio = rs.getFloat("costo_envio");
                float descuento = rs.getFloat("descuento");
                LocalDateTime fechaPedido = rs.getTimestamp("fecha_pedido").toLocalDateTime();
                boolean entregado = rs.getBoolean("entregado");
                LocalDateTime fechaEntregado = rs.getTimestamp("fecha_entregado") != null
                        ? rs.getTimestamp("fecha_entregado").toLocalDateTime()
                        : null;

                // Retrieve Carrito (User and Items)
                Carrito carrito = getCarritoForPedido(numPedido, usuario);

                // Construct Pedido and add to list
                pedido = new Pedido(numPedido, carrito, precioFinal, fechaPedido, entregado, fechaEntregado);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    //HELPER METHOD
    private Carrito getCarritoForPedido(Long numPedido) {
        Usuario usuario = getUsuarioForPedido(numPedido);
        List<ItemCompra> items = getItemsForPedido(numPedido);
        return new Carrito(usuario, items);
    }

    //HELPER METHOD
    private Carrito getCarritoForPedido(Long numPedido, Usuario usuario) {
        List<ItemCompra> items = new ArrayList<>();
        String itemQuery = "SELECT pp.cantidad, p.* FROM pedidos_productos pp INNER JOIN productos p ON " +
                "pp.codigo_producto = p.codigo_producto WHERE pp.num_pedido = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement itemStmt = conn.prepareStatement(itemQuery)) {
            itemStmt.setLong(1, numPedido);
            ResultSet rs = itemStmt.executeQuery();

            while (rs.next()) {
                int cantidad = rs.getInt("cantidad");
                int codigoProducto = rs.getInt("codigo_producto");
                String nombre = rs.getString("nombre");
                EnumCategoria categoria = EnumCategoria.valueOf(rs.getString("categoria"));
                //IEnumSubcategoria subcategoria = IEnumSubcategoria.valueOf(rs.getString("subcategoria"));
                float precio = rs.getFloat("precio");
                float descuento = rs.getFloat("descuento");
                float precioFinal = rs.getFloat("precioFinal");
                int stock = rs.getInt("stock");

                Producto producto = new Producto(codigoProducto, nombre, categoria, precio);
                ItemCompra item = new ItemCompra(producto, cantidad);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Carrito(usuario, items);
    }

    //HELPER METHOD
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
        String productQuery = "SELECT pr.* FROM productos pr INNER JOIN pedidos_productos pp ON pr.codigo_producto " +
                "= pp.codigo_producto WHERE pp.num_pedido = ?";

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
