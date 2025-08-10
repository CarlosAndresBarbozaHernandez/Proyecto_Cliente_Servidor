package org.example.Modelo;

public abstract class Producto {
    protected int codigo;
    protected String nombre;
    protected double precio;
    protected int inventario;
    protected Categoria categoria;


    //Constructor
    public Producto(double precio, int codigo, String nombre, int inventario, Categoria categoria) {
        this.precio = precio;
        this.codigo = codigo;
        this.nombre = nombre;
        this.inventario = inventario;
        this.categoria = categoria;
    }


    //Getters y Setters
    public abstract void mostrarDetalles();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
