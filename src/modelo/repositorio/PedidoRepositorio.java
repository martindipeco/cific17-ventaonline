package modelo.repositorio;

import modelo.dominio.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepositorio {

    private static List<Pedido> listaPedidos = new ArrayList<>();

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }
}
