package com.example.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventarioService {
    private List<Producto> productos;

    public InventarioService() {
        productos = new ArrayList<>();
        // Agregar productos
        productos.add(new Producto(1, "Producto A", 10.0, 50));
        productos.add(new Producto(2, "Producto B", 15.0, 30));
        productos.add(new Producto(3, "Producto C", 20.0, 20));
        productos.add(new Producto(4, "Producto D", 30.0, 15));
        productos.add(new Producto(5, "Producto E", 40.0, 10));
        productos.add(new Producto(6, "Producto F", 25.0, 60));
        productos.add(new Producto(7, "Producto G", 50.0, 5));
        productos.add(new Producto(8, "Producto H", 60.0, 3));
        productos.add(new Producto(9, "Producto I", 80.0, 7));
        productos.add(new Producto(10, "Producto J", 35.0, 45));
    }

    // Agregar un producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    // Obtener todos los productos
    public List<Producto> obtenerProductos() {
        return productos;
    }

    // Obtener un producto por ID
    public Producto obtenerProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Eliminar un producto
    public boolean eliminarProducto(int id) {
        Producto producto = obtenerProductoPorId(id);
        if (producto != null) {
            productos.remove(producto);
            return true;
        }
        return false;
    }
}
