public class ProductoComputadora extends Producto {

    private String marca;
    private String modelo;

    public ProductoComputadora(int codigoProducto, String nombre, float precio,String marca, String modelo) {
        super(codigoProducto, nombre, precio); // Llamada al constructor de la clase padre
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