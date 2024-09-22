package modelo.dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
    private static Long numeroAutoincremental = 1l; // Static field shared by all instances to keep track of the last assigned ID
    private Long numPedido;
    private Carrito carrito;
    private LocalDateTime fechaPedido;
    private boolean entregado;
    private LocalDateTime fechaEntregado;

    public Pedido(Carrito carrito) {
        this.numPedido = numeroAutoincremental++; //adjudica el valor de autoIncremental, y luego le suma 1
        this.carrito = carrito;
        fechaPedido = LocalDateTime.now();
        entregado = false;
    }

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

    @Override
    public String toString() {
        return "Pedido{" +
                "numPedido=" + numPedido +
                ", carrito=" + carrito +
                ", fechaPedido=" + fechaPedido.format(DateTimeFormatter.ISO_DATE) +
                ", entregado=" + entregado +
                '}';
    }
}
