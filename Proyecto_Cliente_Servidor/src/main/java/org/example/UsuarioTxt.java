package org.example;

import java.io.*;

public class UsuarioTxt {
    private static final String ARCHIVO = "usuarios.txt";

    public static void registrar(Usuario user, String contrasena) {
        String contrasenaHash = HashUtil.hashPassword(contrasena);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(user.getNombre() + "," + user.getCorreo() + "," + contrasenaHash + "," + user.getRol().name());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario buscarPorCorreoYContrasena(String correo, String contrasena) {
        String contrasenaHashIngresada = HashUtil.hashPassword(contrasena);
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String nombre = partes[0];
                    String correoUser = partes[1];
                    String contrasenaHashUser = partes[2];
                    String rolStr = partes[3];
                    if (correoUser.equals(correo) && contrasenaHashUser.equals(contrasenaHashIngresada)) {
                        Rol rol = Rol.valueOf(rolStr.toUpperCase());
                        return new Usuario(nombre, correo, rol);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}