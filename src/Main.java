import modelo.dominio.Carrito;
import modelo.dominio.Pedido;
import modelo.repositorio.PedidoRepositorio;
import modelo.repositorio.ProductoRepositorio;
import modelo.repositorio.UsuarioRepositorio;
import modelo.servicio.CarritoServicio;
import modelo.servicio.PedidoServicio;

public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenidos a la Venta On Line");

        //repos simulados, A REEMPLAZAR POR BASE DE DATOS
        ProductoRepositorio productoRepositorio = new ProductoRepositorio();
        UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();
        PedidoRepositorio pedidoRepositorio = new PedidoRepositorio();

        CarritoServicio servicioCarro = new CarritoServicio();

        //CASO DE USO 1: instancio carrito con usuario logueado
        Carrito carrito1 = new Carrito(usuarioRepositorio.getListaDeUsuarios().get(0));

        //proceso de compra
        servicioCarro.agregarProducto(carrito1, productoRepositorio.getListaDeProductos().get(0), 1);

        servicioCarro.agregarProducto(carrito1, productoRepositorio.getListaDeProductos().get(
                productoRepositorio.getListaDeProductos().size()-1), 2);

        //TODO: contemplar caso de uso eliminar producto de lista

        //se confirma la compra
        Pedido pedido1 = new Pedido(carrito1);

        //se informa la compra
        System.out.println("Pedido confirmado: " + pedido1);

        //se agrega el pedido al repo
        PedidoServicio pedidoServicio = new PedidoServicio(pedidoRepositorio);
        pedidoServicio.agregarPedido(pedido1);

        //historial de pedidos
        System.out.println(pedidoServicio.getListaPedidos());

        //CASO DE USO 2: Compra sin usuario logueado
        Carrito carritoAnonimo = new Carrito();

        servicioCarro.agregarProducto(carritoAnonimo, productoRepositorio.getListaDeProductos().get(1), 3);
        servicioCarro.agregarProducto(carritoAnonimo, productoRepositorio.getListaDeProductos().get(2), 2);

        Pedido pedido2 = new Pedido(carritoAnonimo);

        pedidoServicio.agregarPedido(pedido2);

        System.out.println(pedidoServicio.getListaPedidos());

    }
}