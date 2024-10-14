package vista;

import controlador.Controlador;
import modelo.dominio.Carrito;
import modelo.dominio.Pedido;
import modelo.dominio.Producto;
import modelo.dominio.ProductoCategoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuCompra extends JDialog {
    private Controlador controlador = Controlador.getInstanciaUnicaControlador();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tableProductos;
    private JTextField textFieldCodigoProducto;
    private JButton buttonCodigo;
    private JTextField textFieldNombre;
    private JButton buttonNombre;
    private JTextField textFieldPrecioMin;
    private JButton buttonPrecio;
    private JTextField textFieldPrecioMax;
    private JComboBox<ProductoCategoria> comboBoxCategoria;
    private JButton buttonAgregarACarrito;
    //private JButton buttonVerCarrito;
    private JButton buttonBorrarSeleccion;
    private JButton buttonVerPedidos;
    private JTable tableCarrito;
    private DefaultTableModel tableModel = new DefaultTableModel();

    private DefaultTableModel tableModelCarrito = new DefaultTableModel();

    public MenuCompra() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);  // Centers the dialog on the screen

        // Populate the comboBoxCategoria with ProductoCategoria enum values
        for (ProductoCategoria categoria : ProductoCategoria.values()) {
            comboBoxCategoria.addItem(categoria); // Add each enum value to the combo box
        }

        // Call method to initiate and populate the tables
        iniciarTablaProductos();
        agregarTodosTablaProductos();

        iniciarTablaCarrito();

        pack(); //adjusts size to content

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonCodigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCodigoProducto();
            }
        });

        buttonNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNombre();
            }
        });

        buttonPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPrecio();
            }
        });

        comboBoxCategoria.addActionListener(e -> {
            ProductoCategoria categoriaSeleccionada = (ProductoCategoria) comboBoxCategoria.getSelectedItem();
            if (categoriaSeleccionada != null) {
                cargarTablaPorCategoria(categoriaSeleccionada);
            }
        });

        buttonAgregarACarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAgregarACarrito();
            }
        });

        buttonBorrarSeleccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBorrarSeleccion();
            }
        });

//        buttonVerCarrito.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                onVerCarrito();
//            }
//        });

        buttonVerPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onVerPedidos();
            }
        });

        ListSelectionModel selectionModel = tableProductos.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        ListSelectionModel selectionModelCarrito = tableCarrito.getSelectionModel();
        selectionModelCarrito.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

