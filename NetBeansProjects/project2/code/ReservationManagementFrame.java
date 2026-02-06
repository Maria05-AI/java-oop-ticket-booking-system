/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ReservationManagementFrame extends JFrame {
    
    private JList<String> reservationList;
    private DefaultListModel<String> reservationListModel;

    public ReservationManagementFrame() {
        setTitle("Reservation Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        

        // Reservations list
        reservationListModel = new DefaultListModel<>();
        reservationList = new JList<>(reservationListModel);
        reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(reservationList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Reservations"));
        panel.add(scrollPane);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton cancelButton = new JButton("Cancel Reservation");
        cancelButton.addActionListener(new CancelReservationListener());
        JButton updateButton = new JButton("Update Reservation");
        updateButton.addActionListener(new UpdateReservationListener());
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);

        loadReservations();
    }

    private void loadReservations() {
        reservationListModel.addElement("Booking ID: 1001 | Passenger: Dana   | Date: 2023-10-01 | Type: Bus");
        reservationListModel.addElement("Booking ID: 1002 | Passenger: Fatimah| Date: 2023-10-02 | Type: Train");
        reservationListModel.addElement("Booking ID: 1003 | Passenger: Ali    | Date: 2023-10-03 | Type: Bus");
    }

    private class CancelReservationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String sel = reservationList.getSelectedValue();
            if (sel != null) {
                reservationListModel.removeElement(sel);
                JOptionPane.showMessageDialog(
                    ReservationManagementFrame.this,
                    "Cancelled Reservation:\n" + sel,
                    "Cancellation",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    ReservationManagementFrame.this,
                    "Please select a reservation to cancel.",
                    "Cancellation Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class UpdateReservationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String sel = reservationList.getSelectedValue();
            if (sel != null) {
                JOptionPane.showMessageDialog(
                    ReservationManagementFrame.this,
                    "Updating Reservation:\n" + sel,
                    "Update",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    ReservationManagementFrame.this,
                    "Please select a reservation to update.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
    

