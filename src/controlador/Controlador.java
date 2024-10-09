package controlador;

import modelo.dominio.Carrito;
import modelo.dominio.Usuario;
import modelo.repositorio.ProductoRepositorio;
import modelo.servicio.*;

public class Controlador {

    // Static instance of Controlador (Singleton)
    private static Controlador instanciaUnicaControlador;

    // Private constructor to prevent external instantiation
    private Controlador() {
        carritoServicio = new CarritoServicio();
        pedidoServicio = new PedidoServicio();
        productoServicio = new ProductoServicio(new ProductoRepositorio());
        usuarioServicio = new UsuarioServicio();
        carritoSesion = new Carrito();
        encriptaServicio = new EncriptaServicio();
        validadorServicio = new ValidadorServicio();
    }

    // Public method to return the single instance of Controlador
    public static Controlador getInstanciaUnicaControlador() {
        if (instanciaUnicaControlador == null) {
            instanciaUnicaControlador = new Controlador();
        }
        return instanciaUnicaControlador;
    }

    // Services and session-related objects
    CarritoServicio carritoServicio = new CarritoServicio();
    PedidoServicio pedidoServicio = new PedidoServicio();
    ProductoServicio productoServicio = new ProductoServicio(new ProductoRepositorio());
    UsuarioServicio usuarioServicio = new UsuarioServicio();
    Carrito carritoSesion = new Carrito();
    EncriptaServicio encriptaServicio = new EncriptaServicio();

    ValidadorServicio validadorServicio = new ValidadorServicio();

    // Getters for services
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
