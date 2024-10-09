package panels;

import model.Presentation;
import model.Project;
import model.Submission;
import model.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class StudentPanel extends JPanel {
    private JComboBox<String> projectIdBox;
    private JTextField submissionLinkField, assessmentTypeField;
    private JDatePickerImpl datePicker;
    private JButton submitButton, requestPresentationButton, checkStatusButton, backButton;
    private List<Project> projectList;
    private List<Submission> submissionList;
    private List<User> userList;
    private List<Presentation> presentationList;
    private JFrame frame;

    public StudentPanel(List<User> userList, List<Project> projectList, List<Submission> submissionList, List<Presentation> presentationList, JFrame frame) {
        this.userList = userList;
        this.projectList = projectList;
        this.submissionList = submissionList;
        this.presentationList = presentationList;
        this.frame = frame;

        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        JLabel studentLabel = new JLabel("STUDENT", JLabel.CENTER);
        studentLabel.setFont(new Font("Arial", Font.BOLD, 32));
        studentLabel.setOpaque(true);
        studentLabel.setBackground(Color.DARK_GRAY);
        studentLabel.setForeground(Color.WHITE);
        add(studentLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        addComponent(mainPanel, new JLabel("Project ID:"), gbc);

        gbc.gridx = 1;
        projectIdBox = new JComboBox<>();
        loadProjectIds();
        projectIdBox.setFont(new Font("Arial", Font.PLAIN, 18));
        addComponent(mainPanel, projectIdBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addComponent(mainPanel, new JLabel("Submission Link:"), gbc);

        gbc.gridx = 1;
        submissionLinkField = new JTextField();
        submissionLinkField.setFont(new Font("Arial", Font.PLAIN, 18));
        addComponent(mainPanel, submissionLinkField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addComponent(mainPanel, new JLabel("Assessment Type:"), gbc);

        gbc.gridx = 1;
        assessmentTypeField = new JTextField();
        assessmentTypeField.setFont(new Font("Arial", Font.PLAIN, 18));
        addComponent(mainPanel, assessmentTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addComponent(mainPanel, new JLabel("Submission Date:"), gbc);

        gbc.gridx = 1;
        datePicker = createDatePicker();
        addComponent(mainPanel, datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = createStyledButton("Submit Project", new Color(34, 139, 34), Color.WHITE, this::submitProject);
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        addComponent(mainPanel, submitButton, gbc);

        gbc.gridy = 5;
        requestPresentationButton = createStyledButton("Request Presentation Date", new Color(30, 144, 255), Color.WHITE, this::requestPresentationDate);
        addComponent(mainPanel, requestPresentationButton, gbc);

        gbc.gridy = 6;
        checkStatusButton = createStyledButton("Check Submission Status", new Color(255, 165, 0), Color.WHITE, this::checkSubmissionStatus);
        addComponent(mainPanel, checkStatusButton, gbc);

        gbc.gridy = 7;
        backButton = createStyledButton("Back", new Color(169, 169, 169), Color.BLACK, e -> switchToMainPanel());
        addComponent(mainPanel, backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc) {
        component.setPreferredSize(new Dimension(300, 30));
        panel.add(component, gbc);
    }

    private void loadProjectIds() {
        projectIdBox.removeAllItems();
        for (Project project : projectList) {
            projectIdBox.addItem(project.getId());
        }
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void submitProject(ActionEvent e) {
        String projectId = (String) projectIdBox.getSelectedItem();
        String submissionLink = submissionLinkField.getText();
        String assessmentType = assessmentTypeField.getText();
        Date date = (Date) datePicker.getModel().getValue();

        if (projectId == null || submissionLink.isEmpty() || assessmentType.isEmpty() || date == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        submissionList.add(new Submission(projectId, submissionLink, assessmentType, new java.sql.Date(date.getTime())));
        JOptionPane.showMessageDialog(this, "Project submitted successfully!");
        clearFields();
    }

    private void requestPresentationDate(ActionEvent e) {
        String projectId = (String) projectIdBox.getSelectedItem();
        if (projectId == null) {
            JOptionPane.showMessageDialog(this, "Please select a project ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date date = (Date) datePicker.getModel().getValue();
        if (date == null) {
            JOptionPane.showMessageDialog(this, "Please select a date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Presentation requestedPresentation = new Presentation(
                projectId,
                new java.sql.Date(date.getTime()),
                "Requested Presentation"
        );
        presentationList.add(requestedPresentation);
        JOptionPane.showMessageDialog(this, "Presentation date requested successfully!");
    }

    private void checkSubmissionStatus(ActionEvent e) {
        String projectId = (String) projectIdBox.getSelectedItem();
        if (projectId == null) {
            JOptionPane.showMessageDialog(this, "Please select a project ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Submission foundSubmission = submissionList.stream()
                .filter(submission -> submission.getProjectId().equals(projectId))
                .findFirst()
                .orElse(null);

        if (foundSubmission != null) {
            JOptionPane.showMessageDialog(this, "Submission Status: " + foundSubmission.getStatus(), "Submission Status", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No submission found for the selected project ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        projectIdBox.setSelectedIndex(-1);
        submissionLinkField.setText("");
        assessmentTypeField.setText("");
        datePicker.getModel().setSelected(false);
    }

    private void switchToMainPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new MainPanel(userList, presentationList, projectList, submissionList, frame));
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof Date) {
                    return dateFormatter.format((Date) value);
                } else if (value instanceof Calendar) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }
            }
            return "";
        }
    }
}
