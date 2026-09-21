import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public class StudentManagementSystem extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtID, txtFirst, txtLast, txtCourse, txtEmail, txtPhone, txtGrade, txtAttendance;
    private File file = new File("students.txt");

    private boolean darkMode = false;

    public StudentManagementSystem() {

        setTitle("Professional Student Management System");
        setSize(1300,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTopBar();
        createSidebar();
        createMainPanel();
        createStatusBar();

        loadFromFile();
        setVisible(true);
    }

    // ================= TOP BAR =================
    private void createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(100,60));
        topBar.setBackground(new Color(33,150,243));

        JLabel logo = new JLabel("  🎓 SMS");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JLabel title = new JLabel("Student Management System", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JButton darkToggle = new JButton("☾");
        darkToggle.setFocusPainted(false);
        darkToggle.addActionListener(e -> toggleDarkMode());

        topBar.add(logo, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(darkToggle, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    // ================= SIDEBAR =================
    private void createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(6,1,10,10));
        sidebar.setPreferredSize(new Dimension(200,100));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        sidebar.setBackground(new Color(44,62,80));

        String[] menuItems = {"Dashboard","Students","Reports","Settings","Logout"};

        for(String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(52,73,94));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            sidebar.add(btn);
        }

        add(sidebar, BorderLayout.WEST);
    }

    // ================= MAIN PANEL =================
    private void createMainPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.setBackground(Color.WHITE);

        // ---- FORM PANEL (Card Style) ----
        JPanel formPanel = new JPanel(new GridLayout(4,4,15,15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        txtID = new JTextField();
        txtFirst = new JTextField();
        txtLast = new JTextField();
        txtCourse = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtGrade = new JTextField();
        txtAttendance = new JTextField();

        formPanel.add(new JLabel("ID")); formPanel.add(txtID);
        formPanel.add(new JLabel("First Name")); formPanel.add(txtFirst);
        formPanel.add(new JLabel("Last Name")); formPanel.add(txtLast);
        formPanel.add(new JLabel("Course")); formPanel.add(txtCourse);
        formPanel.add(new JLabel("Email")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Phone")); formPanel.add(txtPhone);
        formPanel.add(new JLabel("Grade")); formPanel.add(txtGrade);
        formPanel.add(new JLabel("Attendance (%)")); formPanel.add(txtAttendance);

        JButton btnAdd = createMainButton("Add", new Color(46,204,113));
        JButton btnUpdate = createMainButton("Update", new Color(241,196,15));
        JButton btnDelete = createMainButton("Delete", new Color(231,76,60));

        JPanel actionPanel = new JPanel();
        actionPanel.add(btnAdd);
        actionPanel.add(btnUpdate);
        actionPanel.add(btnDelete);

        // ---- TABLE ----
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "ID","First","Last","Course","Email","Phone","Grade","Attendance"
        });

        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(33,150,243));
        table.getTableHeader().setForeground(Color.WHITE);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // ---- EVENTS ----
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
    }

    // ================= STATUS BAR =================
    private void createStatusBar() {
        JLabel status = new JLabel("  Status: Ready");
        status.setPreferredSize(new Dimension(100,30));
        status.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(status, BorderLayout.SOUTH);
    }

    // ================= BUTTON STYLE =================
    private JButton createMainButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ================= DARK MODE =================
    private void toggleDarkMode() {
        darkMode = !darkMode;
        getContentPane().setBackground(darkMode ? Color.DARK_GRAY : Color.WHITE);
        repaint();
    }

    // ================= CRUD =================
    private void addStudent() {
        model.addRow(new Object[]{
                txtID.getText(), txtFirst.getText(), txtLast.getText(),
                txtCourse.getText(), txtEmail.getText(),
                txtPhone.getText(), txtGrade.getText(),
                txtAttendance.getText()
        });
        saveToFile();
    }

    private void updateStudent() {
        int row = table.getSelectedRow();
        if(row>=0) {
            model.setValueAt(txtID.getText(),row,0);
            model.setValueAt(txtFirst.getText(),row,1);
            model.setValueAt(txtLast.getText(),row,2);
            model.setValueAt(txtCourse.getText(),row,3);
            model.setValueAt(txtEmail.getText(),row,4);
            model.setValueAt(txtPhone.getText(),row,5);
            model.setValueAt(txtGrade.getText(),row,6);
            model.setValueAt(txtAttendance.getText(),row,7);
            saveToFile();
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if(row>=0){
            model.removeRow(row);
            saveToFile();
        }
    }

    private void saveToFile() {
        try(PrintWriter pw = new PrintWriter(new FileWriter(file))){
            for(int i=0;i<model.getRowCount();i++){
                for(int j=0;j<model.getColumnCount();j++){
                    pw.print(model.getValueAt(i,j));
                    if(j<model.getColumnCount()-1) pw.print(",");
                }
                pw.println();
            }
        } catch(Exception e){}
    }

    private void loadFromFile() {
        if(!file.exists()) return;
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null)
                model.addRow(line.split(","));
        } catch(Exception e){}
    }

    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}
