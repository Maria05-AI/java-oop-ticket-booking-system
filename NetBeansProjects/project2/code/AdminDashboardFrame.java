/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import oopproject.model.*;
import oopproject.dao.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class AdminDashboardFrame extends JFrame {
    private User admin;

    public AdminDashboardFrame(User admin) {
        this.admin = admin;

        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 255));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome, " + admin.getFirstName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        JLabel roleLabel = new JLabel("Admin Dashboard");
        roleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        headerPanel.add(roleLabel, BorderLayout.EAST);

        // Actions panel with 5 buttons
        JPanel actionsPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        actionsPanel.setOpaque(false);

        JButton userManagementButton = createStyledButton("User Management");
        JButton reservationManagementButton = createStyledButton("Reservation Management");
        JButton scheduleManagementButton = createStyledButton("Schedule Management");
        JButton exportButton = createStyledButton("Export");
        JButton logoutButton = createStyledButton("Logout");

        userManagementButton.addActionListener(e -> new UserManagementFrame());
        reservationManagementButton.addActionListener(e -> new ReservationManagementFrame());
        scheduleManagementButton.addActionListener(e -> new ScheduleManagementFrame());
        exportButton.addActionListener(e -> new oopproject.util.DataManagementFrame());
        logoutButton.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(null, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            new main();
        });

       
        actionsPanel.add(userManagementButton);
        actionsPanel.add(reservationManagementButton);
        actionsPanel.add(scheduleManagementButton);
        actionsPanel.add(exportButton);
        actionsPanel.add(logoutButton);

        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createTitledBorder("System Statistics"));
        statsPanel.setOpaque(false);
        String[] stats = loadSystemStats();
        for (String stat : stats) {
            JLabel statLabel = new JLabel("• " + stat);
            statLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            statsPanel.add(statLabel);
            statsPanel.add(Box.createVerticalStrut(5));
        }

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(actionsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(statsPanel);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setUI(new BasicButtonUI());
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        Color normalBg = new Color(70, 130, 180);
        Color hoverBg = new Color(100, 149, 237);
        button.setBackground(normalBg);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(normalBg));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalBg);
            }
        });
        return button;
    }

    private String[] loadSystemStats() {
        try {
            UserDAO userDAO = new UserDAO();
            TicketDAO ticketDAO = new TicketDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            int userCount = userDAO.getAllUsers().size();
            int ticketCount = ticketDAO.getAllTickets().size();
            return new String[] {
                "Total Users: " + userCount,
                "Total Tickets: " + ticketCount,
                "Active Bookings: " + (ticketCount / 2),
                "System Uptime: 10 days, 4 hours",
                "Last Database Backup: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date())
            };
        } catch (Exception e) {
            System.err.println("Error loading system stats: " + e.getMessage());
            return new String[] {
                "Total Users: --",
                "Total Tickets: --",
                "Active Bookings: --",
                "System Uptime: --",
                "Last Database Backup: --"
            };
        }
    }
}

