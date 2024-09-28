import modelo.dominio.Carrito;
import modelo.dominio.Pedido;
import modelo.repositorio.PedidoRepositorio;
import modelo.repositorio.ProductoRepositorio;
import modelo.repositorio.UsuarioRepositorio;
import modelo.servicio.CarritoServicio;
import modelo.servicio.PedidoServicio;
import modelo.servicio.ProductoServicio;
import vista.Menu;

public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenidos a la Venta On Line");

        Menu menu = new Menu();

        //repos simulados, A REEMPLAZAR POR BASE DE DATOS
        ProductoRepositorio productoRepositorio = new ProductoRepositorio();
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        PedidoRepositorio pedidoRepositorio = new PedidoRepositorio();

        //inicializar servicios
        ProductoServicio productoServicio = new ProductoServicio(productoRepositorio);
        CarritoServicio carritoServicio = new CarritoServicio();
        PedidoServicio pedidoServicio = new PedidoServicio();
        pedidoServicio.setPedidoRepositorio(pedidoRepositorio);

        //CASO DE USO 1: instancio carrito con usuario logueado
        Carrito carrito1 = new Carrito(usuarioRepositorio.getListaDeUsuarios().get(0));

        //proceso de compra
        carritoServicio.agregarProducto(carrito1, productoRepositorio.getListaDeProductos().get(0), 1);
        carritoServicio.mostrarCarrito(carrito1);
        carritoServicio.agregarProducto(carrito1, productoRepositorio.getListaDeProductos().get(
                productoRepositorio.getListaDeProductos().size()-1), 2);
        carritoServicio.mostrarCarrito(carrito1);

        //se confirma la compra
        Pedido pedido1 = new Pedido(carrito1);

        //se informa la compra
        System.out.println("Pedido confirmado: ");
        pedidoServicio.mostrarPedido(pedido1);

        //se agrega el pedido al repo
        pedidoServicio.agregarPedido(pedido1);

        //CASO DE USO 2: Compra sin usuario logueado
        Carrito carritoAnonimo = new Carrito();

        carritoServicio.agregarProducto(carritoAnonimo, productoRepositorio.getListaDeProductos().get(1), 3);
        carritoServicio.agregarProducto(carritoAnonimo, productoRepositorio.getListaDeProductos().get(2), 2);
        carritoServicio.mostrarCarrito(carritoAnonimo);

        Pedido pedido2 = new Pedido(carritoAnonimo);

        pedidoServicio.agregarPedido(pedido2);
        pedidoServicio.mostrarPedido(pedido2);

        //historial de pedidos
        //System.out.println(pedidoServicio.getListaPedidos());

        menu.muestraMenu();

    }
}