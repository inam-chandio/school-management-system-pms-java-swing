import panels.AdminPanel;
import panels.LecturerPanel;
import panels.ProjectManagerPanel;
import panels.RegistrationPanel;
import panels.StudentPanel;
import panels.LoginPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import model.Presentation;
import model.Project;
import model.Submission;
import model.User;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<User> userList = new ArrayList<>();
            List<Presentation> presentationList = new ArrayList<>();
            List<Project> projectList = new ArrayList<>();
            List<Submission> submissionList = new ArrayList<>();

            JFrame frame = new JFrame("Main Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            RegistrationPanel registrationPanel = new RegistrationPanel(userList, presentationList, projectList, submissionList, frame);
            frame.setContentPane(registrationPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
