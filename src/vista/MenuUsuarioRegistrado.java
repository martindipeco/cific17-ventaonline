package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class MenuUsuarioRegistrado extends JDialog {
    Controlador controlador = Controlador.getInstanciaUnicaControlador();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonAtras;
    private JTextField textFieldMail;
    private JPasswordField passwordFieldRegistrado;

    private int contador = 3;

    public MenuUsuarioRegistrado() {
        setContentPane(contentPane);
        setModal(true); //forces focus
        setLocationRelativeTo(null);  // Centers the dialog on the screen
        pack(); //adjusts size to content
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonAtras.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAtras();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onAtras();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAtras();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        Boolean datosValidados = true;

        String mailUsuario = textFieldMail.getText();
        if(!controlador.getValidadorServicio().esValidoMail(mailUsuario))
        {
            JOptionPane.showMessageDialog(this, "Mail no es válido");
            textFieldMail.setText("");
            datosValidados = false;
        }

        String passUsuario = new String(passwordFieldRegistrado.getPassword());
        if(!controlador.getValidadorServicio().esValidoPass(passUsuario))
        {
            JOptionPane.showMessageDialog(this, "Contraseña " + passUsuario + "no es válida");
            passwordFieldRegistrado.setText("");
            datosValidados = false;
        }

        if(datosValidados)
        {
            //chequear contra repo si usuario existe. si existe, instanciarlo a través de controlador
            //al método logUsuario debo pasarle la contraseña encriptada, ya que esa será la que compare en Base de Datos
            String passEncriptado = controlador.getEncriptaServicio().encriptaHash(passUsuario);
            controlador.setUsuarioSesion(controlador.getUsuarioServicio().logUsuario(mailUsuario, passEncriptado));
            if (controlador.getUsuarioSesion()==null)
            {
                //si no existe, informar , restar una posibilidad y preguntar si desea crear nuevo usuario
                contador--;
                JOptionPane.showMessageDialog(this, "Mail o contraseña incorrectos, " +
                        "le quedan " + contador + " posibilidades");
                textFieldMail.setText("");
                passwordFieldRegistrado.setText("");
                if(contador == 0)
                {
                    JOptionPane.showMessageDialog(this, "Ingresando como invitado");
                    controlador.setUsuarioSesion(null);
                    //usuarioListo = true;
                }

            }
            else
            {
                JOptionPane.showMessageDialog(this, "Ingreso validado");
                //TODO muestra menu compras
            }
        }
    }

    private void onAtras() {
        this.setVisible(false);
    }
}
