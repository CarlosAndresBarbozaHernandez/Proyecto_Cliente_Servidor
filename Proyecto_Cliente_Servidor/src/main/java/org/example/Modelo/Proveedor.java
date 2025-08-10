package org.example.Modelo;

public abstract class Proveedor {
    protected int id;
    protected String nombre;
    protected String producto;

    //Contstructor
    public Proveedor(int id, String nombre, String producto) {
        this.id = id;
        this.nombre = nombre;
        this.producto = producto;
    }

    //Metodo para mostrar al proveedor
    public abstract void mostrarProveedor();
}
