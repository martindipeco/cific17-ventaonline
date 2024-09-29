package vista;

import controlador.Controlador;
import modelo.dominio.Carrito;
import modelo.dominio.Producto;
import modelo.dominio.Pedido;
import modelo.dominio.Usuario;

import java.util.Comparator;
import java.util.Scanner;

public class Menu {

    private Controlador controlador = new Controlador();
    private Usuario usuario;

    public void muestraMenu()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenidos a Venta Online Cific17");
        //TODO: preguntar por usuario registrado o crear usuario
        //TODO: 1 soy usuario registrado -> ingresar mail y pass, chequear contra repo de usuarios
        //TODO: 2 crear nuevo usuario -> instanciar usuario con mail y pass, agregandolo a la lista del repo
        String opcion;
        do {
            System.out.println("\n1: Nueva consulta/ operación");
            System.out.println("2: Ver historial de pedidos");
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
                                    String confirmaCompra;
                                    do {
                                        System.out.println("Presione cualquier tecla para confirmar la compra de: ");
                                        System.out.println(producto);
                                        System.out.println("O presione x para volver atrás");
                                        confirmaCompra = scanner.nextLine().toLowerCase();
                                        if(!confirmaCompra.equals("x"))
                                        {
                                            //agrego producto al carrito
                                            controlador.getCarritoServicio().agregarProductoX1(new Carrito(), producto);
                                        }
                                    }
                                    while (!"x".equalsIgnoreCase(confirmaCompra));
                                    break;
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
                        System.out.println("1: Ordenar por Fecha");
                        System.out.println("2: Ordenar por Monto");
                        System.out.println("3: Ordenar por Usuario");
                        System.out.println("x: Volver atrás");

                        eleccionOrden = scanner.nextLine();

                        switch (eleccionOrden)
                        {
                            case "1":
                                System.out.println("Ordenado por Fecha");
                                controlador.getPedidoServicio().getListaPedidos().stream()
                                        .sorted(Comparator.comparing(Pedido::getFechaPedido))
                                        .forEach(pedido -> controlador.getPedidoServicio().mostrarPedido(pedido));
                                break;
                            case "2":
                                System.out.println("Ordenado por Monto");
                                controlador.getPedidoServicio().getListaPedidos().stream()
                                        .sorted(Comparator.comparing(Pedido::getPrecioFinal))
                                        .forEach(pedido -> controlador.getPedidoServicio().mostrarPedido(pedido));
                                break;
                            case "3":
                                System.out.println("Ordenado por Usuario");
                                controlador.getPedidoServicio().ordenarPorUsuario();
                                controlador.getPedidoServicio().listarPedidos();
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
