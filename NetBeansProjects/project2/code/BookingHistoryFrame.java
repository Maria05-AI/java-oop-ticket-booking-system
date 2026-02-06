/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import oopproject.model.*;
import oopproject.dao.*;
import java.util.Date;


public class BookingHistoryFrame extends JFrame {
    
    private User currentUser;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    
    public BookingHistoryFrame(User user) {
        this.currentUser = user;
        
        setTitle("Booking History");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lbl = new JLabel("Your Booking History");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(lbl, BorderLayout.WEST);
        
        
        
        add(header, BorderLayout.NORTH);

        // Table
        String[] cols = {
            "Booking ID",
            "Travel Type",
            "Departure Station",
            "Arrival Station",
            "Date",
            "Time",
            "Quantity",
            "Ticket Type",
            "Total Amount"
        };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        bookingsTable = new JTable(tableModel);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.getTableHeader().setReorderingAllowed(false);
        bookingsTable.setRowHeight(25);
        add(new JScrollPane(bookingsTable), BorderLayout.CENTER);

        // Single button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            dispose(); // Close current frame
            new PassengerDashboardFrame(currentUser); // Open passenger dashboard
        });
        
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        
        loadDemoRow();

        setVisible(true);
    }
    
    
    private void loadDemoRow() {
        tableModel.setRowCount(0);
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dfTime = new SimpleDateFormat("hh:mma");

        Object[] demo = {
            "BK1001",                   // Booking ID
            "Train",                    // Travel Type
            "Station A",                // Departure
            "Station E",                // Arrival
            "2025-05-10",               // Date
            "10:00AM",                  // Time
            2,                          // Quantity
            "Standard",                 // Ticket Type
            "$200.00"                   // Total Amount
        };
        tableModel.addRow(demo);
    }
    
    
        
    }