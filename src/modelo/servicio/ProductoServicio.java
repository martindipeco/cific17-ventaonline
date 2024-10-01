package modelo.servicio;

import modelo.dominio.Producto;
import modelo.dominio.ProductoCategoria;
import modelo.repositorio.ProductoRepositorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoServicio {
    private ProductoRepositorio productoRepositorio;

    // Constructor para inicializar ProductoRepositorio
    public ProductoServicio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    // Constructor vacio
    public ProductoServicio() {
    }

    public void setProductoRepositorio(ProductoRepositorio productoRepositorio)
    {
        this.productoRepositorio = productoRepositorio;
    }
    public List<Producto> listarProductos()
    {
        return productoRepositorio.getListaDeProductos();
    }

    public List<Producto> listar5productosPorPagina(int numPagina)
    {
        if(numPagina<1)
        {
            System.out.println("Ingrese un número positivo");
            System.out.println("Se devuelve una lista vacía");
            return List.of();
        }
        int rango = 5;
        int desde = (numPagina - 1) * rango;
        int hasta = Math.min(desde + rango, productoRepositorio.getListaDeProductos().size());
        if(desde >= productoRepositorio.getListaDeProductos().size())
        {
            System.out.println("Ya no hay más items");
            return List.of();

        }
        return productoRepositorio.getListaDeProductos().subList(desde, hasta);
    }

    public Producto buscarPorCodigo(int codigo)
    {
        for(Producto p : productoRepositorio.getListaDeProductos())
        {
            if(p.getCodigoProducto() == codigo)
            {
                return p;
            }
        }
        return null;
    }

    public List<Producto> buscarPorNombre(String nombre)
    {
        List<Producto> productos = new ArrayList<>();
        if(nombre.length()>2)
        {
            for(Producto p : productoRepositorio.getListaDeProductos())
            {
                if (p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                {
                    productos.add(p);
                }
            }
        }
        else
        {
            System.out.println("Por favor ingrese al menos 3 caracteres");
            System.out.println("Se devuelve una lista vacía");
        }
        return productos;
    }

    //BUSCAR POR CATEGORIA
    public List<Producto> buscarPorCategoria(ProductoCategoria categoria)
    {
        return productoRepositorio.getListaDeProductos().stream()
                .filter(producto -> producto.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    //BUSCAR POR RANGO DE PRECIO
    public List<Producto> buscarPorRangoPrecio(float min, float max)
    {
        List<Producto> productos = new ArrayList<>();
        for(Producto p : productoRepositorio.getListaDeProductos())
        {
            if(p.getPrecio() >= min || p.getPrecio() <= max)
            {
                productos.add(p);
            }
        }
        if(productos.isEmpty())
        {
            System.out.println("No hay productos en ese rango de precio");
        }
        else
        {
            productos.sort(Comparator.comparing(Producto::getPrecio)); //orden ascendente
            //productos.sort(Comparator.comparing(Producto::getPrecio).reversed())
        }
        return productos;
    }
}
