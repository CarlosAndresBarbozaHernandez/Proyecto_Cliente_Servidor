package org.example;

import javax.swing.*;

public class LoginVentana extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIniciar;
    private JButton btnRegistrarse;

    public LoginVentana(){
        setTitle("Inicio Sesion");
        setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnIniciar.addActionListener(e -> {
            Usuario user = UsuarioTxt.buscarCorreoContrasena(
                    txtCorreo.getText(),
                    new String(txtPassword.getPassword())
            );

            if (user != null){
                JOptionPane.showMessageDialog(this, "Bienvenido, " + user.getNombre());
                new Carrito().setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "El correo o la contraseña se encuentra incorrecta!!!");
            }
        });

        btnRegistrarse.addActionListener(e -> {
            this.dispose();
            new RegistroVentana().setVisible(true);
        });
    }

}
