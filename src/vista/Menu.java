package vista;

import controlador.Controlador;
import modelo.dominio.Producto;

import java.util.Scanner;

public class Menu {

    private Controlador controlador = new Controlador();

    public void muestraMenu()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenidos a Venta Online Cific17");
        String opcion;
        do {
            System.out.println("\n1: Nueva consulta/ operación");
            System.out.println("2: Ver historial");
            System.out.println("3: Guardar/ Imprimir");
            System.out.println("x: Salir");

            opcion = scanner.nextLine().toLowerCase();

            switch (opcion)
            {
                //Nueva consulta
                case "1":
                    String eleccion;
                    do {
                        System.out.println("Opciones disponibles:");
                        System.out.println("1: Buscar por nombre producto");
                        System.out.println("2: Buscar por categoría");
                        System.out.println("3: Buscar por precio");
                        System.out.println("4: Buscar por código");
                        System.out.println("x: atrás");

                        eleccion = scanner.nextLine().toLowerCase();
                        boolean convertir = true;
                        switch (eleccion)
                        {
                            case "1":
                                System.out.println("Buscando por nombre de producto");
                                //TODO
                                break;
                            case "2":
                                System.out.println("Buscando por categoría");
                                //TODO
                                break;
                            case "3":
                                //TODO
                                System.out.println("Buscando por precio");
                                break;
                            case "4":
                                //TODO
                                System.out.println("Buscando por código");
                                System.out.println("Ingrese el código");
                                String codigoString = scanner.nextLine();
                                try
                                {
                                    Integer codigo = Integer.parseInt(codigoString);
                                    Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigo);
                                    System.out.println(producto);
                                }
                                catch (NumberFormatException e)
                                {
                                    System.out.println("Ingrese un número válido");
                                }
                                break;
                            case "x":
                                System.out.println("Volviendo atrás");
                                break;
                            default:
                                System.out.println("Por favor ingrese una opción válida");
                        }
                    }
                    while (!"x".equalsIgnoreCase(eleccion));
                    break;

                //Historial
                case "2":
                    System.out.println("Vemos el historial");
                    String eleccionOrden;
                    do {
                        System.out.println("1: Ordenar por Fecha más próxima");
                        System.out.println("2: Ordenar por Fecha más antigua");
                        System.out.println("3: Ordenar por Monto descendente");
                        System.out.println("4: Ordenar por Monto ascendente");
                        System.out.println("5: Ordenar por Nombre de usuario");
                        System.out.println("x: Volver atrás");

                        eleccionOrden = scanner.nextLine();

                        switch (eleccionOrden)
                        {
                            case "1":
                                System.out.println("Ordenada por Fecha próxima");
                                //usuario.ordenaPorFechaProxima();
                                break;
                            case "2":
                                System.out.println("Ordenada por Fecha más antigua");
                                //usuario.ordenaPorFechaAntigua();
                                break;
                            case "3":
                                System.out.println("Ordenada por Monto descendente");
                                //usuario.ordenaPorMontoMonedaOrigen();
                                break;
                            case "4":
                                System.out.println("Ordenada por Monto ascendente");
                                //usuario.ordenaPorMontoMonedaDestino();
                                break;
                            case "5":
                                System.out.println("Ordenada por Nombre de usuario");
                                //usuario.ordenaPorNombreMonedaOrigen();
                                break;
                            case "x":
                                System.out.println("Volviendo atrás");
                                break;
                            default:
                                System.out.println("Por favor ingrese una opción válida");
                        }
                    }
                    while (!eleccionOrden.equalsIgnoreCase("x"));
                    break;

                //Guardar
                case "3":
                    System.out.println("La funcionalidad de guardado e impresión está en desarrollo");
                    //generadorDeArchivo.guardarListaTransaccionTxt(usuario.getListaTransacciones());
                    System.out.println("Presione cualquier tecla para continuar");
                    scanner.nextLine();
                    break;
                case "x":
                    System.out.println("Gracias por usar el sistema");
                    break;
                default:
                    System.out.println("Por favor ingrese una opción válida");
            }
        }
        while (!"x".equalsIgnoreCase(opcion));
        scanner.close();
    }
}
