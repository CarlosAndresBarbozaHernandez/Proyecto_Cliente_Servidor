package org.example;

import java.io.*;
import java.util.ArrayList;

public class UsuarioTxt {
    private static final String ARCHIVO = "usuarios.txt";

    public static void registrar(Usuario user){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))){
            writer.write(user.getNombre() + "," + user.getCorreo() + "," + user.getContrasena());
            writer.newLine();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Usuario buscarCorreoContrasena(String correo, String contrasena){
        try(BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))){
            String linea;
            while  ((linea = reader.readLine()) != null){
                String[] partes = linea.split(",");
                if (partes.length == 3){
                    String nombre = partes[0];
                    String correoUser = partes[1];
                    String contrasenaUser = partes[2];

                    if(correoUser.equals(correo) && contrasenaUser.equals(contrasena)){
                        return new Usuario(nombre, correo, contrasena);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
