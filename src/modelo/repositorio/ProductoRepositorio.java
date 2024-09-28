package modelo.repositorio;

import modelo.dominio.Producto;
import modelo.dominio.ProductoCategoria;
import modelo.dominio.ProductoComputadora;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio {

    private List<Producto> listaDeProductos;

    public ProductoRepositorio() {

        Producto producto1 = new Producto(1234, "Zapatillas", ProductoCategoria.CALZADO, 50f);
        Producto producto2 = new Producto(5678, "Televisor", ProductoCategoria.ELECTRODOMESTICOS, 800f);
        Producto producto3 = new Producto(9012, "Mesa", ProductoCategoria.HOGAR, 120f);
        Producto producto4 = new ProductoComputadora(1470, "Computadora", ProductoCategoria.INFORMATICA,1200f, "HP", "Pavilion");

        List<Producto> listado = new ArrayList<>();
        listado.add(producto1);
        listado.add(producto2);
        listado.add(producto3);
        listado.add(producto4);
        this.listaDeProductos = listado;
    }

    public List<Producto> getListaDeProductos() {
        return listaDeProductos;
    }

    public void setListaDeProductos(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }
}
