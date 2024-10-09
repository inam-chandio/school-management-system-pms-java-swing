package ui;

import panels.MainPanel;
import panels.RegistrationPanel;
import utils.DataStorage;
import model.Presentation;
import model.Project;
import model.Submission;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainApp {
    private static final String USERS_FILE = "users.dat";
    private static final String PRESENTATIONS_FILE = "presentations.dat";
    private static final String PROJECTS_FILE = "projects.dat";
    private static final String SUBMISSIONS_FILE = "submissions.dat";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<User> userList = DataStorage.loadData(USERS_FILE);
            List<Presentation> presentationList = DataStorage.loadData(PRESENTATIONS_FILE);
            List<Project> projectList = DataStorage.loadData(PROJECTS_FILE);
            List<Submission> submissionList = DataStorage.loadData(SUBMISSIONS_FILE);

            JFrame frame = new JFrame("Main Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            RegistrationPanel registrationPanel = new RegistrationPanel(userList, presentationList, projectList, submissionList, frame);
            frame.getContentPane().add(registrationPanel);
            frame.pack();
            frame.setSize(new Dimension(800, 600));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DataStorage.saveData(userList, USERS_FILE);
                    DataStorage.saveData(presentationList, PRESENTATIONS_FILE);
                    DataStorage.saveData(projectList, PROJECTS_FILE);
                    DataStorage.saveData(submissionList, SUBMISSIONS_FILE);
                }
            });
        });
    }
}
