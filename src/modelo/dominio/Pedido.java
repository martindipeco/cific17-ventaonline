package modelo.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
    private static Long numeroAutoincremental = 1l; // Static field shared by all instances to keep track of the last assigned ID
    private Long numPedido;
    private Carrito carrito;
    private Float precioFinal;
    private float costoEnvio;
    private float descuento;
    private LocalDateTime fechaPedido;
    private boolean entregado;
    private LocalDateTime fechaEntregado;

    //carrito sin descuento sin costo envio
    public Pedido(Carrito carrito) {
        this.numPedido = numeroAutoincremental++; //adjudica el valor de autoIncremental, y luego le suma 1
        this.carrito = carrito;
        this.fechaPedido = LocalDateTime.now();
        this.entregado = false;
        this.precioFinal = carrito.getMontoCarrito() - this.descuento + this.costoEnvio;;
    }

    //num pedido desde base de datos
    public Pedido(long numPedido, Carrito carrito, Float precioFinal, LocalDateTime fechaPedido, boolean entregado,
                  LocalDateTime fechaEntregado) {
        this.numPedido = numPedido;
        this.carrito = carrito;
        this.precioFinal = precioFinal;
        this.fechaPedido = fechaPedido;
        this.entregado = entregado;
        this.fechaEntregado = fechaEntregado;
        this.precioFinal = carrito.getMontoCarrito() - this.descuento + this.costoEnvio;;
    }

    //carrito con descuento sin costo envio
    public Pedido(Carrito carrito, float descuento) {
        this.numPedido = numeroAutoincremental++; //adjudica el valor de autoIncremental, y luego le suma 1
        this.carrito = carrito;
        this.fechaPedido = LocalDateTime.now();
        this.entregado = false;
        this.precioFinal = carrito.getMontoCarrito() - descuento + this.costoEnvio;;
    }

    //carrito con descuento con costo de envio
    public Pedido(Carrito carrito, float descuento, float costoEnvio) {
        this.numPedido = numeroAutoincremental++; //adjudica el valor de autoIncremental, y luego le suma 1
        this.carrito = carrito;
        this.fechaPedido = LocalDateTime.now();
        this.entregado = false;
        this.precioFinal = carrito.getMontoCarrito() - descuento + costoEnvio;
    }

    //constructor que recibe de base de datos
    public Pedido(Long numPedido, String usuarioMail, float precioFinal, float costoEnvio, float descuento,
                  LocalDateTime fechaPedido, boolean entregado, LocalDateTime fechaEntregado)
    {
        this.numPedido = numPedido;
        this.carrito = new Carrito();
        this.fechaPedido = fechaPedido;
        this.entregado = entregado;
    }

    public Long getNumPedido() {return numPedido;}
    public Carrito getCarrito() {
        return carrito;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public LocalDateTime getFechaEntregado() {
        return fechaEntregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public void setFechaEntregado(LocalDateTime fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    public Float getPrecioFinal() {
        return precioFinal;
    }

    @Override
    public String toString() {

        return "Pedido num: " + numPedido +
                ". Cliente: " + carrito.getUsuario() +
                ". Fecha: " + fechaPedido.format(DateTimeFormatter.ISO_DATE) +
                ". Productos: " + carrito.getListaItems() +
                ". Monto: " + precioFinal +
                ". Entregado: " + entregado;
    }
}
