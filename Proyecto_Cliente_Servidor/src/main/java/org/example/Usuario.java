package org.example;

public class Usuario {
    private String nombre;
    private String correo;
    private Rol rol;
    private String primer_apellido;
    private String segundo_apellido;

    public Usuario(String nombre, String correo, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Rol getRol() {
        return rol;
    }
}