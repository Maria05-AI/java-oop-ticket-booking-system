/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import oopproject.model.*;
import oopproject.dao.*;



public class UserProfileFrame extends JFrame {
    
     private User currentUser;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    public UserProfileFrame(User user) {
        this.currentUser = user;

        setTitle("User Profile");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 255));

        // User info fields
        nameField = createLabeledField("Name:", mainPanel);
        emailField = createLabeledField("Email:", mainPanel);
        phoneField = createLabeledField("Phone Number:", mainPanel);

        // Password field for changing password
        passwordField = new JPasswordField();
        addLabeledComponent("New Password:", passwordField, mainPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(245, 248, 255));

        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new SaveChangesListener());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        add(mainPanel);

        
        loadUserProfile();

        setVisible(true);
    }

    // method to create labeled fields
    private JTextField createLabeledField(String labelText, JPanel container) {
        JTextField textField = new JTextField();
        addLabeledComponent(labelText, textField, container);
        return textField;
    }

    // method to add labeled components
    private void addLabeledComponent(String labelText, JComponent field, JPanel container) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));

        container.add(label);
        container.add(Box.createVerticalStrut(5));
        container.add(field);
        container.add(Box.createVerticalStrut(15));
    }

    // Load user profile data
    private void loadUserProfile() {
        nameField.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        emailField.setText(currentUser.getEmail());
        phoneField.setText(currentUser.getPhone());
    }

    // Inner class for save button
    private class SaveChangesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(UserProfileFrame.this,
                    "Please fill in all required fields.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            String[] parts = name.split(" ", 2);
            currentUser.setFirstName(parts[0]);
            currentUser.setLastName(parts.length > 1 ? parts[1] : "");
            currentUser.setEmail(email);
            currentUser.setPhone(phone);

            
            if (!newPassword.isEmpty()) {
                currentUser.setPassword(newPassword);
            }

           
            JOptionPane.showMessageDialog(UserProfileFrame.this,
                "Profile updated successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}







