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
import java.util.List;

public class ProjectManagerPanel extends JPanel {
    private JTextField projectIdField, projectNameField;
    private JComboBox<String> studentIdBox, supervisorBox, secondMarkerBox, assessmentTypeBox;
    private JButton assignProjectButton, viewStatusButton, backButton;
    private JTable projectTable;
    private DefaultTableModel tableModel;
    private List<Project> projectList;
    private List<User> userList;
    private List<Presentation> presentationList;
    private List<Submission> submissionList;
    private JFrame frame;

    public ProjectManagerPanel(List<User> userList, List<Project> projectList, List<Presentation> presentationList, List<Submission> submissionList, JFrame frame) {
        this.userList = userList;
        this.projectList = projectList;
        this.presentationList = presentationList;
        this.submissionList = submissionList;
        this.frame = frame;

        initializeComponents();
        layoutComponents();
        loadSupervisors();
    }

    private void initializeComponents() {
        projectIdField = new JTextField(20);
        projectNameField = new JTextField(20);
        studentIdBox = new JComboBox<>();
        assessmentTypeBox = new JComboBox<>(new String[]{"Internship", "Investigation Reports", "CP1", "CP2", "RMCP", "FYP"});
        supervisorBox = new JComboBox<>();
        secondMarkerBox = new JComboBox<>();
        assignProjectButton = createStyledButton("Assign Project", new Color(34, 139, 34), Color.WHITE, this::assignProject);
        viewStatusButton = createStyledButton("View Project Status", new Color(30, 144, 255), Color.WHITE, this::viewProjectStatus);
        backButton = createStyledButton("Back", new Color(169, 169, 169), Color.BLACK, e -> switchToMainPanel());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Student ID", "Assessment Type", "Supervisor", "Second Marker"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        projectTable = new JTable(tableModel);
        projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void layoutComponents() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        JLabel pmLabel = new JLabel("PROJECT MANAGER", JLabel.CENTER);
        pmLabel.setFont(new Font("Arial", Font.BOLD, 24));
        pmLabel.setOpaque(true);
        pmLabel.setBackground(Color.DARK_GRAY);
        pmLabel.setForeground(Color.WHITE);
        add(pmLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        addComponent(mainPanel, new JLabel("Project ID:"), gbc);

        gbc.gridx = 1;
        addComponent(mainPanel, projectIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addComponent(mainPanel, new JLabel("Project Name:"), gbc);

        gbc.gridx = 1;
        addComponent(mainPanel, projectNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addComponent(mainPanel, new JLabel("Student ID:"), gbc);

        gbc.gridx = 1;
        loadStudentIds();
        addComponent(mainPanel, studentIdBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addComponent(mainPanel, new JLabel("Assessment Type:"), gbc);

        gbc.gridx = 1;
        addComponent(mainPanel, assessmentTypeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addComponent(mainPanel, new JLabel("Supervisor:"), gbc);

        gbc.gridx = 1;
        addComponent(mainPanel, supervisorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        addComponent(mainPanel, new JLabel("Second Marker:"), gbc);

        gbc.gridx = 1;
        addComponent(mainPanel, secondMarkerBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addComponent(mainPanel, assignProjectButton, gbc);

        gbc.gridy = 7;
        addComponent(mainPanel, viewStatusButton, gbc);

        gbc.gridy = 8;
        addComponent(mainPanel, backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc) {
        panel.add(component, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void loadStudentIds() {
        studentIdBox.removeAllItems();
        for (User user : userList) {
            if ("Student".equals(user.getRole())) {
                studentIdBox.addItem(user.getId());
            }
        }
    }

    private void loadSupervisors() {
        supervisorBox.removeAllItems();
        secondMarkerBox.removeAllItems();
        for (User user : userList) {
            if ("Lecturer".equals(user.getRole())) {
                supervisorBox.addItem(user.getId());
                secondMarkerBox.addItem(user.getId());
            }
        }
    }

    private void assignProject(ActionEvent e) {
        String projectId = projectIdField.getText();
        String projectName = projectNameField.getText();
        String studentId = (String) studentIdBox.getSelectedItem();
        String assessmentType = (String) assessmentTypeBox.getSelectedItem();
        String supervisorId = (String) supervisorBox.getSelectedItem();
        String secondMarkerId = (String) secondMarkerBox.getSelectedItem();

        if (projectId.isEmpty() || projectName.isEmpty() || studentId == null || assessmentType == null || supervisorId == null || secondMarkerId == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        projectList.add(new Project(projectId, projectName, studentId, assessmentType, supervisorId, secondMarkerId));
        JOptionPane.showMessageDialog(this, "Project assigned successfully!");
        clearFields();
        viewProjectStatus(null);
    }

    private void viewProjectStatus(ActionEvent e) {
        tableModel.setRowCount(0);
        for (Project project : projectList) {
            tableModel.addRow(new Object[]{project.getId(), project.getName(), project.getStudentId(), project.getAssessmentType(), project.getSupervisorId(), project.getSecondMarkerId()});
        }
    }

    private void clearFields() {
        projectIdField.setText("");
        projectNameField.setText("");
        studentIdBox.setSelectedIndex(-1);
        assessmentTypeBox.setSelectedIndex(-1);
        supervisorBox.setSelectedIndex(-1);
        secondMarkerBox.setSelectedIndex(-1);
    }

    private void switchToMainPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new MainPanel(userList, presentationList, projectList, submissionList, frame));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}
