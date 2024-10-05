package controlador;

import modelo.dominio.Carrito;
import modelo.dominio.Usuario;
import modelo.repositorio.ProductoRepositorio;
import modelo.servicio.CarritoServicio;
import modelo.servicio.PedidoServicio;
import modelo.servicio.ProductoServicio;
import modelo.servicio.UsuarioServicio;

public class Controlador {

    CarritoServicio carritoServicio = new CarritoServicio();
    PedidoServicio pedidoServicio = new PedidoServicio();
    ProductoServicio productoServicio = new ProductoServicio(new ProductoRepositorio());
    UsuarioServicio usuarioServicio = new UsuarioServicio();
    //Usuario usuarioSesion = new Usuario();
    Carrito carritoSesion = new Carrito();

    public CarritoServicio getCarritoServicio() {
        return carritoServicio;
    }

    public PedidoServicio getPedidoServicio() {
        return pedidoServicio;
    }

    public ProductoServicio getProductoServicio() {
        return productoServicio;
    }

    public UsuarioServicio getUsuarioServicio() {return usuarioServicio; }

    public Usuario getUsuarioSesion() {
        return getCarritoSesion().getUsuario();
    }
    public void setUsuarioSesion (Usuario usuario)
    {
        this.carritoSesion.setUsuario(usuario);
    }

    public Carrito getCarritoSesion() {
        return carritoSesion;
    }

    public void setCarritoSesion(Carrito carritoSesion) {
        this.carritoSesion = carritoSesion;
    }
}
