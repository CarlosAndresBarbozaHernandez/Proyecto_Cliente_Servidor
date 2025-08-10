package org.example.Vista;

import org.example.Controlador.ConexionDB;

import javax.swing.*;
import java.sql.*;

public class RegistroVentana extends JFrame {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JButton btnRegistrar;
    private JPasswordField txtPassword;
    private JPanel MainPanel;
    private ConexionDB conexionDB = new ConexionDB();


    public RegistroVentana() {
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Registrarse");

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String contrasena = new String(txtPassword.getPassword());

            if (registrarUsuarioBD(nombre, correo, contrasena)) {
                JOptionPane.showMessageDialog(this, "Registro Exitoso!!!");
                this.dispose();
                new LoginVentana().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario.");
            }
        });
    }

    private boolean registrarUsuarioBD(String nombre, String correo, String contrasena) {
        conexionDB.setConexion();
        String sql = "INSERT INTO usuarios (ID_Usuario, Nombre, Direccion, ID_Tipo_Usuario, ID_Estado, primer_apellido, segundo_apellido, correo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        conexionDB.setConsulta(sql);

        try {
            PreparedStatement ps = conexionDB.getConsulta();
            int nuevoID = (int) (Math.random() * 10000);
            ps.setInt(1, nuevoID);
            ps.setString(2, nombre);
            ps.setInt(3, 0);
            ps.setInt(4, 2);
            ps.setInt(5, 1);
            ps.setString(6, "");
            ps.setString(7, "");
            ps.setString(8, correo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conexionDB.cerrarConexion();
        }
    }
}