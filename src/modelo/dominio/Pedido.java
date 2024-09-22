package modelo.dominio;

import java.time.LocalDateTime;

public class Pedido {

    private Carrito carrito;
    private LocalDateTime fechaPedido;
    private boolean pagado;
    private LocalDateTime fechaPagado;
    private boolean entregado;
    private LocalDateTime fechaEntregado;
}
