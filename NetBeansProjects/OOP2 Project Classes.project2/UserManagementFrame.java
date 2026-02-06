/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserManagementFrame extends JFrame{
    
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox; 
    private JTextArea userListArea;

    public UserManagementFrame() {
        setTitle("User Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameField = new JTextField();
        emailField = new JTextField();

        String[] roles = {"Passenger", "Employee", "Admin"};
        roleComboBox = new JComboBox<>(roles);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);
        panel.add(Box.createVerticalStrut(20));

        JButton addButton    = new JButton("Add User");
        JButton removeButton = new JButton("Remove User");
        JButton updateButton = new JButton("Update User");

        addButton.addActionListener(new AddUserListener());
        removeButton.addActionListener(new RemoveUserListener());
        updateButton.addActionListener(new UpdateUserListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        panel.add(buttonPanel);

        userListArea = new JTextArea(8, 40);
        userListArea.setEditable(false);
        userListArea.setBorder(BorderFactory.createTitledBorder("Current Users"));
        JScrollPane scrollPane = new JScrollPane(userListArea);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);

        
        loadInitialUsers();
    }

    private void loadInitialUsers() {
       
        String[][] initial = {
            {"Admin1",     "admin1@example.com", "0501234567",   "adminpass1",   "Admin"},
            {"Admin2",     "admin2@example.com", "0509876543",   "adminpass2",   "Admin"},
            {"Fatimah",    "fatimah@gmail.com",  "0554567890",   "pass123",      "Passenger"},
            {"Ali",        "ali@gmail.com",      "0559876543",   "pass456",      "Passenger"},
            {"John Doe",   "john@example.com",   "555-123-4567", "password123",  "Passenger"},
            {"Jane Smith", "jane@example.com",   "555-987-6543", "password123",  "Passenger"},
            {"Admin User", "admin@example.com",  "555-111-2222", "admin123",     "Admin"}
        };

        for (String[] u : initial) {
            userListArea.append(
                String.format(
                    "Name: %s, Email: %s, Phone: %s, Password: %s, Role: %s%n",
                    u[0], u[1], u[2], u[3], u[4]
                )
            );
        }
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name  = nameField.getText();
            String email = emailField.getText();
            String role  = (String) roleComboBox.getSelectedItem();

            if (validateInput(name, email, role)) {
                userListArea.append(
                    String.format(
                        "Added User: %s (%s) - Role: %s%n",
                        name, email, role
                    )
                );
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                    UserManagementFrame.this,
                    "Please fill in all fields correctly.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class RemoveUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            if (!email.isEmpty()) {
                userListArea.append("Removed User with Email: " + email + "\n");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                    UserManagementFrame.this,
                    "Please enter an email to remove a user.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class UpdateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name  = nameField.getText();
            String email = emailField.getText();
            String role  = (String) roleComboBox.getSelectedItem();

            if (validateInput(name, email, role)) {
                userListArea.append(
                    String.format(
                        "Updated User: %s (%s) - New Role: %s%n",
                        name, email, role
                    )
                );
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                    UserManagementFrame.this,
                    "Please fill in all fields correctly.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private boolean validateInput(String name, String email, String role) {
        return !name.trim().isEmpty()
            && !email.trim().isEmpty()
            && !role.trim().isEmpty();
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        roleComboBox.setSelectedIndex(0);
    }
}
