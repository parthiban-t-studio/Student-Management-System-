import java.awt.*;
import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Dashboard");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1,10,10));

        JButton btnStudents = new JButton("Manage Students");
        JButton btnExit = new JButton("Exit");

        add(new JLabel("Welcome to Student Management System", JLabel.CENTER));
        add(btnStudents);
        add(btnExit);

        btnStudents.addActionListener(e -> new StudentManagementSystem());
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
