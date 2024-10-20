package modelo.dominio;

import java.util.Objects;

public class Producto {
    private int codigoProducto;
    private String nombre;
    private EnumCategoria categoria;
    private Enum<?> subcategoria;
    private float precio;
    private float descuento;
    private float precioFinal;
    private int stock;

    public Producto(int codigoProducto, String nombre, EnumCategoria categoria, float precio) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.subcategoria = null;
        this.precio = precio;
        this.precioFinal = precio - this.descuento;
        this.stock = 10;
    }

    public Producto(int codigoProducto, String nombre, EnumCategoria categoria, Enum<?> subcategoria, float precio) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.precio = precio;
        this.precioFinal = precio - this.descuento;
        this.stock = 10;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecioFinal() {
        return precioFinal;
    }

    public EnumCategoria getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(codigoProducto, producto.codigoProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoProducto);
    }


    @Override
    public String toString() {
        return "\nCód.: " + codigoProducto +
                ": " + nombre +
                ". Precio: " + precio +
                ". Desc.: " + descuento;
    }
}
