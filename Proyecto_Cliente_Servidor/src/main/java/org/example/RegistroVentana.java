package org.example;

import javax.swing.*;

public class RegistroVentana extends JFrame {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JButton btnRegistrar;
    private JPasswordField txtPassword;
    private JPanel MainPanel;

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
            Rol rol = Rol.CLIENTE;  // Rol por defecto: CLIENTE
            Usuario newUser = new Usuario(nombre, correo, rol);
            UsuarioTxt.registrar(newUser, contrasena);
            JOptionPane.showMessageDialog(this, "Registro Exitoso!!!");
            this.dispose();
            new LoginVentana().setVisible(true);
        });
    }
}