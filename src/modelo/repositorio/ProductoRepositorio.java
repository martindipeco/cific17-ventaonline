package modelo.repositorio;

import modelo.dominio.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio {

    private List<Producto> stockDeProductos;

    public ProductoRepositorio() {

        Producto producto1 = new Producto(1234, "Zapatillas", 50f);
        Producto producto2 = new Producto(5678, "Televisor", 800f);
        Producto producto3 = new Producto(9012, "Mesa", 120f);

        List<Producto> listado = new ArrayList<>();
        listado.add(producto1);
        listado.add(producto2);
        listado.add(producto3);
        this.stockDeProductos = listado;
    }

    public List<Producto> getStockDeProductos() {
        return stockDeProductos;
    }

    public void setStockDeProductos(List<Producto> stockDeProductos) {
        this.stockDeProductos = stockDeProductos;
    }
}
