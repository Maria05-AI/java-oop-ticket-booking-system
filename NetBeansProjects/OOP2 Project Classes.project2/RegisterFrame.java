/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oopproject.dao.UserDAO;


public class RegisterFrame extends JFrame {
    
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JComboBox<String> roleComboBox;
    
    public RegisterFrame() {
        setTitle("Register");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));
        
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));
        
        phoneField = new JTextField();
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, phoneField.getPreferredSize().height));
        
        // Role selection
        String[] roles = {"Passenger", "Admin"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, roleComboBox.getPreferredSize().height));
        
        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        
        // components to panel
        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));
        
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));
        
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));
        
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(Box.createVerticalStrut(10));
        
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);
        panel.add(Box.createVerticalStrut(20));
        
        panel.add(registerButton);
        
        // panel to frame
        add(panel);
        setVisible(true);
    }
    
    // Validate input fields
    private boolean validateInput(String name, String email, String password, String phone) {
        return !name.trim().isEmpty() && 
               !email.trim().isEmpty() && 
               !password.trim().isEmpty() &&
               !phone.trim().isEmpty();
    }
    
    // Parse name into first name and last name
    private String[] parseName(String fullName) {
        String[] parts = fullName.split(" ", 2);
        if (parts.length == 1) {
            return new String[] {parts[0], ""}; 
        }
        return parts; 
    }
    
    // Inner class for register button
    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();
            String role = (String) roleComboBox.getSelectedItem();
            
            if (!validateInput(fullName, email, password, phone)) {
                JOptionPane.showMessageDialog(RegisterFrame.this, 
                    "Please fill in all fields correctly.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse name into first and last name
            String[] nameParts = parseName(fullName);
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            
            // Check if email already exists
            UserDAO userDAO = new UserDAO();
            if (userDAO.emailExists(email)) {
                JOptionPane.showMessageDialog(RegisterFrame.this, 
                    "This email is already registered.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Register the user
            boolean success = userDAO.registerUser(firstName, lastName, email, password, phone, role);
            
            if (success) {
                JOptionPane.showMessageDialog(RegisterFrame.this, 
                    "Registration successful for " + fullName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close registration window
                
                // Open login frame based on role
                if (role.equals("Admin")) {
                    new AdminLoginFrame();
                } else {
                    new PassengerLoginFrame();
                }
            } else {
                JOptionPane.showMessageDialog(RegisterFrame.this, 
                    "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
