package modelo.servicio;

import modelo.dominio.Pedido;
import modelo.repositorio.IPedidoRepositorio;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PedidoServicio {

    private IPedidoRepositorio pedidoRepositorio;

    public PedidoServicio(IPedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    public void setPedidoRepositorio(IPedidoRepositorio pedidoRepositorio)
    {
        this.pedidoRepositorio = pedidoRepositorio;
    }
    public void agregarPedido(Pedido pedido)
    {
        pedidoRepositorio.agregarPedido(pedido);
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

    public void listarPedidos()
    {
        for(Pedido pedido : pedidoRepositorio.getListaPedidos())
        {
            System.out.println(pedido);
        }
    }

    public void ordenarPorMonto()
    {
        pedidoRepositorio.getListaPedidos().sort((pedido1, pedido2) ->
                pedido1.getPrecioFinal().compareTo(pedido2.getPrecioFinal()));
    }

    public void ordenarPorFecha()
    {
        pedidoRepositorio.getListaPedidos().sort((pedido1, pedido2) ->
                pedido1.getFechaPedido().compareTo(pedido2.getFechaPedido()));
    }

    public void ordenarPorUsuario()
    {
        pedidoRepositorio.getListaPedidos().sort((pedido1, pedido2) ->
                pedido1.getCarrito().getUsuario().getMail().compareTo(pedido2.getCarrito().getUsuario().getMail()));
    }
}
