package modelo.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carrito {
    private Usuario usuario;
    private Map<Producto, Integer> listaProductos; //Map<Key, Value>

    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        listaProductos = new HashMap<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Map<Producto, Integer> getListaProductos() {
        return listaProductos;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "usuario=" + usuario +
                ", listaProductos=" + listaProductos +
                '}';
    }
}
