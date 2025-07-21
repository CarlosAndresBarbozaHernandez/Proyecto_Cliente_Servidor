package org.example;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

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
