package controlador;

import modelo.dominio.Carrito;
import modelo.dominio.Usuario;
import modelo.repositorio.ProductoRepositorio;
import modelo.servicio.*;

public class Controlador {

    CarritoServicio carritoServicio = new CarritoServicio();
    PedidoServicio pedidoServicio = new PedidoServicio();
    ProductoServicio productoServicio = new ProductoServicio(new ProductoRepositorio());
    UsuarioServicio usuarioServicio = new UsuarioServicio();
    Carrito carritoSesion = new Carrito();
    EncriptaServicio encriptaServicio = new EncriptaServicio();

    ValidadorServicio validadorServicio = new ValidadorServicio();

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

    public EncriptaServicio getEncriptaServicio() {
        return encriptaServicio;
    }
    public ValidadorServicio getValidadorServicio() {
        return validadorServicio;
    }
}
