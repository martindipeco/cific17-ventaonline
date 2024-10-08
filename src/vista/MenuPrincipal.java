package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.event.*;

public class MenuPrincipal extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonUsuarioRegistrado;
    private JButton buttonCrearUsuario;
    private JButton buttonIngresoInvitado;

    public MenuPrincipal() {
        //instancio un controlador
        Controlador controlador = new Controlador();

        setContentPane(contentPane);
        setModal(true);
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

    private void onOK() {
        // este botón no hace nada
        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onUsuarioRegistrado()
    {
        //TODO: abrir menu usuario registrado
    }

    private void onCrearUsuario()
    {
        //TODO menu crear usuario
    }

    private void onIngresoInvitado()
    {
        //TODO ir a menu iniciar compra
    }

    public static void main(String[] args) {
        MenuPrincipal dialog = new MenuPrincipal();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
