package modelo.servicio;

import modelo.dominio.Pedido;
import modelo.repositorio.PedidoRepositorio;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PedidoServicio {

    private PedidoRepositorio pedidoRepositorio;

    public PedidoServicio(PedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    public PedidoServicio()
    {

    }

    public void setPedidoRepositorio(PedidoRepositorio pedidoRepositorio)
    {
        this.pedidoRepositorio = pedidoRepositorio;
    }
    public void agregarPedido(Pedido pedido)
    {
        pedidoRepositorio.getListaPedidos().add(pedido);
    }

    public List<Pedido> getListaPedidos()
    {
        return pedidoRepositorio.getListaPedidos();
    }

    //Emite factura
    public void mostrarPedido(Pedido pedido)
    {
        System.out.println("Pedido num: " + pedido.getNumPedido() +
                ". Fecha: " + pedido.getFechaPedido().format(DateTimeFormatter.ISO_DATE) +
                ". Cliente: " + pedido.getCarrito().getUsuario());
        pedido.getCarrito().mostrarCarrito();
    }
}
