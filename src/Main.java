import modelo.repositorio.ProductoRepositorio;

public class Main {
    public static void main(String[] args) {

        System.out.println("Bienvenidos a la Venta On Line");
        //al comenzar, debo crear el repositorio
        //A REEMPLAZAR POR BASE DE DATOS
        ProductoRepositorio productoRepositorio = new ProductoRepositorio();

    }
}