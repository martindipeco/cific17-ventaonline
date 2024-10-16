package modelo.repositorio;

import modelo.dominio.Producto;
import modelo.dominio.ProductoCategoria;
import modelo.dominio.ProductoInformatica;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio {

    private List<Producto> listaDeProductos;

    public ProductoRepositorio() {
        listaDeProductos = new ArrayList<>();
        agregarProductos();
    }

    private void agregarProductos() {
        agregarProducto(1234, "Zapatillas", ProductoCategoria.CALZADO, 50f);
        agregarProducto(5678, "Televisor", ProductoCategoria.ELECTRODOMESTICOS, 800f);
        agregarProducto(9012, "Mesa", ProductoCategoria.HOGAR, 120f);
        agregarProductoInformatica(1470, "Computadora", ProductoCategoria.INFORMATICA, 1200f, "HP", "Pavilion");
        agregarProductoInformatica(5885, "Mouse", ProductoCategoria.INFORMATICA, 35.3f, "Genius", "inalámbrico");
        agregarProductoInformatica(9632, "Teclado", ProductoCategoria.INFORMATICA, 21.1f, "Nola", "Español");

        // productos extra
        agregarProducto(1111, "Smartphone", ProductoCategoria.TECNOLOGIA, 600f);
        agregarProducto(2222, "Remera", ProductoCategoria.INDUMENTARIA, 30f);
        agregarProducto(3333, "Pantalones", ProductoCategoria.INDUMENTARIA, 45f);
        agregarProducto(4444, "Raqueta", ProductoCategoria.DEPORTE, 150f);
        agregarProducto(5555, "Bicicleta", ProductoCategoria.DEPORTE, 800f);
        agregarProducto(6666, "Silla", ProductoCategoria.MUEBLES, 450f);
        agregarProducto(7777, "Estante", ProductoCategoria.HOGAR, 20f);
        agregarProducto(8888, "Aspiradora", ProductoCategoria.ELECTRODOMESTICOS, 180f);
        agregarProducto(9999, "Tablet", ProductoCategoria.TECNOLOGIA, 250f);
        agregarProducto(1010, "Campera", ProductoCategoria.INDUMENTARIA, 120f);
    }

    private void agregarProducto(int codigo, String nombre, ProductoCategoria categoria, float precio) {
        Producto producto = new Producto(codigo, nombre, categoria, precio);
        listaDeProductos.add(producto);
    }

    private void agregarProductoInformatica(int codigo, String nombre, ProductoCategoria categoria, float precio,
                                            String marca, String modelo) {
        Producto producto = new ProductoInformatica(codigo, nombre, categoria, precio, marca, modelo);
        listaDeProductos.add(producto);
    }


    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }
}
