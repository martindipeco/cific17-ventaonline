package vista;

import controlador.Controlador;
import modelo.dominio.Usuario;

import javax.swing.*;
import java.awt.event.*;

public class MenuUsuarioNuevo extends JDialog {
    Controlador controlador = Controlador.getInstanciaUnicaControlador();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldMailNuevo;
    private JTextField textFieldNombreNuevo;
    private JTextField textFieldDireccionNuevo;
    private JTextField textFieldTarjCredNuevo;
    private JPasswordField passwordFieldPassNuevo;

    public MenuUsuarioNuevo() {
        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);  // Centers the dialog on the screen
        pack(); //adjusts size to content
        getRootPane().setDefaultButton(buttonOK);


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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK()
    {
        Boolean datosValidados = true;
        String mailNuevoUsuario = textFieldMailNuevo.getText();
        if(!controlador.getValidadorServicio().esValidoMail(mailNuevoUsuario))
        {
            JOptionPane.showMessageDialog(this, "Formato de mail no es válido");
            textFieldMailNuevo.setText("");
            datosValidados = false;
        }
        //chequear contra repo si usuario existe.
        if(controlador.getUsuarioServicio().buscarUsuario(mailNuevoUsuario)!= null)
        {
            JOptionPane.showMessageDialog(this, "El usuario ya existe. " +
                    "Inicie sesión con sus credenciales.");
            datosValidados = false;
            this.setVisible(false);
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            menuPrincipal.setVisible(true);
        }
        else
        {
            //pedimos el resto de los datos
            String passNuevoUsuario = new String(passwordFieldPassNuevo.getPassword());
            if(!controlador.getValidadorServicio().esValidoPass(passNuevoUsuario))
            {
                JOptionPane.showMessageDialog(this, "Formato de contraseña " + passNuevoUsuario +
                        "no es válida. Debe tener al menos 6 caracteres, " +
                        "de los cuales al menos uno debe ser un número y al menos uno debe ser una letra");
                passwordFieldPassNuevo.setText("");
                datosValidados = false;
            }

            String nombreNuevoUsuario = textFieldNombreNuevo.getText();
            if(nombreNuevoUsuario.trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Ingrese al menos un caracter para el nombre");
                textFieldNombreNuevo.setText("");
                datosValidados = false;
            }

            String direccionNuevoUsuario = textFieldDireccionNuevo.getText();
            if(direccionNuevoUsuario.trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Ingrese al menos un caracter para la dirección");
                textFieldDireccionNuevo.setText("");
                datosValidados = false;
            }

            boolean tarjetaOK = false;
            String numTarjetaString = textFieldTarjCredNuevo.getText();
            Long numTarjetaNuevoUsuario = 0l;
            try
            {
                numTarjetaNuevoUsuario = Long.parseLong(numTarjetaString);
                tarjetaOK = controlador.getValidadorServicio().esValidaTarjeta(numTarjetaString);
                if(!tarjetaOK)
                {
                    JOptionPane.showMessageDialog(this, "Formato no válido. " +
                            "Ingrese al menos 10 dígitos");
                    textFieldTarjCredNuevo.setText("");
                    datosValidados = false;
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Formato de tarjeta no válido");
                textFieldTarjCredNuevo.setText("");
                datosValidados = false;
            }
            if(tarjetaOK && datosValidados)
            {
                String tarjetaEncriptada = controlador.getEncriptaServicio().encriptaHash(numTarjetaString);
                String passEncriptado = controlador.getEncriptaServicio().encriptaHash(passNuevoUsuario);

                controlador.setUsuarioSesion(new Usuario(mailNuevoUsuario, passEncriptado, nombreNuevoUsuario,
                        direccionNuevoUsuario, tarjetaEncriptada));
                JOptionPane.showMessageDialog(this, "Nuevo usuario registrado con éxito");
                MenuCompra menuCompra = new MenuCompra();
                menuCompra.setVisible(true);
                this.setVisible(false);
            }
        }
    }
    private void onCancel() {
        this.setVisible(false);
    }
}
