package modelo.repositorio;

import modelo.dominio.Carrito;
import modelo.dominio.Pedido;
import modelo.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositorioMock implements IPedidoRepositorio{

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

    @Override
    public List<Pedido> getPedidosDeUsuario(Usuario usuario) {
        return null;
    }

}
