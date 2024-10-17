package modelo.repositorio;

import modelo.dominio.EnumCategoria;
import modelo.dominio.EnumCategoriaTecnologia;
import modelo.dominio.Producto;
import modelo.dominio.ProductoTecnologia;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio {

    private List<Producto> listaDeProductos;

    public ProductoRepositorio() {
        listaDeProductos = new ArrayList<>();
        agregarProductos();
    }

    private void agregarProductos() {
        agregarProducto(1234, "Zapatillas", EnumCategoria.CALZADO, 50f);
        agregarProducto(5678, "Televisor", EnumCategoria.ELECTRODOMESTICOS, 800f);
        agregarProducto(9012, "Mesa", EnumCategoria.HOGAR, 120f);
        agregarProductoTecnologia(1470, "Computadora", EnumCategoriaTecnologia.INFORMATICA,1200f,
                "HP", "Pavilion");
        agregarProductoTecnologia(5885, "Mouse", EnumCategoriaTecnologia.INFORMATICA, 35.3f,
                "Genius", "inalámbrico");
        agregarProductoTecnologia(9632, "Teclado", EnumCategoriaTecnologia.INFORMATICA, 21.1f,
                "Nola", "Español");

        // productos extra
        agregarProducto(1111, "Smartphone", EnumCategoria.TECNOLOGIA, EnumCategoriaTecnologia.CELULARES,
                600f);
        agregarProducto(2222, "Remera", EnumCategoria.INDUMENTARIA, 30f);
        agregarProducto(3333, "Pantalones", EnumCategoria.INDUMENTARIA, 45f);
        agregarProducto(4444, "Raqueta", EnumCategoria.DEPORTE, 150f);
        agregarProducto(5555, "Bicicleta", EnumCategoria.DEPORTE, 800f);
        agregarProducto(6666, "Silla", EnumCategoria.MUEBLES, 450f);
        agregarProducto(7777, "Estante", EnumCategoria.HOGAR, 20f);
        agregarProducto(8888, "Aspiradora", EnumCategoria.ELECTRODOMESTICOS, 180f);
        agregarProducto(9999, "Tablet", EnumCategoria.TECNOLOGIA, EnumCategoriaTecnologia.TABLETS,
                250f);
        agregarProducto(1010, "Campera", EnumCategoria.INDUMENTARIA, 120f);
    }

    private void agregarProducto(int codigo, String nombre, EnumCategoria categoria, float precio) {
        Producto producto = new Producto(codigo, nombre, categoria, precio);
        listaDeProductos.add(producto);
    }

    private void agregarProducto(int codigo, String nombre, EnumCategoria categoria, Enum<?> subcategoria, float precio)
    {
        Producto producto = new Producto(codigo, nombre, categoria, subcategoria, precio);
        listaDeProductos.add(producto);
    }

    private void agregarProductoTecnologia(int codigo, String nombre, EnumCategoriaTecnologia subcategoria,
                                           float precio, String marca, String modelo)
    {
        Producto producto = new ProductoTecnologia(codigo, nombre, EnumCategoria.TECNOLOGIA, subcategoria, precio, marca, modelo);
        listaDeProductos.add(producto);
    }


    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }
}
