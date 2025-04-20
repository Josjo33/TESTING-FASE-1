package com.example.inventory;

public class Main {
    public static void main(String[] args) {
        InventarioService inventario = new InventarioService();

        // Ver productos
        System.out.println("Lista de productos:");
        for (Producto p : inventario.obtenerProductos()) {
            System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio() + ", Cantidad: " + p.getCantidad());
        }

        // Obtener un producto por ID
        System.out.println("\nObteniendo Producto con ID 5:");
        Producto producto = inventario.obtenerProductoPorId(5);
        if (producto != null) {
            System.out.println("Producto: " + producto.getNombre());
        }

        // Eliminar un producto
        System.out.println("\nEliminando Producto con ID 2:");
        boolean eliminado = inventario.eliminarProducto(2);
        if (eliminado) {
            System.out.println("Producto eliminado.");
        }

        // Ver productos después de eliminar
        System.out.println("\nLista de productos después de eliminación:");
        for (Producto p : inventario.obtenerProductos()) {
            System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio() + ", Cantidad: " + p.getCantidad());
        }
    }
}
