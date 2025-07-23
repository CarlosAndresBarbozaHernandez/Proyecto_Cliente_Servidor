package org.example;

import javax.swing.*;

public class RegistroVentana extends JFrame{
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JButton btnRegistrar;
    private JPasswordField txtPassword;
    private JPanel MainPanel;

    public RegistroVentana(){
        setContentPane(MainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Registrarse");

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
