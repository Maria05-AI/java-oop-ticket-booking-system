/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oopproject.model.*;
import oopproject.dao.*;



public class PassengerLoginFrame extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    
    public PassengerLoginFrame() {
        setTitle("Passenger Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        emailField = new JTextField(15);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));
        
        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));
        
        JButton loginButton = new JButton("Login");
        JButton forgotPasswordButton = new JButton("Forgot password?");
        JButton registerButton = new JButton("Register");
        
        // alignment for register button
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // button panel for login and forgot password
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(forgotPasswordButton);
        
        // components to main panel
        mainPanel.add(new JLabel("Email:"));
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(registerButton);
        
        // action listeners
        loginButton.addActionListener(new LoginButtonListener());
        forgotPasswordButton.addActionListener(e -> new ForgotPasswordFrame());
        registerButton.addActionListener(e -> new RegisterFrame());
        
        // main panel to frame
        add(mainPanel);
        setVisible(true);
    }
    
    // validation method
    private boolean validateLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
    
    // Inner class for login button
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            
            if (!validateLogin(email, password)) {
                JOptionPane.showMessageDialog(PassengerLoginFrame.this, 
                    "Please enter both email and password.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            UserDAO userDAO = new UserDAO();
            User user = userDAO.authenticateUser(email, password);
            
            if (user != null) {
                // Check if the user is a passenger
                if (user.getRole().equalsIgnoreCase("Passenger")) {
                    JOptionPane.showMessageDialog(PassengerLoginFrame.this, 
                        "Welcome, " + user.getFirstName() + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Create passenger dashboard with user info
                    new PassengerDashboardFrame(user);
                    dispose(); // Close login window
                } else {
                    JOptionPane.showMessageDialog(PassengerLoginFrame.this, 
                        "This account does not have passenger access.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(PassengerLoginFrame.this, 
                    "Invalid email or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
}
