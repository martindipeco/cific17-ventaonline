package modelo.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carrito {
    private Usuario usuario;
    private Map<Producto, Integer> listaProductos; //Map<Key, Value>
    private ItemCompra itemCompra;
    private List<ItemCompra> listaItems;
    private float montoCarrito;

    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        listaProductos = new HashMap<>();
        listaItems = new ArrayList<>();
    }

    //crear un carrito con usuario anonimo
    public Carrito()
    {
        this.usuario = null;
        this.listaProductos = new HashMap<>();
        listaItems = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Map<Producto, Integer> getListaProductos() {
        return listaProductos;
    }
    public List<ItemCompra> getListaItems() {return listaItems;}

    public float getMontoCarrito() {
        return montoCarrito;
    }

    public void setMontoCarrito(float montoCarrito) {
        this.montoCarrito = montoCarrito;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "usuario=" + usuario +
                ", listaProductos=" + listaProductos +
                ", monto=" + montoCarrito +
                '}';
    }
}
