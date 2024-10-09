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

    public MenuUsuarioRegistrado() {
        setContentPane(contentPane);
        setModal(true);
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
        int contador = 3;
        while(contador > 0)
        {
            String mailUsuario = "";
            do{
                //TODO: transformar bucle
                mailUsuario = textFieldMail.getText();
                JOptionPane.showMessageDialog(this, "Verificando Mail de usuario");
            }
            while (!controlador.getValidadorServicio().esValidoMail(mailUsuario));

            String passUsuario = "";
            do{
                //TODO: transformar bucle
                //System.out.println("Ingrese su contraseña");
                passUsuario = Arrays.toString(passwordFieldRegistrado.getPassword());
                JOptionPane.showMessageDialog(this, "Verificando Mail de usuario");
            }
            while (!controlador.getValidadorServicio().esValidoPass(passUsuario));

            //chequear contra repo si usuario existe. si existe, instanciarlo a través de controlador
            //al método logUsuario debo pasarle la contraseña encriptada, ya que esa será la que compare en Base de Datos
            String passEncriptado = controlador.getEncriptaServicio().encriptaHash(passUsuario);
            controlador.setUsuarioSesion(controlador.getUsuarioServicio().logUsuario(mailUsuario, passEncriptado));
            if (controlador.getUsuarioSesion()==null)
            {
                //si no existe, informar , restar una posibilidad y preguntar si desea crear nuevo usuario
                JOptionPane.showMessageDialog(this, "Mail o contraseña incorrectos");
                //System.out.println("Mail o contraseña incorrectos");
                contador--;
                if(contador == 0)
                {
                    JOptionPane.showMessageDialog(this, "Ya no hay más posibilidades, " +
                            "Ingresando como invitado");

                    controlador.setUsuarioSesion(null);
                    //usuarioListo = true;
                    break;
                }
//                System.out.println("Le quedan " + contador + " posibilidades");
//                System.out.println("Presione x para volver atrás, o cualquier tecla para intentar de nuevo");
//                String respuesta = scanner.nextLine().toLowerCase();
//                if (respuesta.equals("x")) {
//                    break;
//                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Ingreso validado");
                //System.out.println("Ingreso validado");
                break;
            }
        }

    }

    private void onAtras() {
        // add your code here if necessary
        this.setVisible(false);
    }
}
