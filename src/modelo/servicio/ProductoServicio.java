package modelo.servicio;

import modelo.dominio.Producto;
import modelo.repositorio.ProductoRepositorio;

import java.util.ArrayList;
import java.util.List;

public class ProductoServicio {
    private ProductoRepositorio productoRepositorio;

    public List<Producto> listarProductos()
    {
        return productoRepositorio.getListaDeProductos();
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
        System.out.println("No se encontró ningún producto con código " + codigo);
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

    //BUSCAR POR PRECIO ASCENDENTE

    //BUSCAR POR PRECIO DESCENDENTE


}
