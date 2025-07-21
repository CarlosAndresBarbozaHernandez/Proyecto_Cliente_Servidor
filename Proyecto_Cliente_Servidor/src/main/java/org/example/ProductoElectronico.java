package org.example;

//Esta clase puede que sea temporal pero puede que la vayamos a necesitar luego para la conexion con BD
public class ProductoElectronico extends Producto{

    public ProductoElectronico(int codigo, String nombre, double precio, int inventario, Categoria categoria) {
        super(precio, codigo, nombre, inventario, categoria);
    }

    @Override
    public void mostrarDetalles() {
        System.out.println(String.format("Producto: %s, Precio: %s" + nombre + precio));
    }
}
