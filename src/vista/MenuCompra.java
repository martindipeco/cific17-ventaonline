package vista;

import controlador.Controlador;
import modelo.dominio.Carrito;
import modelo.dominio.EnumCategoria;
import modelo.dominio.Pedido;
import modelo.dominio.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class MenuCompra extends JDialog {
    private Controlador controlador = Controlador.getInstanciaUnicaControlador();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tableProductos;
    private JTextField textFieldCodigoProducto;
    private JButton buttonBuscar;
    private JTextField textFieldNombre;
    private JComboBox<EnumCategoria> comboBoxCategoria;
    private JButton buttonAgregarACarrito;
    private JButton buttonQuitarSeleccion;
    private JButton buttonVerPedidos;
    private JTable tableCarrito;
    private JSpinner spinnerMinimo;
    private JSpinner spinnerMaximo;
    private JButton buttonImprimirFactura;
    private DefaultTableModel tableModelProductos = new DefaultTableModel();
    private DefaultTableModel tableModelCarrito = new DefaultTableModel();

    public MenuCompra() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //setLocationRelativeTo(null);  // Centers the dialog on the screen

        // Get the screen size dynamically
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set dialog size to half the screen dimensions
        int dialogWidth = screenWidth / 2;
        int dialogHeight = screenHeight / 2;
        setSize(dialogWidth, dialogHeight);

        // Calculate and set the dialog position to center it
        int x = (screenWidth - dialogWidth) / 2;
        int y = (screenHeight - dialogHeight) / 2;
        setLocation(x, y);

        // Populate the comboBoxCategoria with EnumCategoria enum values
        comboBoxCategoria.addItem(null);
        for (EnumCategoria categoria : EnumCategoria.values()) {
            comboBoxCategoria.addItem(categoria); // Add each enum value to the combo box
        }

        // Call method to initiate and populate the tables
        iniciarTablaProductos();
        agregarTodosTablaProductos();

        iniciarTablaCarrito();

        pack(); //adjusts size to content

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                String quienEsta = "";
                if(controlador.getCarritoSesion().getUsuario()==null)
                {
                    quienEsta = "Invitado";
                }
                else
                {
                    quienEsta = controlador.getCarritoSesion().getUsuario().getNombre();
                }
                setTitle("Sesión de: " + quienEsta);
            }
            @Override
            public void windowLostFocus(WindowEvent e) {
                // do nothing when dialog loses focus
            }
        });
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

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBuscar();
            }
        });

        //selector de tabla por categoria de combo box
        comboBoxCategoria.addActionListener(e -> {
            EnumCategoria categoriaSeleccionada = (EnumCategoria) comboBoxCategoria.getSelectedItem();
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

        buttonQuitarSeleccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onQuitarSeleccionDeCarrito();
            }
        });

        buttonImprimirFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onImprimirFactura();
            }
        });
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

        spinnerMinimo.setModel(new SpinnerNumberModel(0f, 0f, Float.MAX_VALUE, 1));
        spinnerMaximo.setModel(new SpinnerNumberModel(Float.MAX_VALUE, 0f, Float.MAX_VALUE, 1));

    }

    private void cargarTablaPorCategoria(EnumCategoria categoria)
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
                tableModelProductos.addRow(rowData);
            }
        }
    }

    private void limpiarTablaProductos() {

        // Remove all rows except the header, the first one
        int rowCount = tableModelProductos.getRowCount();
        for (int i = rowCount - 1; i > 0; i--) {  // Start from the last row and go upwards to the second row
            tableModelProductos.removeRow(i);
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

    // Methods to create Productos and Carrito table headers
    private void iniciarTablaProductos() {

        // Create column names
        tableModelProductos.addColumn("Código");
        tableModelProductos.addColumn("Nombre");
        tableModelProductos.addColumn("Categoría");
        tableModelProductos.addColumn("Precio");
        tableModelProductos.addColumn("Stock");
        Object[] headers = {"CODIGO", "NOMBRE", "CATEGORIA", "PRECIO", "STOCK"};
        tableModelProductos.addRow(headers);

        // Set the table model to the JTable
        tableProductos.setModel(tableModelProductos);
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

//    private void agregarATablaProductos(Producto producto)
//    {
//        Object[] rowData = {
//                producto.getCodigoProducto(),
//                producto.getNombre(),
//                producto.getCategoria(),
//                producto.getPrecio(),
//                producto.getStock()
//        };
//        tableModelProductos.addRow(rowData);
//    }

    //Usado por Botón BUSCAR
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
            tableModelProductos.addRow(rowData);
        }
    }

    //Usado en la carga inicial
    private void agregarTodosTablaProductos()
    {
        // Create an instance of ProductoRepositorio to get the products
        List<Producto> productos = controlador.getProductoServicio().listarProductos();

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
            tableModelProductos.addRow(rowData);
        }
    }

    //AGREGA 1 PRODUCTO DE A 1 UNIDAD
    private void agregarATablaCarrito(Producto producto) {
        // capturar codigo de producto , q esta en la primer columna
        String codigoProducto = String.valueOf(producto.getCodigoProducto());
        boolean productoExisteEnTablaCarrito = false;
        int stockDisponible = -1;

        // Check de stock
        for (int i = 0; i < tableModelProductos.getRowCount(); i++) {
            String codigoEnTablaProductos = tableModelProductos.getValueAt(i, 0).toString(); // Col 0 = product cod

            // Find the matching product in the product table
            if (codigoProducto.equals(codigoEnTablaProductos)) {
                stockDisponible = Integer.parseInt(tableModelProductos.getValueAt(i, 4).toString()); // Col 4 = stock
                break;
            }
        }

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

            // Subtract one unit from the stock in the product table TODO AND the data base
            for (int i = 0; i < tableModelProductos.getRowCount(); i++) {
                String codigoEnTablaProductos = tableModelProductos.getValueAt(i, 0).toString(); // Col 0 = cod

                // Find the matching product in the product table
                if (codigoProducto.equals(codigoEnTablaProductos)) {
                    int stockActual = Integer.parseInt(tableModelProductos.getValueAt(i, 4).toString()); // Col 4 = stock
                    tableModelProductos.setValueAt(stockActual - 1, i, 4); // Subtract 1 from stock
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay stock de " + producto.getNombre());
        }
        //deselecciono los elementos
        tableProductos.clearSelection();
    }


    //AGREGA VARIOS PRODUCTOS DE A 1 UNIDAD
    private void agregarATablaCarrito(List<Producto> productos)
    {
        for(Producto producto : productos)
        {
            agregarATablaCarrito(producto);
        }
    }

    private void actualizarStockTablaProductos(Producto producto)
    {
        for (int row = 0; row < tableModelProductos.getRowCount(); row++)
        {
            //codigo es columna 0
            String codigoEnTabla = tableModelProductos.getValueAt(row, 0).toString();
            if (codigoEnTabla.equals(String.valueOf(producto.getCodigoProducto())))
            {
                // stock es col 4
                tableModelProductos.setValueAt(producto.getStock(), row, 4);
                break;
            }
        }
    }

    private void pedirDatosUsuario()
    {
        // opciones para OptionPane
        Object[] options = {"Log In", "Crear Usuario", "OK"};

        // mostrar el option dialgog Pane
        int choice = JOptionPane.showOptionDialog(
                null,
                "Es necesario loguearse o crear usuario",
                "Opciones de usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[2] // Default button is "OK"
        );

        // Handle the user's choice
        if (choice == 0) {
            // Log In button pressed
            MenuUsuarioRegistrado menuUsuarioRegistrado = new MenuUsuarioRegistrado();
            menuUsuarioRegistrado.setVisible(true);
        } else if (choice == 1) {
            // Create User button pressed
            MenuUsuarioNuevo menuUsuarioNuevo = new MenuUsuarioNuevo();
            menuUsuarioNuevo.setVisible(true);
        }  // OK button pressed or dialog closed. Simply return to the previous menu (dialog closes)
    }

    //Filtro con streams
    private void onBuscar()
    {
        //capturar todos los valores, y pasarlos al metodo buscar general
        int codigo = -1;
        String nombre = "";
        EnumCategoria categoria = null;
        float min = 0f;
        float max = Float.MAX_VALUE;

        //capturo valor de codigo, o dejo -1 x defecto
        String codigoString = textFieldCodigoProducto.getText();
        if (!codigoString.trim().isEmpty())
        {
            try
            {
                codigo = Integer.parseInt(codigoString);
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Formato de código no válido");
                textFieldCodigoProducto.setText("");
            }
        }
        //capturo valor de nombre
        nombre = textFieldNombre.getText();
        //capturo valor de categoria
        categoria = (EnumCategoria) comboBoxCategoria.getSelectedItem();
        //capturo valor de min y max
        if(min >= max)
        {
            JOptionPane.showMessageDialog(this, "Mínimo debe ser menor a máximo. No se aplica el filtro");
        }
        else
        {
            min = Float.parseFloat(spinnerMinimo.getValue().toString()) ;
            max = Float.parseFloat(spinnerMaximo.getValue().toString()) ;
        }
        //paso valores por parámetro y capturo resultado en lista
        List<Producto> productosFiltrados = controlador.getProductoServicio()
                .buscarCompleto(codigo, nombre, categoria, min, max);

        //impacto en tabla
        limpiarTablaProductos();
        agregarATablaProductos(productosFiltrados);
        //volver a default todos valores casilleros
        textFieldCodigoProducto.setText("");
        textFieldNombre.setText("");
        spinnerMinimo.setValue(0);
        spinnerMaximo.setValue(Float.MAX_VALUE);
        comboBoxCategoria.setSelectedIndex(0);
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

    private void onQuitarSeleccionDeCarrito()
    {
        // Capture selected items
        int[] selectedRows = tableCarrito.getSelectedRows();
        List<Producto> productosSeleccionados = new ArrayList<>();

        for(int i = selectedRows.length - 1; i >= 0; i--)  // Iterate in reverse to avoid shifting rows after removal
        {
            int selectedRow = selectedRows[i];
            String codigoString = tableCarrito.getValueAt(selectedRow, 0).toString();
            try
            {
                int codigo = Integer.parseInt(codigoString);
                Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigo);
                if(producto != null)
                {
                    //capturar cantidad (columna 4) en carrito
                    int cantidadEnCarrito = Integer.parseInt(tableCarrito.getValueAt(selectedRow, 4).toString());

                    //quitar el producto del carrito, y reponer stock de bd
                    controlador.getCarritoServicio().quitarProducto(controlador.getCarritoSesion(), producto);

                    actualizarStockTablaProductos(producto);

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

        if (productosSeleccionados.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No se seleccionaron productos válidos para quitar del carrito.");
        }
        tableCarrito.clearSelection();
    }

    private void onImprimirFactura()
    {
        Pedido pedidoMasReciente = controlador.getPedidoServicio().muestraUltimoPedido(controlador.getUsuarioSesion());
        exportarAPDF(pedidoMasReciente);
    }

    private void exportarAPDF (Pedido pedido) {
        try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //text
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
        contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 52);
        // Split text by line breaks and write each line separately
        for (String line : pedido.toString().split("\n")) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -15); // Move down by 15 points (adjust as needed)
        }
        //contentStream.showText(pedido.toString());
        contentStream.endText();

        contentStream.close();

        String pdfFileName = "factura_" + pedido.getNumPedido() + ".pdf";
        document.save(pdfFileName);

        // Attempt to open the file after saving
        File pdfFile = new File(pdfFileName);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(pdfFile);
        } else {
            System.out.println("Desktop not supported. Cannot open PDF automatically.");
        }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void onVerPedidos()
    {
        String verPedidos = controlador.getPedidoServicio().muestraPedidosDeUsuario(controlador.getUsuarioSesion()).toString();
        JOptionPane.showMessageDialog(this, verPedidos, "Ultimos 5 pedidos", JOptionPane.INFORMATION_MESSAGE);
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
                pedirDatosUsuario();
            }
            else
            {
                //instancio pedido a partir de carrito actual
                controlador.getPedidoServicio().agregarPedido(new Pedido(controlador.getCarritoSesion()));
                //reiniciamos el carrito
                controlador.setCarritoSesion(new Carrito(controlador.getUsuarioSesion()));
                limpiarTablaCarrito();
                JOptionPane.showMessageDialog(this, "Pedido realizado con éxito");
            }
        }
    }

    private void onCancel() {
        //antes de cerrar me aseguro que no queden items adentro del carrito
        if(!controlador.getCarritoSesion().getListaItems().isEmpty())
        {
            controlador.getCarritoServicio().vaciarCarrito(controlador.getCarritoSesion());
        }
        JOptionPane.showMessageDialog(this, "Gracias por su visita. Vuelva pronto!");
        dispose();
    }
}
