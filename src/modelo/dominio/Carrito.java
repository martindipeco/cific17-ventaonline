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

    //crear carrito con usuario e items
    public Carrito(Usuario usuario, List<ItemCompra> listaItems)
    {
        this.usuario = usuario;
        this.listaItems = listaItems;
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void mostrarCarrito()
    {
        System.out.println("Items de compra: ");
        for(ItemCompra item : listaItems)
        {
            System.out.println(item);
        }
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
