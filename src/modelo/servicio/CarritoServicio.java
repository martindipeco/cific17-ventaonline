package modelo.servicio;

import modelo.dominio.Carrito;
import modelo.dominio.Producto;

import java.util.List;

public class CarritoServicio {

    public void agregarProducto(Carrito carrito, Producto producto, Integer cantidad)
    {
        carrito.getListaProductos().put(producto, cantidad);
    }

    public void quitarProducto(Carrito carrito, Producto producto)
    {
        //TODO
    }
}
