/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import oopproject.model.*;
import oopproject.dao.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class TicketBookingFrame extends JFrame {
    
    private User currentUser;
    private JComboBox<String> travelTypeCombo;
    private JComboBox<String> departureCombo;
    private JComboBox<String> arrivalCombo;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField ticketCountField;
    private JComboBox<String> ticketTypeCombo;
    private JTextField totalAmountField;
    private double totalAmount = 0.0;
    
    
    public TicketBookingFrame(User user) {
        
        this.currentUser = user;
        
        
        setTitle("Ticket Booking");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 250));
        
        // Travel type selection
        travelTypeCombo = new JComboBox<>(new String[]{"Bus", "Train"});
        addLabeledComponent(mainPanel, "Travel Type:", travelTypeCombo);
        
        // Departure station selection
        departureCombo = new JComboBox<>(new String[]{"Station A", "Station B", "Station C", "Station D"});
        addLabeledComponent(mainPanel, "Departure Station:", departureCombo);
        
        // Arrival station selection
        arrivalCombo = new JComboBox<>(new String[]{"Station B", "Station C", "Station D", "Station E"});
        addLabeledComponent(mainPanel, "Arrival Station:", arrivalCombo);
        
        // Date selection
        dateField = new JTextField(10);
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); 
        addLabeledComponent(mainPanel, "Date:", dateField);
        
        // Time selection
        timeField = new JTextField(10);
        timeField.setText("12:00"); 
        addLabeledComponent(mainPanel, "Time:", timeField);
        
        // Number of tickets
        ticketCountField = new JTextField(5);
        ticketCountField.setText("1"); 
        addLabeledComponent(mainPanel, "How many tickets?", ticketCountField);
        
        // Ticket type selection
        ticketTypeCombo = new JComboBox<>(new String[]{"Standard", "VIP"});
        addLabeledComponent(mainPanel, "Ticket Type:", ticketTypeCombo);
        
        // listeners to update total amount when selections change
        ticketCountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTotalAmount();
            }
        });
        
        ticketTypeCombo.addActionListener(e -> updateTotalAmount());
        
        // Total amount field 
        totalAmountField = new JTextField(10);
        totalAmountField.setEditable(false);
        addLabeledComponent(mainPanel, "Total Amount:", totalAmountField);
        
        // Update initial total
        updateTotalAmount();
        
        // Confirm booking button
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setMaximumSize(new Dimension(200, 40));
        confirmButton.setBackground(new Color(70, 130, 180));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        
        confirmButton.addActionListener(e -> processBooking());
        
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(confirmButton);
        
        add(mainPanel);
        setVisible(true);
    }
    
    // method to add labeled components
    private void addLabeledComponent(JPanel panel, String labelText, JComponent inputField) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        container.add(label);
        container.add(Box.createVerticalStrut(5));
        container.add(inputField);
        container.add(Box.createVerticalStrut(15));
        
        panel.add(container);
    }
    
    // Method to update total amount based on selections
    private void updateTotalAmount() {
        try {
            int ticketCount = Integer.parseInt(ticketCountField.getText().trim());
            String ticketType = (String) ticketTypeCombo.getSelectedItem();
            
            // Base prices
            double pricePerTicket = ticketType.equals("VIP") ? 150.0 : 100.0;
            
            // Calculate total
            totalAmount = ticketCount * pricePerTicket;
            totalAmountField.setText("$" + String.format("%.2f", totalAmount));
            
        } catch (NumberFormatException e) {
            totalAmountField.setText("Invalid input");
        }
    }
    
    // Method to process booking
    private void processBooking() {
        try {
            // Validate inputs
            int ticketCount = Integer.parseInt(ticketCountField.getText().trim());
            if (ticketCount <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number of tickets.", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get selected values
            String travelType = (String) travelTypeCombo.getSelectedItem();
            String departureStation = (String) departureCombo.getSelectedItem();
            String arrivalStation = (String) arrivalCombo.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();
            String ticketType = (String) ticketTypeCombo.getSelectedItem();
            
            // Create tickets and bookings in database
            boolean success = createBookings(ticketCount, ticketType);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Booking successful!\n" +
                    "Travel Type: " + travelType + "\n" +
                    "From: " + departureStation + " To: " + arrivalStation + "\n" +
                    "Date: " + date + " Time: " + time + "\n" +
                    "Tickets: " + ticketCount + " (" + ticketType + ")\n" +
                    "Total Amount: $" + String.format("%.2f", totalAmount),
                    "Booking Confirmation", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Open payment frame
                new PaymentFrame(currentUser, totalAmount);
                dispose(); // Close booking frame
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to create booking. Please try again.", 
                    "Booking Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid number of tickets.", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to create bookings in database
    private boolean createBookings(int ticketCount, String ticketType) {
        try {
            // Generate seats starting from a random number
            int startSeat = (int) (Math.random() * 50) + 1;
            
            TicketDAO ticketDAO = new TicketDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            
            List<Ticket> tickets = new ArrayList<>();
            
            // Create tickets
            for (int i = 0; i < ticketCount; i++) {
                Ticket ticket;
                int seatNumber = startSeat + i;
                Date bookingDate = new Date(); 
                
                if (ticketType.equals("VIP")) {
                    ticket = new VIPTicket(0, bookingDate, seatNumber, 150.0f, ticketType, true, true);
                } else {
                    ticket = new StandardTicket(0, bookingDate, seatNumber, 100.0f, ticketType, false);
                }
                
                // Add to database
                boolean ticketAdded = ticketDAO.addTicket(ticket, currentUser.getUserID());
                if (!ticketAdded) {
                    return false;
                }
                
                tickets.add(ticket);
            }
            
            // Create transactions and bookings for each ticket
            for (Ticket ticket : tickets) {
                // Create transaction
                Transaction transaction = new Transaction(0, currentUser.getUserID(), ticket.getTicketID(), new Date(), "Booking");
                boolean transactionAdded = transactionDAO.addTransaction(transaction);
                
                if (!transactionAdded) {
                    return false;
                }
                
                // Create booking
                String bookingReference = "BK" + System.currentTimeMillis() % 10000;
                Booking booking = new Booking(
                    transaction.getTransactionID(),
                    currentUser.getUserID(),
                    ticket.getTicketID(),
                    new Date(),
                    "Booking",
                    "Confirmed",
                    "Credit Card",
                    (float) totalAmount / ticketCount,
                    bookingReference
                );
                
               
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Error creating bookings: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
