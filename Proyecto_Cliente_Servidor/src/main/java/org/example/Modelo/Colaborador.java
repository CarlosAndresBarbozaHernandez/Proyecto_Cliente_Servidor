package org.example.Modelo;

public abstract class Colaborador {
    protected int id;
    protected String nombre;
    protected String correo;
    protected String puesto;

    //Contstructor

    public Colaborador(int id, String nombre, String correo, String puesto) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puesto = puesto;
    }
    //Metodo para mostrar al colaborador
    public abstract void mostrarDatos();
}
