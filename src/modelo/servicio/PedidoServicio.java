package modelo.servicio;

import modelo.dominio.Pedido;
import modelo.repositorio.PedidoRepositorio;

public class PedidoServicio {

    private PedidoRepositorio pedidoRepositorio;

    public PedidoServicio(PedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    public void agregarPedido(Pedido pedido)
    {
        pedidoRepositorio.getListaPedidos().add(pedido);
    }
}
