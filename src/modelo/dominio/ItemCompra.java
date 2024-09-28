package modelo.dominio;

public class ItemCompra {
    private Producto producto;
    private int cantidad;
    private float monto;
    private float descuentoXtra;

    public ItemCompra(Producto producto, int cantidad)
    {
        this.producto = producto;
        this.cantidad = cantidad;
        this.monto = (producto.getPrecioFinal() * cantidad) - descuentoXtra;
    }

    public Producto getProducto()
    {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad)
    {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto: " + producto +
                ". Cant: " + cantidad +
                ". Precio unitario: " + producto.getPrecioFinal() +
                ". Subtotal: " + monto;
    }
}
