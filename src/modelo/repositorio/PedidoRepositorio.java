package modelo.repositorio;

import modelo.dominio.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositorio implements IPedidoRepositorio{

    private List<Pedido> listaPedidos = new ArrayList<>();

    @Override
    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    @Override
    public void agregarPedido(Pedido pedido)
    {
        listaPedidos.add(pedido);
    }
}
