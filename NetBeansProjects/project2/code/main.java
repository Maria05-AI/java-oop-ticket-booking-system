/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;


public class main extends JFrame{
    
    public main(){
        
        
        try {
            DatabaseInitializer.initializeDatabase();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error initializing database: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        
        setTitle("Traveling Booking System");
        setSize(1300,700);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
     
        
        JLabel imageLabel = new JLabel(new ImageIcon("src/images res/logo.jpg")); 
        add(imageLabel, BorderLayout.WEST);
        
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));
        
        
        JButton passengerButton = new JButton("Log in as Passenger");
        JButton adminButton = new JButton("Log in as Admin");
        
        
        passengerButton.setMaximumSize(new Dimension(200, 40));
        adminButton.setMaximumSize(new Dimension(200, 40));
        passengerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        passengerButton.addActionListener(new PassengerButtonListener());
        adminButton.addActionListener(new AdminButtonListener());
        

        JLabel welcomeMessage = new JLabel("WELCOME TO OUR TRAVELING BOOKING SYSTEM!", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.setBackground(new Color(240, 248, 255));
        
        centerPanel.add(welcomeMessage);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(passengerButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(adminButton);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
        
}

}

