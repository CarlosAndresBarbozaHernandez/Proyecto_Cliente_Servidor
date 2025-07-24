package org.example;

import javax.swing.*;

public class LoginVentana extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIniciar;
    private JButton btnRegistrarse;
    private JPanel MainPanel;

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
            Usuario user = UsuarioTxt.buscarPorCorreoYContrasena(correo, contrasena);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido, " + user.getNombre() + " (" + user.getRol() + ")");
                new Carrito().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "El correo o la contraseña se encuentra incorrecta!!!");
            }
        });

        btnRegistrarse.addActionListener(e -> {
            this.dispose();
            new RegistroVentana().setVisible(true);
        });
    }
}