package vista;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

public class MenuPrincipal extends JDialog {
    private JPanel contentPane;
    //private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonUsuarioRegistrado;
    private JButton buttonCrearUsuario;
    private JButton buttonIngresoInvitado;

    public MenuPrincipal() {

        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);

        // Set the custom icon from a URL
        setCustomIconFromURL();

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonUsuarioRegistrado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUsuarioRegistrado();
            }
        });

        buttonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCrearUsuario();
            }
        });

        buttonIngresoInvitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onIngresoInvitado();
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

    private void onCancel() {
        dispose();
    }

    private void onUsuarioRegistrado()
    {
        MenuUsuarioRegistrado menuUsuarioRegistrado = new MenuUsuarioRegistrado();
        menuUsuarioRegistrado.setVisible(true);
        this.setVisible(false);
    }

    private void onCrearUsuario()
    {
        MenuUsuarioNuevo menuUsuarioNuevo = new MenuUsuarioNuevo();
        menuUsuarioNuevo.setVisible(true);
        this.setVisible(false);
    }

    private void onIngresoInvitado()
    {
        MenuCompra menuCompra = new MenuCompra();
        menuCompra.setVisible(true);
        this.setVisible(false);
    }

    private void setCustomIconFromURL() {
        try {
            // Provide the URL of the image
            URL iconURL = new URL("https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgymuTKREEiWvDejTp4tlycIUDzdM8Yg7JgcQ_RcjajsFWV1bT3zB74JkBDZYiTA18d_aMWYmdVNzw6N2NofzOhuWkFW-JqfRyclGK1lluCjMqWLGuY9yviYWTlByU0zKu-gqCb9s226Ss/s1600/uocra.jpg");
            ImageIcon customIcon = new ImageIcon(iconURL);
            setIconImage(customIcon.getImage());
        } catch (Exception e) {
            System.err.println("Failed to load icon from URL: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MenuPrincipal dialog = new MenuPrincipal();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
