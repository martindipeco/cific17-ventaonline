package vista;

import controlador.Controlador;
import modelo.dominio.Producto;
import modelo.dominio.ProductoCategoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private DefaultTableModel tableModel = new DefaultTableModel();

    public MenuCompra() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);  // Centers the dialog on the screen

        // Populate the comboBoxCategoria with ProductoCategoria enum values
        for (ProductoCategoria categoria : ProductoCategoria.values()) {
            comboBoxCategoria.addItem(categoria); // Add each enum value to the combo box
        }

        // Call method to initiate and populate the table
        iniciarTablaProductos();
        agregarTodosTabla();
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
    }

    private void cargarTablaPorCategoria(ProductoCategoria categoria)
    {
        limpiarTabla();
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

    private void limpiarTabla() {

        // Remove all rows except the header, the first one
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i > 0; i--) {  // Start from the last row and go upwards to the second row
            tableModel.removeRow(i);
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

    private void agregarATabla(Producto producto)
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

    private void agregarATabla(List<Producto> productos)
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

    private void agregarTodosTabla()
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
                    limpiarTabla();
                    agregarATabla(producto);
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
                limpiarTabla();
                agregarATabla(productos);
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
                    limpiarTabla();
                    agregarATabla(productos);
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

    private void onOK() {
        // este boton por ahora no hace nada
        JOptionPane.showMessageDialog(this, "Agregando al carrito");
    }

    private void onCancel() {
        // add your code here if necessary
        this.setVisible(false);
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
    }
}
