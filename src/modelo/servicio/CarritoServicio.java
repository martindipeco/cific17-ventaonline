package modelo.servicio;

import modelo.dominio.Carrito;
import modelo.dominio.Producto;

import java.util.List;

public class CarritoServicio {

    public void agregarProducto(Carrito carrito, Producto producto, Integer cantidad)
    {
        //chequeo si el producto está en stock
        if(producto.getStock()<1)
        {
            System.out.println("No hay stock del producto " + producto);
            return;
        }
        carrito.getListaProductos().put(producto, cantidad);
        producto.setStock(producto.getStock()-1);
        carrito.setMontoCarrito(carrito.getMontoCarrito()+(cantidad* producto.getPrecio())); //falta aplicar descuento
    }

    public void quitarProducto(Carrito carrito, Producto producto)
    {
        //TODO
    }
}
