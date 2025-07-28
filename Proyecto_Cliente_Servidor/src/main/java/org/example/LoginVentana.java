package org.example;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginVentana extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIniciar;
    private JButton btnRegistrarse;
    private JPanel MainPanel;
    private ConexionDB conexionDB = new ConexionDB();



    public LoginVentana() {
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Iniciar Sesion");

        btnIniciar.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String contrasena = new String(txtPassword.getPassword());
            Usuario user = buscarUsuarioBD(correo, contrasena);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + user.getNombre() + " (" + user.getRol() + ")");
                new Carrito().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El correo o la contraseña es incorrecta.");
            }
        });

        btnRegistrarse.addActionListener(e -> {
            this.dispose();
            new RegistroVentana().setVisible(true);
        });
    }

    private Usuario buscarUsuarioBD(String correo, String contrasena) {
        conexionDB.setConexion();
        String sql = "SELECT Nombre, ID_Tipo_Usuario FROM usuarios WHERE correo = ? AND ID_Estado = 1";
        conexionDB.setConsulta(sql);

        try {
            PreparedStatement ps = conexionDB.getConsulta();
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                int tipoUsuario = rs.getInt("ID_Tipo_Usuario");
                Rol rol = (tipoUsuario == 1) ? Rol.ADMINISTRADOR : Rol.CLIENTE; // Ajustar según ID reales
                return new Usuario(nombre, correo, rol);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            conexionDB.cerrarConexion();
        }

        return null;
    }
}