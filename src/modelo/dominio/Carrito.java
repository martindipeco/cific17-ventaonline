package modelo.dominio;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private Usuario usuario;
    private List<Producto> listaProductos;

    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        listaProductos = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Producto> getListaProductos() {
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
