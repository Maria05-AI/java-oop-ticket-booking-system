/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ScheduleManagementFrame extends JFrame {
    
    private JTextField scheduleIdField;
    private JTextField transportationTypeField;
    private JTextField departureTimeField;
    private JTextField arrivalTimeField;

    private DefaultListModel<String> scheduleListModel;
    private JList<String> scheduleList;

    public ScheduleManagementFrame() {
        setTitle("Schedule Management");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        scheduleIdField = new JTextField();
        transportationTypeField = new JTextField();
        departureTimeField = new JTextField();
        arrivalTimeField = new JTextField();

        panel.add(new JLabel("Schedule ID:"));
        panel.add(scheduleIdField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Transportation Type:"));
        panel.add(transportationTypeField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Departure Time:"));
        panel.add(departureTimeField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Arrival Time:"));
        panel.add(arrivalTimeField);
        panel.add(Box.createVerticalStrut(20));

        JButton addButton = new JButton("Add Schedule");
        addButton.addActionListener(new AddScheduleListener());

        JButton updateButton = new JButton("Update Schedule");
        updateButton.addActionListener(new UpdateScheduleListener());

        JButton deleteButton = new JButton("Delete Schedule");
        deleteButton.addActionListener(new DeleteScheduleListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel);

        scheduleListModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleListModel);
        scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(scheduleList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Schedules"));

        panel.add(Box.createVerticalStrut(20));
        panel.add(scrollPane);

        add(panel);

       
        loadInitialSchedules();

        setVisible(true);
    }

    private void loadInitialSchedules() {
        
        scheduleListModel.addElement(
            "Schedule ID: 9  | Type: Bus   | Departure: 08:00:00 | Arrival: 10:00:00 | From: Station A | To: Station B | Seats: 40"
        );
        scheduleListModel.addElement(
            "Schedule ID: 10 | Type: Train | Departure: 12:00:00 | Arrival: 14:00:00 | From: Station B | To: Station C | Seats: 120"
        );
        scheduleListModel.addElement(
            "Schedule ID: 11 | Type: Bus   | Departure: 09:00:00 | Arrival: 13:00:00 | From: Station A | To: Station C | Seats: 35"
        );
    }

    private class AddScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String scheduleId = scheduleIdField.getText();
            String transportationType = transportationTypeField.getText();
            String departureTime = departureTimeField.getText();
            String arrivalTime = arrivalTimeField.getText();

            if (validateInput(scheduleId, transportationType, departureTime, arrivalTime)) {
                String scheduleInfo = 
                    "ID: " + scheduleId +
                    " | Type: " + transportationType +
                    " | Departure: " + departureTime +
                    " | Arrival: " + arrivalTime;
                scheduleListModel.addElement(scheduleInfo);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                    ScheduleManagementFrame.this,
                    "Please fill in all fields correctly.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class UpdateScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = scheduleList.getSelectedIndex();
            if (idx != -1) {
                String scheduleId = scheduleIdField.getText();
                String transportationType = transportationTypeField.getText();
                String departureTime = departureTimeField.getText();
                String arrivalTime = arrivalTimeField.getText();

                if (validateInput(scheduleId, transportationType, departureTime, arrivalTime)) {
                    String updated = "ID: " + scheduleId +
                                     " | Type: " + transportationType +
                                     " | Departure: " + departureTime +
                                     " | Arrival: " + arrivalTime;
                    scheduleListModel.set(idx, updated);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(
                        ScheduleManagementFrame.this,
                        "Please fill in all fields correctly.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    ScheduleManagementFrame.this,
                    "Please select a schedule to update.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class DeleteScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = scheduleList.getSelectedIndex();
            if (idx != -1) {
                scheduleListModel.remove(idx);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                    ScheduleManagementFrame.this,
                    "Please select a schedule to delete.",
                    "Deletion Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private boolean validateInput(String scheduleId, String transportationType, String departureTime, String arrivalTime) {
        return !scheduleId.trim().isEmpty() && !transportationType.trim().isEmpty()
               && !departureTime.trim().isEmpty() && !arrivalTime.trim().isEmpty();
    }

    private void clearFields() {
        scheduleIdField.setText("");
        transportationTypeField.setText("");
        departureTimeField.setText("");
        arrivalTimeField.setText("");
    }
}
