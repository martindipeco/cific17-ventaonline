package modelo.dominio;

public class ProductoTecnologia extends Producto {

    private EnumCategoriaTecnologia subcategoria;
    private String marca;
    private String modelo;

    public ProductoTecnologia(int codigoProducto, String nombre,EnumCategoria categoria,
                              EnumCategoriaTecnologia subcategoria, float precio, String marca, String modelo)
    {
        super(codigoProducto, nombre, categoria, precio); // Llamada al constructor de la clase madre
        this.subcategoria = subcategoria;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return super.toString() + ", Marca: " + marca + ", Modelo: " + modelo;
    }

} 