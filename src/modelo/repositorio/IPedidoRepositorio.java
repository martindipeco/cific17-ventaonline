package modelo.repositorio;

import modelo.dominio.Pedido;

import java.util.List;

public interface IPedidoRepositorio {
    List<Pedido> getListaPedidos();
    public void agregarPedido(Pedido pedido);
}
