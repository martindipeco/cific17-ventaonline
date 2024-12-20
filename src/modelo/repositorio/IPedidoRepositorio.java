package modelo.repositorio;

import modelo.dominio.Carrito;
import modelo.dominio.Pedido;
import modelo.dominio.Usuario;

import java.util.List;

public interface IPedidoRepositorio {
    List<Pedido> getListaPedidos();
    public void agregarPedido(Pedido pedido);
    public List<Pedido> getPedidosDeUsuario (Usuario usuario);
    public Pedido getUltimoPedidoDeUsuario (Usuario usuario);
}
