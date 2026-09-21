import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LoginForm extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin;

    public LoginForm() {

        setTitle("Login - Student Management System");
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2,10,10));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Login");
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if(username.equals("admin") && password.equals("1234")) {
            dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(this,"Invalid Login!");
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
