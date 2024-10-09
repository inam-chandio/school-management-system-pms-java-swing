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

public class MainPanel extends JPanel {
    private JButton adminButton, projectManagerButton, lecturerButton, studentButton;
    private List<User> userList;
    private List<Presentation> presentationList;
    private List<Project> projectList;
    private List<Submission> submissionList;
    private JFrame frame;

    public MainPanel(List<User> userList, List<Presentation> presentationList, List<Project> projectList, List<Submission> submissionList, JFrame frame) {
        this.userList = userList;
        this.presentationList = presentationList;
        this.projectList = projectList;
        this.submissionList = submissionList;
        this.frame = frame;

        setPreferredSize(new Dimension(800, 600));
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        adminButton = createStyledButton("Admin", new Color(34, 139, 34), Color.WHITE, e -> switchToLoginPanel("Admin"));
        add(adminButton, gbc);

        gbc.gridy = 1;
        projectManagerButton = createStyledButton("Project Manager", new Color(30, 144, 255), Color.WHITE, e -> switchToLoginPanel("Project Manager"));
        add(projectManagerButton, gbc);

        gbc.gridy = 2;
        lecturerButton = createStyledButton("Lecturer", new Color(255, 165, 0), Color.WHITE, e -> switchToLoginPanel("Lecturer"));
        add(lecturerButton, gbc);

        gbc.gridy = 3;
        studentButton = createStyledButton("Student", new Color(169, 169, 169), Color.BLACK, e -> switchToLoginPanel("Student"));
        add(studentButton, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void switchToLoginPanel(String role) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new LoginPanel(userList, presentationList, projectList, submissionList, frame, role));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}
