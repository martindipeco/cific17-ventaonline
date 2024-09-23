import modelo.dominio.Carrito;
import modelo.dominio.Pedido;
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

        Carrito carrito1 = new Carrito(usuarioRepositorio.getListaDeUsuarios().get(0));

        CarritoServicio servicioCarro = new CarritoServicio();

        servicioCarro.agregarProducto(carrito1, productoRepositorio.getStockDeProductos().get(0), 1);

        //se confirma la compra
        Pedido pedido1 = new Pedido(carrito1);

        //se informa la compra
        System.out.println("Pedido confirmado: " + pedido1);

        //se agrega el pedido al repo
        PedidoServicio pedidoServicio = new PedidoServicio();
        pedidoServicio.agregarPedido(pedido1);

        //historial de pedidos


    }
}