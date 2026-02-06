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


public class PassengerDashboardFrame extends JFrame {
    
    private User currentUser;
    
    public PassengerDashboardFrame(User user) {
        this.currentUser = user;
        
        // dashboard
        setTitle("Passenger Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // panel with GridBagLayout for more control
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints welcomeConstraints = new GridBagConstraints();
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 0;
        welcomeConstraints.gridwidth = 2;
        welcomeConstraints.anchor = GridBagConstraints.WEST;
        welcomeConstraints.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(welcomeLabel, welcomeConstraints);
        
        // buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        
        // styled buttons
        JButton bookingButton = createDashboardButton("Ticket Booking", "Book your tickets");
        JButton historyButton = createDashboardButton("Booking History", "View your past bookings");
        JButton reviewsButton = createDashboardButton("Reviews", "Rate your experiences");
        JButton profileButton = createDashboardButton("User Profile", "Manage your account");
        JButton paymentButton = createDashboardButton("Payment", "Manage payments");
        JButton logoutButton = createDashboardButton("Logout", "Exit your account");
        
        // action listeners
        bookingButton.addActionListener(e -> openTicketBooking());
        historyButton.addActionListener(e -> openBookingHistory());
        reviewsButton.addActionListener(e -> openReviews());
        profileButton.addActionListener(e -> openUserProfile());
        paymentButton.addActionListener(e -> openPayment());
        logoutButton.addActionListener(e -> logout());
        
        // buttons to panel
        buttonsPanel.add(bookingButton);
        buttonsPanel.add(historyButton);
        buttonsPanel.add(reviewsButton);
        buttonsPanel.add(profileButton);
        buttonsPanel.add(paymentButton);
        buttonsPanel.add(logoutButton);
        
        // buttons panel to main panel
        GridBagConstraints buttonsConstraints = new GridBagConstraints();
        buttonsConstraints.gridx = 0;
        buttonsConstraints.gridy = 1;
        buttonsConstraints.gridwidth = 2;
        buttonsConstraints.fill = GridBagConstraints.BOTH;
        buttonsConstraints.weightx = 1.0;
        buttonsConstraints.weighty = 1.0;
        mainPanel.add(buttonsPanel, buttonsConstraints);
        
        // main panel to frame
        add(mainPanel);
        setVisible(true);
    }
    
    // Helper method to create styled dashboard buttons
    private JButton createDashboardButton(String title, String description) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setHorizontalAlignment(JLabel.CENTER);
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        button.add(titleLabel, BorderLayout.NORTH);
        button.add(descLabel, BorderLayout.CENTER);
        
        button.setBackground(new Color(240, 240, 245));
        button.setFocusPainted(false);
        
        return button;
    }
    
    // Action methods for buttons
    private void openTicketBooking() {
        new TicketBookingFrame(currentUser);
    }
    
    private void openBookingHistory() {
        new BookingHistoryFrame(currentUser);
    }
    
    private void openReviews() {
        new ReviewsFrame(currentUser);
    }
    
    private void openUserProfile() {
        new UserProfileFrame(currentUser);
    }
    
    private void openPayment() {
        new PaymentFrame(currentUser, 200.0);
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Close the dashboard
            new main(); // Return to main screen
            JOptionPane.showMessageDialog(null, 
                "You have been logged out.", 
                "Logout Successful", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    

}
