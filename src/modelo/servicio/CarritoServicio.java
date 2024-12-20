package modelo.servicio;

import modelo.dominio.Carrito;
import modelo.dominio.ItemCompra;
import modelo.dominio.Producto;

import java.util.List;
import java.util.Optional;

public class CarritoServicio {

    public void agregarProducto(Carrito carrito, Producto producto, Integer cantidad)
    {
        //validación de cantidad
        if (cantidad < 1)
        {
            System.out.println("Por favor, ingrese un número positivo");
            return;
        }
        //validación de stock
        if(producto.getStock() < cantidad)
        {
            System.out.println("No hay suficiente stock del producto " + producto);
            return;
        }

        //chequeo si el item ya existe en el carrito
        Optional<ItemCompra> itemExistente = carrito.getListaItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();

        if(itemExistente.isPresent())
        {
            ItemCompra itemCompra = itemExistente.get();
            itemCompra.setCantidad(itemCompra.getCantidad()+cantidad);
        }
        else
        {
            carrito.getListaItems().add(new ItemCompra(producto, cantidad));
        }


        producto.setStock(producto.getStock()-cantidad);
        carrito.setMontoCarrito(carrito.getMontoCarrito()+(cantidad* producto.getPrecioFinal()));
    }

    public void agregarProductoX1(Carrito carrito, Producto producto)
    {
        //validación de stock
        if(producto.getStock() < 1)
        {
            System.out.println("No hay suficiente stock del producto " + producto);
            return;
        }

        //chequeo si el item ya existe en el carrito
        Optional<ItemCompra> itemExistente = carrito.getListaItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();

        if(itemExistente.isPresent())
        {
            ItemCompra itemCompra = itemExistente.get();
            itemCompra.setCantidad(itemCompra.getCantidad()+1);
        }
        else
        {
            carrito.getListaItems().add(new ItemCompra(producto, 1));
        }
        producto.setStock(producto.getStock()-1);
        carrito.setMontoCarrito(carrito.getMontoCarrito()+(1 * producto.getPrecioFinal()));
    }

    public void quitarProductoX1(Carrito carrito, Producto producto)
    {
        //chequeo si el item efectivamente existe en el carrito
        Optional<ItemCompra> itemExistente = carrito.getListaItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();

        if(itemExistente.isPresent())
        {
            ItemCompra itemCompra = itemExistente.get();
            if(itemCompra.getCantidad() > 0)
            {
                itemCompra.setCantidad(itemCompra.getCantidad()-1);
                producto.setStock(producto.getStock()+1);

                if(itemCompra.getCantidad() == 0)
                {
                    carrito.getListaItems().remove(itemCompra);
                }
            }

            carrito.setMontoCarrito(carrito.getMontoCarrito()-(1 * producto.getPrecioFinal()));

            // En caso de descuentos, por las dudas, chequear que el precio no sea negativo
            if (carrito.getMontoCarrito() < 0) {
                carrito.setMontoCarrito(0f);
            }
        }
        else
        {
            System.out.println("El producto no está en el carrito");
        }
    }

    public void quitarProducto(Carrito carrito, Producto producto)
    {
        //chequeo si el item efectivamente existe en el carrito
        Optional<ItemCompra> itemExistente = carrito.getListaItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();

        if(itemExistente.isPresent())
        {
            ItemCompra itemCompra = itemExistente.get();

            // Remove all instances of the product
            int cantidadAEliminar = itemCompra.getCantidad();
            carrito.getListaItems().remove(itemCompra);

            // Linea eliminada, porque producìa bug x falta de sincronización base datos con modelo tabla
            //producto.setStock(producto.getStock() + cantidadAEliminar);

            // Adjust the total amount of the cart
            carrito.setMontoCarrito(carrito.getMontoCarrito() - (cantidadAEliminar * producto.getPrecioFinal()));

            // Ensure the total amount does not go negative
            if (carrito.getMontoCarrito() < 0)
            {
                carrito.setMontoCarrito(0f);
            }
        }
        else
        {
            System.out.println("El producto no está en el carrito");
        }
    }

    public void quitarProductoXcantidad(Carrito carrito, Producto producto, Integer cantidad)
    {
        //chequeo si el item efectivamente existe en el carrito
        Optional<ItemCompra> itemExistente = carrito.getListaItems().stream()
                .filter(item -> item.getProducto().equals(producto))
                .findFirst();

        if(itemExistente.isPresent())
        {
            ItemCompra itemCompra = itemExistente.get();
            if(itemCompra.getCantidad() > cantidad)
            {
                itemCompra.setCantidad(itemCompra.getCantidad()-cantidad);
                producto.setStock(producto.getStock()+cantidad);
            }
            else //si tengo menos items en el carrito de la cantidad que quiero restar
            {
                producto.setStock(producto.getStock() + itemCompra.getCantidad());
                carrito.getListaItems().remove(itemCompra);
            }
            //actualizo el monto de acuerdo a la cantidad de items removidos
            int itemsRemovidos = Math.min(cantidad, itemCompra.getCantidad());
            carrito.setMontoCarrito(carrito.getMontoCarrito()-(itemsRemovidos * producto.getPrecioFinal()));

            // En caso de descuentos, por las dudas, chequear que el precio no sea negativo
            if (carrito.getMontoCarrito() < 0) {
                carrito.setMontoCarrito(0f);
            }
        }
        else
        {
            System.out.println("El producto no está en el carrito");
        }
    }

    public void mostrarCarrito(Carrito carrito)
    {
        System.out.println("Items de compra: ");
        for(ItemCompra item : carrito.getListaItems())
        {
            System.out.println(item);
        }
    }

    public String obtenerCarrito(Carrito carrito)
    {
        String carritoString = "";
        for (ItemCompra item : carrito.getListaItems())
        {
            carritoString = carritoString + "\n" + item;
        }
        return carritoString;
    }

    public void vaciarCarrito(Carrito carrito)
    {
        // Iterar cada item en carrito
        for (ItemCompra item : carrito.getListaItems()) {
            Producto producto = item.getProducto();
            int cantidadEnCarrito = item.getCantidad();

            // Restaurar stock
            producto.setStock(producto.getStock() + cantidadEnCarrito);
        }

        // Limpiar carrito
        carrito.getListaItems().clear();
        carrito.setMontoCarrito(0.0f); // Reset the total amount of the cart
    }
}