//        selectionModel.addListSelectionListener(e -> {
//            // Ignora los eventos cuando la selección está ajustando
//            if (!e.getValueIsAdjusting()) {
//
//                int[] selectedRows = tableProductos.getSelectedRows();
//                List<Producto> productosSeleccionados = new ArrayList<>();
//
//                for(int selectedRow : selectedRows)
//                {
//                    String codigoString = tableProductos.getValueAt(selectedRow, 0).toString();
//                    Integer codigo = Integer.parseInt(codigoString);
//                    controlador.getCarritoServicio().agregarProductoX1(controlador.getCarritoSesion(),
//                            controlador.getProductoServicio().buscarPorCodigo(codigo));
//                }
//            }
//        });
    }

    private void cargarTablaPorCategoria(ProductoCategoria categoria)
    {
        limpiarTablaProductos();
        List<Producto> productos = controlador.getProductoServicio().buscarPorCategoria(categoria);
        for (Producto producto : productos) {
            if (producto.getCategoria().equals(categoria)) {
                Object[] rowData = {
                        producto.getCodigoProducto(),
                        producto.getNombre(),
                        producto.getCategoria(),
                        producto.getPrecio(),
                        producto.getStock()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    private void limpiarTablaProductos() {

        // Remove all rows except the header, the first one
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i > 0; i--) {  // Start from the last row and go upwards to the second row
            tableModel.removeRow(i);
        }
    }

    private void limpiarTablaCarrito()
    {
        // Remove all rows except the header, the first one
        int rowCount = tableModelCarrito.getRowCount();
        for (int i = rowCount - 1; i > 0; i--) {  // Start from the last row and go upwards to the second row
            tableModelCarrito.removeRow(i);
        }
    }

    // Method to create table header
    private void iniciarTablaProductos() {

        // Create column names
        tableModel.addColumn("Código");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Categoría");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");
        Object[] headers = {"CODIGO", "NOMBRE", "CATEGORIA", "PRECIO", "STOCK"};
        tableModel.addRow(headers);

        // Set the table model to the JTable
        tableProductos.setModel(tableModel);
    }

    private void iniciarTablaCarrito()
    {
        // Create column names
        tableModelCarrito.addColumn("Código");
        tableModelCarrito.addColumn("Nombre");
        tableModelCarrito.addColumn("Categoría");
        tableModelCarrito.addColumn("Precio");
        tableModelCarrito.addColumn("Cantidad");
        Object[] headers = {"CODIGO", "NOMBRE", "CATEGORIA", "PRECIO", "CANT."};
        tableModelCarrito.addRow(headers);

        // Set the table model to the JTable
        tableCarrito.setModel(tableModelCarrito);
    }

    private void agregarATablaProductos(Producto producto)
    {
        Object[] rowData = {
                producto.getCodigoProducto(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStock()
        };
        tableModel.addRow(rowData);
    }

    private void agregarATablaProductos(List<Producto> productos)
    {
        int limit =  Math.min(productos.size(), 10);// Limit to 10 or fewer if there are less products

        // Populate the table model with product data
        for (int i = 0; i < limit; i++) {
            Producto producto = productos.get(i);
            Object[] rowData = {
                    producto.getCodigoProducto(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock()
            };
            tableModel.addRow(rowData);
        }
    }

    private void agregarTodosTablaProductos()
    {
        // Create an instance of ProductoRepositorio to get the products
        List<Producto> productos = Controlador.getInstanciaUnicaControlador().getProductoServicio()
                .listarProductos();

        int limit = Math.min(productos.size(), 10); // Limit to 10 or fewer if there are less products

        // Populate the table model with product data
        for (int i = 0; i < limit; i++) {
            Producto producto = productos.get(i);
            Object[] rowData = {
                    producto.getCodigoProducto(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock()
            };
            tableModel.addRow(rowData);
        }
    }

    //AGREGA 1 PRODUCTO DE A 1 UNIDAD
    private void agregarATablaCarrito(Producto producto) {
        // Get the product code (first column in table)
        String codigoProducto = String.valueOf(producto.getCodigoProducto());
        boolean productoExisteEnTablaCarrito = false;
        int stockDisponible = -1;

        // Check the stock in the product table before adding to cart
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String codigoEnTablaProductos = tableModel.getValueAt(i, 0).toString(); // Col 0 = product cod

            // Find the matching product in the product table
            if (codigoProducto.equals(codigoEnTablaProductos)) {
                stockDisponible = Integer.parseInt(tableModel.getValueAt(i, 4).toString()); // Col 4 = stock
                break;
            }
        }

        // Only allow adding the product to the cart if stock is greater than 0
        if (stockDisponible > 0) {
            // Iterate over the rows of the shopping cart to check if the product is already in the cart
            for (int i = 0; i < tableModelCarrito.getRowCount(); i++) {
                String codigoEnTablaCarrito = tableModelCarrito.getValueAt(i, 0).toString(); // Col 0 = cod

                // If the product is found, update the "Cant" (quantity) column, nº 4
                if (codigoProducto.equals(codigoEnTablaCarrito)) {
                    int cantidadActualCarrito = Integer.parseInt(tableModelCarrito.getValueAt(i, 4).toString());
                    tableModelCarrito.setValueAt(cantidadActualCarrito + 1, i, 4); // Update Cant + 1
                    productoExisteEnTablaCarrito = true;
                    break;
                }
            }

            // If the product was not found in the shopping cart, add a new row with Cant = 1
            if (!productoExisteEnTablaCarrito) {
                Object[] rowDataCarrito = {
                        producto.getCodigoProducto(),
                        producto.getNombre(),
                        producto.getCategoria(),
                        producto.getPrecio(),
                        1
                };
                tableModelCarrito.addRow(rowDataCarrito);
            }

            // Subtract one unit from the stock in the product table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String codigoEnTablaProductos = tableModel.getValueAt(i, 0).toString(); // Col 0 = cod

                // Find the matching product in the product table
                if (codigoProducto.equals(codigoEnTablaProductos)) {
                    int stockActual = Integer.parseInt(tableModel.getValueAt(i, 4).toString()); // Col 4 = stock
                    tableModel.setValueAt(stockActual - 1, i, 4); // Subtract 1 from stock
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay stock de " + producto.getNombre());
        }
    }


    //AGREGA VARIOS PRODUCTOS DE A 1 UNIDAD
    private void agregarATablaCarrito(List<Producto> productos)
    {
        for(Producto producto : productos)
        {
            agregarATablaCarrito(producto);
        }
    }

    private void onCodigoProducto()
    {
        Boolean datosValidados = true;
        //capturar texto
        String codigoString = textFieldCodigoProducto.getText();
        if (codigoString.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Ingrese al menos un caracter para el código");
            textFieldCodigoProducto.setText("");
            datosValidados = false;
        }
        try
        {
            int codigoProd = Integer.parseInt(codigoString);
            Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigoProd);
            if(producto == null)
            {
                JOptionPane.showMessageDialog(this, "No se encontraron productos con ese código");
                textFieldCodigoProducto.setText("");
                datosValidados = false;
            }
            else
            {
                if(datosValidados)
                {
                    limpiarTablaProductos();
                    agregarATablaProductos(producto);
                    textFieldCodigoProducto.setText("");
                }
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Formato de código no válido");
            textFieldCodigoProducto.setText("");
            datosValidados = false;
        }
    }

    private void onNombre()
    {
        Boolean datosValidados = true;
        String nombre = textFieldNombre.getText();
        if (nombre.trim().isEmpty() || nombre.length() < 3)
        {
            JOptionPane.showMessageDialog(this, "Ingrese al menos tres caracteres para el nombre");
            textFieldNombre.setText("");
            datosValidados = false;
        }
        List<Producto> productos = controlador.getProductoServicio().buscarPorNombre(nombre);
        if(productos.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No se encontraron productos con ese nombre");
            textFieldNombre.setText("");
            datosValidados = false;
        }
        else
        {
            if(datosValidados)
            {
                limpiarTablaProductos();
                agregarATablaProductos(productos);
                textFieldNombre.setText("");
            }
        }
    }

    private void onPrecio()
    {
        Boolean datosValidados = true;
        String precioMinString = textFieldPrecioMin.getText();
        String precioMaxString = textFieldPrecioMax.getText();

        if (precioMinString.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Se toma consulta sin precio mínimo");
            textFieldPrecioMin.setText("0");
        }
        if(precioMaxString.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Se toma consulta sin precio máximo");
            textFieldPrecioMax.setText("" + Float.MAX_VALUE);
        }
        try
        {
            float precioMin = Float.parseFloat(precioMinString);
            float precioMax = Float.parseFloat(precioMaxString);
            if(precioMin >= precioMax)
            {
                JOptionPane.showMessageDialog(this, "Mínimo debe ser menor a máximo");
                textFieldPrecioMin.setText("");
                textFieldPrecioMax.setText("");
                datosValidados = false;
            }

            List<Producto> productos = controlador.getProductoServicio().buscarPorRangoPrecio(precioMin, precioMax);
            if(productos.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "No se encontraron productos en ese rango");
                textFieldPrecioMin.setText("");
                textFieldPrecioMax.setText("");
                datosValidados = false;
            }
            else
            {
                if(datosValidados)
                {
                    limpiarTablaProductos();
                    agregarATablaProductos(productos);
                    textFieldPrecioMin.setText("");
                    textFieldPrecioMax.setText("");
                }
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Formato de precio no válido");
            textFieldCodigoProducto.setText("");
            datosValidados = false;
        }
    }

    private void onAgregarACarrito()
    {
        // Capture selected items
        int[] selectedRows = tableProductos.getSelectedRows();
        List<Producto> productosSeleccionados = new ArrayList<>();

        // Iterate through selected rows and add to the cart
        for (int selectedRow : selectedRows) {
            String codigoString = tableProductos.getValueAt(selectedRow, 0).toString();
            try
            {
                int codigo = Integer.parseInt(codigoString);

                // Retrieve the product using the code and add it to the cart
                Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigo);
                if (producto != null) {
                    controlador.getCarritoServicio().agregarProductoX1(controlador.getCarritoSesion(), producto);
                    productosSeleccionados.add(producto); // Optional: keep track of added products
                    agregarATablaCarrito(producto); //mostrar en tabla
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Error de formato en código");
            }
        }

        // Optional: Show a message or update UI after adding products to the cart
        if (productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se seleccionaron productos válidos para agregar al carrito.");
        }
    }

    private void onBorrarSeleccion()
    {
        // Capture selected items
        int[] selectedRows = tableProductos.getSelectedRows();
        List<Producto> productosSeleccionados = new ArrayList<>();

        for(int i = selectedRows.length - 1; i >= 0; i--)  // Iterate in reverse to avoid shifting rows after removal
        {
            int selectedRow = selectedRows[i];
            String codigoString = tableProductos.getValueAt(selectedRow, 0).toString();
            try
            {
                int codigo = Integer.parseInt(codigoString);
                Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigo);
                if(producto != null)
                {
                    controlador.getCarritoServicio().quitarProducto(controlador.getCarritoSesion(), producto);
                    productosSeleccionados.add(producto);

                    // Remove the row from the table model
                    tableModelCarrito.removeRow(selectedRow);
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Error de formato en código");
            }
        }

        if (!productosSeleccionados.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Productos eliminados del carrito: " + productosSeleccionados.size());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No se seleccionaron productos válidos para quitar del carrito.");
        }
    }

//    private void onVerCarrito()
//    {
//        String verCarrito = controlador.getCarritoServicio().obtenerCarrito(controlador.getCarritoSesion());
//        JOptionPane.showMessageDialog(this, verCarrito);
//    }

    private void onVerPedidos()
    {
        String verPedidos = controlador.getPedidoServicio().getListaPedidos().toString();
        JOptionPane.showMessageDialog(this, verPedidos);
    }

    private void onOK() {
        if(controlador.getCarritoSesion().getListaItems().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "El carrito está vacío. " +
                    "Agregue al menos un producto");
        }
        else
        {
            if (controlador.getCarritoSesion().getUsuario()==null)
            {
                JOptionPane.showMessageDialog(this, "Es necesario crear usuario o loguearse");
                //TODO: chequear si es necesario instanciar nuevo menu principal
                MenuPrincipal.getWindows();
            }
            JOptionPane.showMessageDialog(this, "Procesando su compra");
            //instancio pedido a partir de carrito actual
            controlador.getPedidoServicio().agregarPedido(new Pedido(controlador.getCarritoSesion()));
            //muestro el pedido, el último en la lista x q acaba de agregarse
            JOptionPane.showMessageDialog(this, controlador.getPedidoServicio().getListaPedidos().get(
                    controlador.getPedidoServicio().getListaPedidos().size()-1));
            //reiniciamos el carrito
            controlador.setCarritoSesion(new Carrito(controlador.getUsuarioSesion()));
            limpiarTablaCarrito();
        }
        JOptionPane.showMessageDialog(this, "Pedido realizado con éxito");
    }

    private void onCancel() {
        //antes de cerrar me aseguro que no queden items adentro del carrito
        if(!controlador.getCarritoSesion().getListaItems().isEmpty())
        {
            controlador.getCarritoServicio().vaciarCarrito(controlador.getCarritoSesion());
        }
        dispose();
    }
}
