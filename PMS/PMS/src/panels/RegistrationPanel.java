package panels;

import model.Presentation;
import model.Project;
import model.Submission;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegistrationPanel extends JPanel {
    private JTextField nameField;
    private JTextField idField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton registerButton, goButton;
    private List<User> userList;
    private List<Presentation> presentationList;
    private List<Project> projectList;
    private List<Submission> submissionList;
    private JFrame frame;

    public RegistrationPanel(List<User> userList, List<Presentation> presentationList, List<Project> projectList, List<Submission> submissionList, JFrame frame) {
        this.userList = userList;
        this.presentationList = presentationList;
        this.projectList = projectList;
        this.submissionList = submissionList;
        this.frame = frame;

        setPreferredSize(new Dimension(600, 300));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        JLabel registerLabel = new JLabel("REGISTRATION", JLabel.CENTER);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        registerLabel.setOpaque(true);
        registerLabel.setBackground(Color.DARK_GRAY);
        registerLabel.setForeground(Color.WHITE);
        add(registerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(Color.LIGHT_GRAY);

        formPanel.add(createLabel("Name:"));
        nameField = createTextField();
        formPanel.add(nameField);

        formPanel.add(createLabel("ID:"));
        idField = createTextField();
        formPanel.add(idField);

        formPanel.add(createLabel("Email:"));
        emailField = createTextField();
        formPanel.add(emailField);

        formPanel.add(createLabel("Password:"));
        passwordField = createPasswordField();
        formPanel.add(passwordField);

        roleBox = new JComboBox<>(new String[]{"Student", "Lecturer", "Admin", "Project Manager"});
        roleBox.setFont(new Font("Arial", Font.PLAIN, 18));
        formPanel.add(createLabel("Role:"));
        formPanel.add(roleBox);

        registerButton = createStyledButton("Register", new Color(34, 139, 34), Color.WHITE, this::registerUser);
        formPanel.add(registerButton);

        goButton = createStyledButton("Already have an account", new Color(0, 123, 255), Color.WHITE, this::goToMainPanel);
        formPanel.add(goButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        return passwordField;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void registerUser(ActionEvent e) {
        String name = nameField.getText();
        String id = idField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (!name.isEmpty() && !id.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            userList.add(new User(id, name, email, password, role));
            JOptionPane.showMessageDialog(this, "User registered successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goToMainPanel(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new MainPanel(userList, presentationList, projectList, submissionList, frame));
        frame.pack();
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }
}
