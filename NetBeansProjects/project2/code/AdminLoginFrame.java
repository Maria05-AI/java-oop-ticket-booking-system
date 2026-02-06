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
import oopproject.model.*;



public class AdminLoginFrame extends JFrame{
    
    
    private JTextField emailField;
    private JPasswordField passwordField;
    
    public AdminLoginFrame() {
        setTitle("Admin Login");
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
        
        // action listeners
        loginButton.addActionListener(new LoginButtonListener());
        forgotPasswordButton.addActionListener(e -> new ForgotPasswordFrame());
        registerButton.addActionListener(e -> new RegisterFrame());
        
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
        
        // main panel to frame
        add(mainPanel);
        setVisible(true);
    }
    
    // class for login button
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Admin adminUser = new Admin();
            adminUser.setUserID(1);
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail(emailField.getText());
            adminUser.setPassword(new String(passwordField.getPassword()));
            adminUser.setPhone("123-456-7890");
            adminUser.setRole("Admin");
            
            JOptionPane.showMessageDialog(AdminLoginFrame.this, 
                "Welcome, Admin!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            
            // Open admin dashboard 
            new AdminDashboardFrame(adminUser);
            dispose(); // Close login window
        }
    }
    
}
        
    
    
    

        
    

