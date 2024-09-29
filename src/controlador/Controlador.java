package controlador;

import modelo.repositorio.ProductoRepositorio;
import modelo.repositorio.UsuarioRepositorio;
import modelo.servicio.CarritoServicio;
import modelo.servicio.PedidoServicio;
import modelo.servicio.ProductoServicio;

public class Controlador {

    CarritoServicio carritoServicio = new CarritoServicio();
    PedidoServicio pedidoServicio = new PedidoServicio();
    ProductoServicio productoServicio = new ProductoServicio(new ProductoRepositorio());
    UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();

    public CarritoServicio getCarritoServicio() {
        return carritoServicio;
    }

    public PedidoServicio getPedidoServicio() {
        return pedidoServicio;
    }

    public ProductoServicio getProductoServicio() {
        return productoServicio;
    }
}
