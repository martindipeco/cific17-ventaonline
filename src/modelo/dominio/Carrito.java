package modelo.dominio;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private Usuario usuario;
    private List<ItemCompra> listaItems;
    private float montoCarrito;

    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        listaItems = new ArrayList<>();
    }

    //crear un carrito con usuario anonimo
    public Carrito()
    {
        this.usuario = null;
        listaItems = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
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
                ", lista de Items=" + listaItems +
                ", monto=" + montoCarrito +
                '}';
    }
}
