package panels;

import model.Presentation;
import model.Project;
import model.Submission;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class AdminPanel extends JPanel {
    private JTextField nameField, idField, emailField, passwordField;
    private JComboBox<String> roleBox;
    private JButton addButton, deleteButton, viewButton, updateButton, logoutButton, backButton;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private List<User> userList;
    private List<Presentation> presentationList;
    private List<Project> projectList;
    private List<Submission> submissionList;
    private JFrame frame;

    public AdminPanel(List<User> userList, List<Presentation> presentationList, List<Project> projectList, List<Submission> submissionList, JFrame frame) {
        this.userList = userList;
        this.presentationList = presentationList;
        this.projectList = projectList;
        this.submissionList = submissionList;
        this.frame = frame;

        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        // Create the top label
        JLabel adminLabel = new JLabel("ADMIN", JLabel.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 24));
        adminLabel.setOpaque(true);
        adminLabel.setBackground(Color.DARK_GRAY);
        adminLabel.setForeground(Color.WHITE);
        add(adminLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("User Information"));
        topPanel.setBackground(Color.WHITE);

        topPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        topPanel.add(nameField);

        topPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        topPanel.add(idField);

        topPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        topPanel.add(emailField);

        topPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        topPanel.add(passwordField);

        topPanel.add(new JLabel("Role:"));
        roleBox = new JComboBox<>(new String[]{"Student", "Lecturer", "Project Manager"});
        topPanel.add(roleBox);

        addButton = createStyledButton("Add User", new Color(34, 139, 34), Color.WHITE, this::addUser);
        topPanel.add(addButton);

        deleteButton = createStyledButton("Delete User", new Color(178, 34, 34), Color.WHITE, this::deleteUser);
        topPanel.add(deleteButton);

        viewButton = createStyledButton("View Users", new Color(30, 144, 255), Color.WHITE, this::viewUsers);
        topPanel.add(viewButton);

        updateButton = createStyledButton("Update User", new Color(255, 165, 0), Color.WHITE, this::updateUser);
        topPanel.add(updateButton);

        logoutButton = createStyledButton("Logout", new Color(169, 169, 169), Color.BLACK, e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new LoginPanel(userList, presentationList, projectList, submissionList, frame, "Admin"));
            topFrame.revalidate();
        });
        topPanel.add(logoutButton);

        backButton = createStyledButton("Back", new Color(169, 169, 169), Color.BLACK, e -> switchToMainPanel());
        topPanel.add(backButton);

        add(topPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Role"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(800, 150)); // Set preferred size for the table
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void addUser(ActionEvent e) {
        String name = nameField.getText();
        String id = idField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = (String) roleBox.getSelectedItem();

        if (name.isEmpty() || id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userList.add(new User(id, name, email, password, role));
        JOptionPane.showMessageDialog(this, "User added successfully!");
        clearFields();
    }

    private void deleteUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        userList.removeIf(user -> user.getId().equals(id));
        tableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "User deleted successfully!");
    }

    private void viewUsers(ActionEvent e) {
        tableModel.setRowCount(0);
        for (User user : userList) {
            if (!user.getRole().equals("Admin")) {
                tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), user.getRole()});
            }
        }
    }

    private void updateUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        User user = userList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        idField.setText(user.getId());
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        roleBox.setSelectedItem(user.getRole());

        addButton.setText("Save Changes");
        addButton.removeActionListener(this::addUser);
        addButton.addActionListener(e1 -> {
            String newName = nameField.getText();
            String newEmail = emailField.getText();
            String newPassword = passwordField.getText();
            String newRole = (String) roleBox.getSelectedItem();

            if (newName.isEmpty() || id.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            user.setName(newName);
            user.setEmail(newEmail);
            user.setPassword(newPassword);
            user.setRole(newRole);
            JOptionPane.showMessageDialog(this, "User updated successfully!");
            clearFields();
            viewUsers(null);
            addButton.setText("Add User");
            addButton.removeActionListener(this::updateUser);
            addButton.addActionListener(this::addUser);
        });
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        emailField.setText("");
        passwordField.setText("");
        roleBox.setSelectedIndex(0);
    }

    private void switchToMainPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new MainPanel(userList, presentationList, projectList, submissionList, frame));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}
