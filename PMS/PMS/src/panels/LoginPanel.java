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

public class LoginPanel extends JPanel {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;
    private List<User> userList;
    private List<Presentation> presentationList;
    private List<Project> projectList;
    private List<Submission> submissionList;
    private JFrame frame;
    private String role;

    public LoginPanel(List<User> userList, List<Presentation> presentationList, List<Project> projectList, List<Submission> submissionList, JFrame frame, String role) {
        this.userList = userList;
        this.presentationList = presentationList;
        this.projectList = projectList;
        this.submissionList = submissionList;
        this.frame = frame;
        this.role = role;

        setPreferredSize(new Dimension(400, 300));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        JLabel loginLabel = new JLabel("LOGIN", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setOpaque(true);
        loginLabel.setBackground(Color.DARK_GRAY);
        loginLabel.setForeground(Color.WHITE);
        add(loginLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(Color.LIGHT_GRAY);

        formPanel.add(new JLabel("ID:"));
        idField = new JTextField(20);
        formPanel.add(idField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        loginButton = createStyledButton("Login", new Color(0, 123, 255), Color.WHITE, this::login);
        formPanel.add(loginButton);

        backButton = createStyledButton("Back", new Color(169, 169, 169), Color.BLACK, e -> switchToMainPanel());
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void login(ActionEvent e) {
        String id = idField.getText();
        String password = new String(passwordField.getPassword());

        User user = userList.stream()
                .filter(u -> u.getId().equals(id) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null && user.getRole().equals(role)) {
            switchToRolePanel(role);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid login. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void switchToRolePanel(String role) {
        JPanel panel = null;
        switch (role) {
            case "Admin":
                panel = new AdminPanel(userList, presentationList, projectList, submissionList, frame);
                break;
            case "Project Manager":
                panel = new ProjectManagerPanel(userList, projectList, presentationList, submissionList, frame);
                break;
            case "Lecturer":
                panel = new LecturerPanel(userList, presentationList, projectList, submissionList, frame);
                break;
            case "Student":
                panel = new StudentPanel(userList, projectList, submissionList, presentationList, frame);
                break;
        }
        if (panel != null) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(panel);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        }
    }

    private void switchToMainPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new MainPanel(userList, presentationList, projectList, submissionList, frame));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}
