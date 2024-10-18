package modelo.servicio;

import modelo.dominio.EnumCategoria;
import modelo.dominio.Producto;
import modelo.repositorio.IProductoRepositorio;
import modelo.repositorio.ProductoRepositorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductoServicio {
    private IProductoRepositorio productoRepositorio;

    // Constructor para inicializar ProductoRepositorio
    public ProductoServicio(IProductoRepositorio productoRepositorio) {
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
        return productoRepositorio.buscarPorCodigo(codigo);
    }

    public List<Producto> buscarPorNombre(String nombre)
    {
        return productoRepositorio.buscarPorNombre(nombre);
    }

    //BUSCAR POR CATEGORIA
    public List<Producto> buscarPorCategoria(EnumCategoria categoria)
    {
        return productoRepositorio.buscarPorCategoria(categoria);
    }

    //BUSCAR POR RANGO DE PRECIO
    public List<Producto> buscarPorRangoPrecio(float min, float max)
    {
        return productoRepositorio.buscarPorRangoPrecio(min, max);
    }

    //BUSCAR COMBINANDO TODOS LOS FILTROS
    public List<Producto> buscarCompleto(int codigo, String nombre, EnumCategoria categoria, float min, float max)
    {
        List<Producto> listaProductos = productoRepositorio.getListaDeProductos();
        Stream<Producto> stream = listaProductos.stream();

        //si recibo dato de codigo, aplico unicamente ese filtro
        if(codigo > 0)
        {
            stream = stream.filter(p -> p.getCodigoProducto() == codigo);
        }
        else
        {
            //Si nombre viene vacio, NO aplico el filtro
            if(!nombre.isEmpty())
            {
                stream = stream.filter(p -> p.getNombre().contains(nombre));
            }
            //Si categoria viene null, NO aplico el filtro
            if(categoria != null)
            {
                stream = stream.filter(p -> p.getCategoria().equals(categoria));
            }
            //Si min es menor o igual a 0, No aplico ese filtro
            if(min > 0)
            {
                stream = stream.filter(p -> p.getPrecio() >= min);
            }
            //Si max es Float.Max, NO aplico ese filtro
            if(max != Float.MAX_VALUE)
            {
                stream = stream.filter(p -> p.getPrecio() <= max);
            }
        }
        return stream.collect(Collectors.toList());
    }
}
