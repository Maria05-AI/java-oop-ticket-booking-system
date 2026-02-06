/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import oopproject.dao.UserDAO;


public class ForgotPasswordFrame extends JFrame {
    
    private JTextField emailField;
    private JPanel mainPanel;
    private JPanel resetPanel;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private String verificationCode;
    private JTextField codeField;
    
    public ForgotPasswordFrame() {
        setTitle("Forgot Password");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // email entry panel
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));
        
        JButton sendCodeButton = new JButton("Send Verification Code");
        sendCodeButton.addActionListener(e -> sendVerificationCode());
        
        emailPanel.add(new JLabel("Enter your email:"));
        emailPanel.add(emailField);
        emailPanel.add(Box.createVerticalStrut(10));
        emailPanel.add(sendCodeButton);
        
        // verification and reset panel
        resetPanel = new JPanel();
        resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.Y_AXIS));
        resetPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        codeField = new JTextField();
        codeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, codeField.getPreferredSize().height));
        
        newPasswordField = new JPasswordField();
        newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newPasswordField.getPreferredSize().height));
        
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, confirmPasswordField.getPreferredSize().height));
        
        JButton resetButton = new JButton("Reset Password");
        resetButton.addActionListener(e -> resetPassword());
        
        resetPanel.add(new JLabel("Enter Verification Code:"));
        resetPanel.add(codeField);
        resetPanel.add(Box.createVerticalStrut(10));
        resetPanel.add(new JLabel("New Password:"));
        resetPanel.add(newPasswordField);
        resetPanel.add(Box.createVerticalStrut(10));
        resetPanel.add(new JLabel("Confirm Password:"));
        resetPanel.add(confirmPasswordField);
        resetPanel.add(Box.createVerticalStrut(20));
        resetPanel.add(resetButton);
        
        
        mainPanel.add(emailPanel, "email");
        mainPanel.add(resetPanel, "reset");
        
        // Show email panel first
        cardLayout.show(mainPanel, "email");
        
        
        add(mainPanel);
        setVisible(true);
    }
    
    // Method to send verification code
    private void sendVerificationCode() {
        String email = emailField.getText().trim();
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter your email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if email exists in the database
        UserDAO userDAO = new UserDAO();
        if (!userDAO.emailExists(email)) {
            JOptionPane.showMessageDialog(this, 
                "Email not found in our records.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Generate verification code
        verificationCode = generateRandomCode();
       

       
       JOptionPane.showMessageDialog(
        this,
        "Password reset link sent to " + email,   
        "Message",                                 
        JOptionPane.INFORMATION_MESSAGE           
);
        
        // Switch to reset panel
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "reset");
    }
    
    // Method to reset password
    private void resetPassword() {
        String code = codeField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validate inputs
        if (code.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check verification code
        if (!code.equals(verificationCode)) {
            JOptionPane.showMessageDialog(this, 
                "Invalid verification code.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update password in database
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updatePassword(emailField.getText().trim(), newPassword);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Password reset successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the frame
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to reset password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Generate random verification code
    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6-digit code
        return String.valueOf(code);
    }
}
