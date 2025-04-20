package test.com.example.inventory;

import com.example.inventory.InventarioService;
import com.example.inventory.Producto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InventarioServiceTest {

    private InventarioService inventarioService;

    @Before
    public void setup() {
        inventarioService = new InventarioService();
    }

    // Test para verificar que los productos se agregan correctamente
    @Test
    public void testAgregarProducto() {
        Producto nuevoProducto = new Producto(11, "Producto K", 25.0, 40);
        inventarioService.agregarProducto(nuevoProducto);
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("El número de productos debería ser 11 después de agregar uno nuevo", 11, productos.size());
    }

    // Test para verificar que un producto se puede obtener correctamente por su ID
    @Test
    public void testObtenerProductoPorId() {
        Producto producto = inventarioService.obtenerProductoPorId(3);
        assertNotNull("El producto no debería ser null", producto);
        assertEquals("El nombre del producto debería ser 'Producto C'", "Producto C", producto.getNombre());
    }

    // Test para verificar la eliminación de un producto
    @Test
    public void testEliminarProducto() {
        boolean eliminado = inventarioService.eliminarProducto(4);  // Eliminando "Producto D"
        assertTrue("El producto debería ser eliminado exitosamente", eliminado);
        assertNull("El producto debería ser null después de eliminarlo", inventarioService.obtenerProductoPorId(4));
    }

    // Test para verificar que el número total de productos es correcto
    @Test
    public void testObtenerTodosLosProductos() {
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("Debería haber 10 productos iniciales en el inventario", 10, productos.size());
    }

    // Test para verificar la cantidad de un producto después de agregarlo
    @Test
    public void testVerificarCantidadProducto() {
        Producto producto = inventarioService.obtenerProductoPorId(2);
        assertNotNull("El producto no debería ser null", producto);
        assertEquals("La cantidad del Producto B debería ser 30", 30, producto.getCantidad());
    }

    // Test para verificar que no se pueda agregar un producto con un ID duplicado
    @Test
    public void testAgregarProductoIdDuplicado() {
        Producto productoDuplicado = new Producto(1, "Producto A", 50.0, 10); // ID duplicado
        inventarioService.agregarProducto(productoDuplicado);

        // Verificar que el número total de productos sigue siendo el mismo
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("El número de productos no debería haber cambiado al agregar un producto con ID duplicado", 10, productos.size());
    }

    // Test para verificar que no se pueda agregar un producto con cantidad negativa
    @Test
    public void testAgregarProductoCantidadNegativa() {
        Producto productoInvalido = new Producto(12, "Producto L", 20.0, -5); // Cantidad negativa
        inventarioService.agregarProducto(productoInvalido);

        // Verificar que el producto con cantidad negativa no ha sido agregado
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("El número de productos no debería haber cambiado al agregar un producto con cantidad negativa", 10, productos.size());
    }

    // Test para verificar la eliminación de un producto inexistente
    @Test
    public void testEliminarProductoInexistente() {
        boolean eliminado = inventarioService.eliminarProducto(100);  // Producto que no existe
        assertFalse("No se debería permitir eliminar un producto que no existe", eliminado);
    }

    // Test para verificar la eliminación de un producto y luego intentar obtenerlo
    @Test
    public void testEliminarYObtenerProducto() {
        // Eliminar un producto existente
        inventarioService.eliminarProducto(3);

        // Verificar que el producto ya no se puede obtener
        Producto productoEliminado = inventarioService.obtenerProductoPorId(3);
        assertNull("El producto eliminado no debería poder ser obtenido", productoEliminado);
    }

    // Test para verificar que el sistema no permita agregar un producto con nombre vacío
    @Test
    public void testAgregarProductoConNombreVacio() {
        Producto productoInvalido = new Producto(13, "", 25.0, 30); // Nombre vacío
        inventarioService.agregarProducto(productoInvalido);

        // Verificar que el número de productos no cambió
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("El producto con nombre vacío no debería haber sido agregado", 10, productos.size());
    }

    // Test para verificar el comportamiento cuando el inventario está lleno (suponiendo un límite de 20 productos)
    @Test
    public void testInventarioLleno() {
        for (int i = 11; i <= 20; i++) {
            inventarioService.agregarProducto(new Producto(i, "Producto " + i, 10.0, 10));
        }
        // Intentar agregar un producto más cuando ya hay 20 productos
        Producto productoExceso = new Producto(21, "Producto Exceso", 25.0, 10);
        inventarioService.agregarProducto(productoExceso);

        // Verificar que no se haya agregado el producto
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("El inventario no debería permitir más de 20 productos", 20, productos.size());
    }

    // Test para verificar que el producto más caro es "Producto H"
    @Test
    public void testProductoMasCaro() {
        Producto productoMasCaro = null;
        for (Producto p : inventarioService.obtenerProductos()) {
            if (productoMasCaro == null || p.getPrecio() > productoMasCaro.getPrecio()) {
                productoMasCaro = p;
            }
        }
        assertNotNull("El producto más caro no debería ser null", productoMasCaro);
        assertEquals("El producto más caro debería ser 'Producto H'", "Producto H", productoMasCaro.getNombre());
    }

    // Test para verificar la cantidad total de productos en el inventario
    @Test
    public void testCantidadTotalInventario() {
        int cantidadTotal = 0;
        for (Producto p : inventarioService.obtenerProductos()) {
            cantidadTotal += p.getCantidad();
        }
        assertEquals("La cantidad total de productos en el inventario debería ser 250", 250, cantidadTotal);
    }

    // Test para verificar la actualización de la cantidad de un producto (sin cambiar el nombre)
    @Test
    public void testActualizarCantidadProducto() {
        Producto producto = inventarioService.obtenerProductoPorId(1);
        int cantidadOriginal = producto.getCantidad();
        producto.setCantidad(cantidadOriginal + 10); // Actualizar cantidad
        inventarioService.agregarProducto(producto); // Intentar agregarlo con nueva cantidad

        Producto productoActualizado = inventarioService.obtenerProductoPorId(1);
        assertEquals("La cantidad del Producto A debería haber aumentado", cantidadOriginal + 10, productoActualizado.getCantidad());
    }

    // Test para agregar un producto con un precio 0.0 (posible fallo lógico)
    @Test
    public void testAgregarProductoConPrecioCero() {
        Producto productoInvalido = new Producto(14, "Producto M", 0.0, 10); // Precio cero
        inventarioService.agregarProducto(productoInvalido);

        // Verificar que el número de productos sigue siendo el mismo (no se debería agregar)
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("No se debería permitir agregar un producto con precio 0.0", 10, productos.size());
    }

    // Test para agregar un producto con una cantidad de inventario extremadamente alta (overflow)
    @Test
    public void testAgregarProductoConCantidadExcesiva() {
        Producto productoExcesivo = new Producto(15, "Producto N", 100.0, Integer.MAX_VALUE); // Cantidad máxima de un entero
        inventarioService.agregarProducto(productoExcesivo);

        // Verificar que el producto fue agregado y que la cantidad se mantiene correctamente
        Producto producto = inventarioService.obtenerProductoPorId(15);
        assertNotNull("El producto con cantidad excesiva debería haberse agregado", producto);
        assertEquals("La cantidad debería ser igual a Integer.MAX_VALUE", Integer.MAX_VALUE, producto.getCantidad());
    }

    // ERROR QUE DEMUESTRA QUE EL CODIGO PRESENTA EL ERROR DE PERMITIR AGREGAR ID NEGATIVO
    @Test
    public void testAgregarProductoConIdNegativo() {
        Producto productoInvalido = new Producto(-1, "Producto O", 25.0, 10); // ID negativo
        inventarioService.agregarProducto(productoInvalido);

        // Verificar que el número de productos no cambió
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("No se debería permitir agregar un producto con un ID negativo", 10, productos.size());
    }

    // Test para agregar un producto con nombre null (invalidación de lógica)
    @Test
    public void testAgregarProductoConNombreNull() {
        Producto productoInvalido = new Producto(16, null, 30.0, 15); // Nombre null
        inventarioService.agregarProducto(productoInvalido);

        // Verificar que el número de productos no cambió
        List<Producto> productos = inventarioService.obtenerProductos();
        assertEquals("No se debería permitir agregar un producto con nombre null", 10, productos.size());
    }

    // Test para obtener un producto después de eliminarlo repetidamente
    @Test
    public void testEliminarYBuscarProductoRepetidamente() {
        // Eliminar producto
        inventarioService.eliminarProducto(5);  // Producto E

        // Buscar varias veces el producto
        Producto producto = inventarioService.obtenerProductoPorId(5);
        assertNull("El producto debería ser null después de eliminarlo", producto);

        producto = inventarioService.obtenerProductoPorId(5);
        assertNull("El producto debería seguir siendo null después de intentar buscarlo nuevamente", producto);
    }

    // Test para agregar productos con precios extremadamente altos
    @Test
    public void testAgregarProductoConPrecioExtremadamenteAlto() {
        Producto productoInvalido = new Producto(19, "Producto R", Double.MAX_VALUE, 10); // Precio máximo
        inventarioService.agregarProducto(productoInvalido);

        // Verificar que el producto fue agregado y que el precio es correcto
        Producto producto = inventarioService.obtenerProductoPorId(19);
        assertNotNull("El producto con precio extremadamente alto debería haberse agregado", producto);
        assertEquals("El precio del Producto R debería ser Double.MAX_VALUE", Double.MAX_VALUE, producto.getPrecio(), 0);
    }
}
