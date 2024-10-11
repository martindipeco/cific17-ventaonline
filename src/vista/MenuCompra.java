package vista;

import controlador.Controlador;
import modelo.dominio.Producto;
import modelo.dominio.ProductoCategoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MenuCompra extends JDialog {
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

    public MenuCompra() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);  // Centers the dialog on the screen
        pack(); //adjusts size to content

        // Populate the comboBoxCategoria with ProductoCategoria enum values
        for (ProductoCategoria categoria : ProductoCategoria.values()) {
            comboBoxCategoria.addItem(categoria); // Add each enum value to the combo box
        }

        // Call method to populate the table
        poblarTablaProductos();
    }

    // Method to populate the table with product data
    private void poblarTablaProductos() {
        // Create an instance of ProductoRepositorio to get the products
        List<Producto> productos = Controlador.getInstanciaUnicaControlador().getProductoServicio()
                .listarProductos();

        // Create a table model with column names
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Código");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Categoría");
        tableModel.addColumn("Precio");
        Object[] headers = {"CODIGO", "NOMBRE", "CATEGORIA", "PRECIO"};
        tableModel.addRow(headers);

        // Populate the table model with product data
        for (Producto producto : productos) {
            Object[] rowData = {
                    producto.getCodigoProducto(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio()
            };
            tableModel.addRow(rowData);
        }

        // Set the table model to the JTable
        tableProductos.setModel(tableModel);
    }
}
