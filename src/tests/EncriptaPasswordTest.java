package tests;

import controlador.Controlador;
import modelo.dominio.Usuario;

import java.util.Scanner;

public class EncriptaPasswordTest {



    public static void main(String[] args)
    {
        testeoDirecto();

        testeoSimulaConsola();

    }

    public static void testeoDirecto()
    {
        Controlador controlador = Controlador.getInstanciaUnicaControlador();
        //given
        String passUsuario = "pe2";
        String passEncriptado = controlador.getEncriptaServicio().encriptaHash(passUsuario);
        Usuario usuarioTest1 = new Usuario("Pe@mail.com", passEncriptado, "Pedro",
                "Aca a la vuelta", "123");

        //when
        System.out.println("El pass que ingresó el usuario es: "  + passUsuario);
        System.out.println("El pass en BD es: " + usuarioTest1.getPassword());

        //then
        System.out.println(passUsuario + " no es igual a " + usuarioTest1.getPassword());
    }

    public static void testeoSimulaConsola()
    {
        Scanner scanner = new Scanner(System.in);
        Controlador controlador = Controlador.getInstanciaUnicaControlador();

        System.out.println("Ingrese nuevo mail");
        String mailNuevoUsuario = scanner.nextLine();
        //chequear contra repo si usuario existe.
        if(controlador.getUsuarioServicio().buscarUsuario(mailNuevoUsuario)!= null)
        {
            System.out.println("El usuario ya existe. Inicie sesión con sus credenciales.");
            //menuUsuarioRegistrado();
        }
        else {
            //pedimos el resto de los datos
            System.out.println("Ingrese una contraseña");
            String passNuevoUsuario = scanner.nextLine();
            System.out.println("Ingrese su nombre");
            String nombreNuevoUsuario = scanner.nextLine();
            System.out.println("Ingrese su dirección");
            String direccionNuevoUsuario = scanner.nextLine();
            boolean tarjetaOK = false;
            String numTarjetaString = "";
            Long numTarjetaNuevoUsuario = 0l;
            do {
                System.out.println("Ingrese el número de su tarjeta de crédito");
                numTarjetaString = scanner.nextLine();
                try {
                    numTarjetaNuevoUsuario = Long.parseLong(numTarjetaString);
                    tarjetaOK = true;
                } catch (NumberFormatException e) {
                    System.out.println("número no válido");
                }
            }
            while (!tarjetaOK);
            //la contraseña la guardo encriptada
            //la contraseña y el num de tarjeta los guardo encriptados
            String passEncriptado = controlador.getEncriptaServicio().encriptaHash(passNuevoUsuario);
            String tarjetaEncriptada = controlador.getEncriptaServicio().encriptaHash(numTarjetaString);

            controlador.setUsuarioSesion(new Usuario(mailNuevoUsuario, passEncriptado, nombreNuevoUsuario,
                    direccionNuevoUsuario, tarjetaEncriptada));
            System.out.println("Nuevo usuario registrado con éxito");
            System.out.println("El usuario ingresó como pass: " + passNuevoUsuario);
            System.out.println("En base se guardó: " + controlador.getUsuarioSesion().getPassword());
            System.out.println("El usuario ingresó como numero de tarjeta: " + numTarjetaString);
            System.out.println("En base se guardó: " + controlador.getUsuarioSesion().getNumTarjeta());
        }
    }

}
