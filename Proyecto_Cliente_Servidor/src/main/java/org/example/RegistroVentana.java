package org.example;

import javax.swing.*;

public class RegistroVentana extends JFrame{
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JButton btnRegistrar;
    private JPasswordField txtPassword;

    public RegistroVentana(){
        setTitle("Registro Cliente");
        setSize(300,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        btnRegistrar.addActionListener(e -> {
            Usuario newUser = new Usuario(
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    new String(txtPassword.getPassword())
            );
            UsuarioTxt.registrar(newUser);
            JOptionPane.showMessageDialog(this, "Registro Exitoso!!!");
            this.dispose();
            new LoginVentana().setVisible(true);
        });

    }
}
