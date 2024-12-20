package modelo.repositorio;

import modelo.dominio.EnumCategoria;
import modelo.dominio.Producto;

import java.util.List;

public interface IProductoRepositorio {
    List<Producto> getListaDeProductos();
    Producto buscarPorCodigo(int codigo);
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarPorCategoria(EnumCategoria categoria);
    List<Producto> buscarPorRangoPrecio(float min, float max);
}
