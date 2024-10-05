package vista;

import controlador.Controlador;
import modelo.dominio.*;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private Controlador controlador = new Controlador();

    Scanner scanner = new Scanner(System.in);

    public void muestraMenu()
    {
        System.out.println("Bienvenidos a Venta Online Cific17");
        String opcionUsuario;
        boolean usuarioListo = false;
        do {
            System.out.println("\n1: Soy usuario registrado");
            System.out.println("2: Quiero registrarme");
            System.out.println("3: Seguir como invitado");
            System.out.println("x: Salir");

            opcionUsuario= scanner.nextLine().toLowerCase();

            switch (opcionUsuario)
            {
                case "1":
                    menuUsuarioRegistrado();
                    usuarioListo = true;
                    break;
                case "2":
                    menuNuevoUsuario();
                    usuarioListo = true;
                    break;
                case "3":
                    System.out.println("Ingresando como invitado");
                    controlador.setUsuarioSesion(null);
                    usuarioListo = true;
                    break;
                case "x":
                    usuarioListo = true;
                    System.out.println("Adiós!");
                    break;
                default:
                    System.out.println("Por favor, ingrese una opción válida");
            }
        }
        while (!usuarioListo); //!"x".equalsIgnoreCase(opcionUsuario)

        //una vez conformada la opcion de usuario, instanciamos un carrito y se lo asignamos
        controlador.setCarritoSesion(new Carrito(controlador.getUsuarioSesion()));

        String opcion;
        do {
            System.out.println("\n1: Nueva consulta -> compra");
            System.out.println("2: Ver historial de pedidos");
            System.out.println("3: Imprimir comprobantes");
            System.out.println("x: Salir");

            opcion = scanner.nextLine().toLowerCase();

            switch (opcion)
            {
                //Nueva consulta
                case "1":
                    String eleccion;
                    do {
                        System.out.println("Opciones disponibles:\n");
                        System.out.println("1: Listar todos los productos");
                        System.out.println("2: Buscar por nombre producto");
                        System.out.println("3: Buscar por categoría");
                        System.out.println("4: Buscar por precio");
                        System.out.println("5: Buscar por código");
                        System.out.println("6: Ver Carrito");
                        System.out.println("7: Confirmar compra");
                        System.out.println("x: Volver atrás");

                        eleccion = scanner.nextLine().toLowerCase();
                        boolean convertir = true;
                        switch (eleccion)
                        {
                            case "1":
                                System.out.println("Listando todos los productos en tandas");
                                int numPagina = 1;
                                while (true) {

                                    // Obtener la paginacion correspondiente
                                    List<Producto> tanda = controlador.getProductoServicio()
                                            .listar5productosPorPagina(numPagina);

                                    // si la tanda está vacía, llegamos al final
                                    if (tanda.isEmpty()) {
                                        System.out.println("Fin de la lista");
                                        break;
                                    }

                                    System.out.println("tanda " + numPagina + ": " + tanda);
                                    System.out.println("Presione cualquier tecla para continuar...");
                                    scanner.nextLine();
                                    numPagina++;
                                }
                                menuCompraPorCodigo();
                                break;
                            case "2":
                                System.out.println("Ingrese nombre de producto");
                                String nombreProducto = scanner.nextLine();
                                System.out.println(controlador.getProductoServicio().buscarPorNombre(nombreProducto));

                                menuCompraPorCodigo();

                                break;
                            case "3":
                                System.out.println("Seleccione una categoría:");
                                ProductoCategoria[] categorias = ProductoCategoria.values();
                                for (int i = 0; i < categorias.length; i++) {
                                    System.out.println((i + 1) + ". " + categorias[i]);
                                }

                                // Pedir input de categoria
                                int opcionCategoria = -1;
                                while (opcionCategoria < 1 || opcionCategoria > categorias.length) {
                                    System.out.print("Ingrese el número de la categoría: ");
                                    if (scanner.hasNextInt()) {
                                        opcionCategoria = scanner.nextInt();
                                        if (opcionCategoria < 1 || opcionCategoria > categorias.length) {
                                            System.out.println("Por favor, ingrese un número válido.");
                                        }
                                    } else {
                                        System.out.println("Entrada no válida. Por favor ingrese un número.");
                                        scanner.next(); // Limpiar opción no válida
                                    }
                                }

                                // Convertir input a correspondente ProductoCategoria enum
                                ProductoCategoria categoriaElegida = categorias[opcionCategoria - 1];
                                System.out.println(controlador.getProductoServicio().buscarPorCategoria(categoriaElegida));
                                //limpiar scanner
                                scanner.nextLine();

                                menuCompraPorCodigo();

                                break;
                            case "4":
                                System.out.println("Buscando por precio");
                                try
                                {
                                    System.out.println("Ingrese precio mínimo");
                                    Float precioMin = Float.parseFloat(scanner.nextLine());
                                    System.out.println("Ingrese precio máximo");
                                    Float precioMax = Float.parseFloat(scanner.nextLine());
                                    System.out.println(controlador.getProductoServicio()
                                            .buscarPorRangoPrecio(precioMin, precioMax));

                                    menuCompraPorCodigo();

                                }
                                catch (NumberFormatException e)
                                {
                                    System.out.println("Por favor ingrese un número válido");
                                }
                                break;
                            case "5":
                                menuCompraPorCodigo();
                                break;
                            case "6":
                                System.out.println("Mostrando Carrito");
                                controlador.getCarritoSesion().mostrarCarrito();
                                if(!controlador.getCarritoSesion().getListaItems().isEmpty())
                                {
                                    String eleccionCarrito;
                                    do {
                                        System.out.println("\n1: Agregar unidades");
                                        System.out.println("2: Quitar productos");
                                        System.out.println("3: Confirmar compra");
                                        System.out.println("x: Atrás");
                                        eleccionCarrito = scanner.nextLine();

                                        switch(eleccionCarrito){
                                            case "1":
                                                //redirijo a menu anterior para seguir comprando
                                                menuCompraPorCodigo();
                                                break;
                                            case "2":
                                                //listar items en carrito
                                                menuQuitarPorCodigo();
                                                break;
                                            case "3":
                                                menuConfirmarCompra();
                                                break;
                                            case "x":
                                                break;
                                            default:
                                                System.out.println("Por favor ingrese una opción válida");
                                        }
                                    }
                                    while (!eleccionCarrito.equalsIgnoreCase("x"));
                                }
                                break;
                            case "7":
                                menuConfirmarCompra();
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
                        //System.out.println("3: Ordenar por Usuario"); //antes no había log in
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
//                            case "3":
//                                System.out.println("Ordenado por Usuario");
//                                controlador.getPedidoServicio().ordenarPorUsuario();
//                                controlador.getPedidoServicio().listarPedidos();
//                                break;
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
                    System.out.println("La funcionalidad de impresión está en desarrollo");
                    //generadorDeArchivo.guardarListaTransaccionTxt(usuario.getListaTransacciones());
                    System.out.println("Presione cualquier tecla para continuar");
                    scanner.nextLine();
                    break;
                case "x":
                    System.out.println("Gracias por su visita. Vuelva pronto al Cific17!");
                    break;
                default:
                    System.out.println("Por favor ingrese una opción válida");
            }
        }
        while (!"x".equalsIgnoreCase(opcion));
        scanner.close();
    }

    public void menuUsuarioRegistrado()
    {
        int contador = 3;
        while(contador > 0)
        {
            System.out.println("Ingrese su mail");
            String mailUsuario = scanner.nextLine();
            System.out.println("Ingrese su contraseña");
            String passUsuario = scanner.nextLine();
            //chequear contra repo si usuario existe. si existe, instanciarlo a través de controlador
            controlador.setUsuarioSesion(controlador.getUsuarioServicio().logUsuario(mailUsuario, passUsuario));
            if (controlador.getUsuarioSesion()==null)
            {
                //si no existe, informar , restar una posibilidad y preguntar si desea crear nuevo usuario
                System.out.println("Mail o contraseña incorrectos");
                contador--;
                if(contador == 0)
                {
                    System.out.println("Ya no hay más posibilidades");
                    System.out.println("Ingresando como invitado");
                    controlador.setUsuarioSesion(null);
                    //usuarioListo = true;
                    break;
                }
                System.out.println("Le quedan " + contador + " posibilidades");
                System.out.println("Presione x para volver atrás, o cualquier tecla para intentar de nuevo");
                String respuesta = scanner.nextLine().toLowerCase();
                if (respuesta.equals("x")) {
                    break;
                }
            }
            else
            {
                //usuarioListo = true;
                System.out.println("Ingreso validado");
                break;
            }
        }
    }

    public void menuNuevoUsuario()
    {
        System.out.println("Ingrese nuevo mail");
        String mailNuevoUsuario = scanner.nextLine();
        //chequear contra repo si usuario existe.
        if(controlador.getUsuarioServicio().buscarUsuario(mailNuevoUsuario)!= null)
        {
            System.out.println("El usuario ya existe. Inicie sesión con sus credenciales.");
        }
        else
        {
            //pedimos el resto de los datos
            System.out.println("Ingrese una contraseña");
            String passNuevoUsuario = scanner.nextLine();
            System.out.println("Ingrese su nombre");
            String nombreNuevoUsuario = scanner.nextLine();
            System.out.println("Ingrese su dirección");
            String direccionNuevoUsuario = scanner.nextLine();
            boolean tarjetaOK = false;
            Long numTarjetaNuevoUsuario = 0l;
            do {
                System.out.println("Ingrese el número de su tarjeta de crédito");
                String numTarjetaString = scanner.nextLine();
                try
                {
                    numTarjetaNuevoUsuario = Long.parseLong(numTarjetaString);
                    tarjetaOK = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("número no válido");
                }
            }
            while (!tarjetaOK);
            controlador.setUsuarioSesion(new Usuario(mailNuevoUsuario, passNuevoUsuario, nombreNuevoUsuario,
                    direccionNuevoUsuario, numTarjetaNuevoUsuario));
            //usuarioListo = true;
            System.out.println("Nuevo usuario registrado con éxito");
        }
    }

    public void menuCompraPorCodigo()
    {
        System.out.println("Ingrese código para agregar producto o x para volver atrás");
        String codigoString = scanner.nextLine();
        if(codigoString.equalsIgnoreCase("x"))
        {
            return;
        }
        try
        {
            int codigo = Integer.parseInt(codigoString);
            Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigo);
            if(producto == null)
            {
                System.out.println("No hay producto con código " + codigo);
                return;
            }
            String confirmaCompra;
            do {
                System.out.println("Presione cualquier tecla para agregar: ");
                System.out.println(producto);
                System.out.println("O presione x para volver atrás");
                confirmaCompra = scanner.nextLine().toLowerCase();
                if(!confirmaCompra.equals("x"))
                {
                    //agrego producto al carrito
                    controlador.getCarritoServicio().agregarProductoX1(
                            controlador.getCarritoSesion(), producto);
                    System.out.println("El producto se agregó exitosamente al carrito");
                    break;
                }
            }
            while (!"x".equalsIgnoreCase(confirmaCompra));
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ingrese un número válido");
        }
    }

    public void menuQuitarPorCodigo()
    {
        System.out.println("Ingrese código de producto para quitarlo del carrito o x para volver atrás");
        String codigoString = scanner.nextLine();
        if(codigoString.equalsIgnoreCase("x"))
        {
            return;
        }
        try
        {
            int codigo = Integer.parseInt(codigoString);
            Producto producto = controlador.getProductoServicio().buscarPorCodigo(codigo);
            if(producto == null)
            {
                System.out.println("No hay producto con código " + codigo);
                return;
            }
            String confirmaQuita;
            do {
                System.out.println("Presione cualquier tecla para quitar: ");
                System.out.println(producto);
                System.out.println("O presione x para volver atrás");
                confirmaQuita = scanner.nextLine().toLowerCase();
                if(!confirmaQuita.equals("x"))
                {
                    //Quito al producto del carrito
                    controlador.getCarritoServicio().quitarProductoX1(
                            controlador.getCarritoSesion(), producto);
                    System.out.println("El producto se quitó exitosamente del carrito");
                    break;
                }
            }
            while (!"x".equalsIgnoreCase(confirmaQuita));
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ingrese un número válido");
        }
    }

    public void menuConfirmarCompra()
    {
        if(controlador.getCarritoSesion().getListaItems().isEmpty())
        {
            System.out.println("El carrito está vacío.");
            System.out.println("Agregue al menos un producto");
        }
        else
        {
            if (controlador.getCarritoSesion().getUsuario()==null)
            {
                System.out.println("Es necesario crear usuario o loguearse");
                menuNuevoUsuario();
            }
            System.out.println("Confirmando compra");
            //instancio pedido a partir de carrito actual
            controlador.getPedidoServicio().agregarPedido(new Pedido(controlador.getCarritoSesion()));
            //muestro el pedido, el último en la lista x q acaba de agregarse
            System.out.println(controlador.getPedidoServicio().getListaPedidos().get(
                    controlador.getPedidoServicio().getListaPedidos().size()-1));
            //reiniciamos el carrito
            controlador.setCarritoSesion(new Carrito(controlador.getUsuarioSesion()));
        }
    }
}
