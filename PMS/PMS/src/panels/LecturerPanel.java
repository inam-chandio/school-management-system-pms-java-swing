package panels;

import model.Presentation;
import model.Project;
import model.Submission;
import model.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class LecturerPanel extends JPanel {
    private JTextField presentationIdField, topicField;
    private JDatePickerImpl datePicker;
    private JButton scheduleButton, logoutButton, backButton, viewRequestsButton, evaluateReportButton;
    private JTable superviseeTable;
    private DefaultTableModel tableModel;
    private List<Presentation> presentationList;
    private List<User> userList;
    private List<Project> projectList;
    private List<Submission> submissionList;
    private JFrame frame;

    public LecturerPanel(List<User> userList, List<Presentation> presentationList, List<Project> projectList, List<Submission> submissionList, JFrame frame) {
        this.userList = userList;
        this.presentationList = presentationList;
        this.projectList = projectList;
        this.submissionList = submissionList;
        this.frame = frame;

        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        JLabel lecturerLabel = new JLabel("LECTURER", JLabel.CENTER);
        lecturerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        lecturerLabel.setOpaque(true);
        lecturerLabel.setBackground(Color.DARK_GRAY);
        lecturerLabel.setForeground(Color.WHITE);
        add(lecturerLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        addComponent(mainPanel, new JLabel("Presentation ID:"), gbc);

        gbc.gridx = 1;
        presentationIdField = new JTextField(20);
        addComponent(mainPanel, presentationIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addComponent(mainPanel, new JLabel("Date:"), gbc);

        gbc.gridx = 1;
        datePicker = createDatePicker();
        addComponent(mainPanel, datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addComponent(mainPanel, new JLabel("Topic:"), gbc);

        gbc.gridx = 1;
        topicField = new JTextField(20);
        addComponent(mainPanel, topicField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        scheduleButton = createStyledButton("Schedule Presentation", new Color(34, 139, 34), Color.WHITE, this::schedulePresentation);
        addComponent(mainPanel, scheduleButton, gbc);

        gbc.gridy = 4;
        viewRequestsButton = createStyledButton("View Presentation Requests", new Color(30, 144, 255), Color.WHITE, this::viewPresentationRequests);
        addComponent(mainPanel, viewRequestsButton, gbc);

        gbc.gridy = 5;
        evaluateReportButton = createStyledButton("Evaluate Report", new Color(255, 165, 0), Color.WHITE, this::evaluateReport);
        addComponent(mainPanel, evaluateReportButton, gbc);

        gbc.gridy = 6;
        logoutButton = createStyledButton("Logout", new Color(169, 169, 169), Color.BLACK, e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.setContentPane(new LoginPanel(userList, presentationList, projectList, submissionList, topFrame, "Lecturer"));
            topFrame.revalidate();
        });
        addComponent(mainPanel, logoutButton, gbc);

        gbc.gridy = 7;
        backButton = createStyledButton("Back", new Color(169, 169, 169), Color.BLACK, e -> switchToMainPanel());
        addComponent(mainPanel, backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Assessment Type", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        superviseeTable = new JTable(tableModel);
        superviseeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(superviseeTable);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        add(scrollPane, BorderLayout.SOUTH);

        loadSupervisees();
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc) {
        panel.add(component, gbc);
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

    private void schedulePresentation(ActionEvent e) {
        String presentationId = presentationIdField.getText();
        String topic = topicField.getText();
        Date date = (Date) datePicker.getModel().getValue();

        if (presentationId.isEmpty() || topic.isEmpty() || date == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        presentationList.add(new Presentation(presentationId, new java.sql.Date(date.getTime()), topic));
        JOptionPane.showMessageDialog(this, "Presentation scheduled successfully!");
    }

    private void viewPresentationRequests(ActionEvent e) {
        // Implement the functionality to view presentation requests
    }

    private void evaluateReport(ActionEvent e) {
        // Implement the functionality to evaluate reports with feedback
    }

    private void loadSupervisees() {
        tableModel.setRowCount(0);
        List<Project> superviseeProjects = projectList.stream()
                .filter(project -> userList.stream().anyMatch(user -> user.getId().equals(project.getStudentId()) && "Lecturer".equals(user.getRole())))
                .collect(Collectors.toList());

        for (Project project : superviseeProjects) {
            tableModel.addRow(new Object[]{project.getId(), project.getName(), project.getAssessmentType(), "Pending"});
        }
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
