/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import oopproject.model.User;
import oopproject.dao.*;


public class PaymentFrame extends JFrame{
    
    private User currentUser;

    public PaymentFrame(User currentUser, double amount) {
        this.currentUser = currentUser;
        setTitle("Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 12, 8, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

        try {
            // 1. National ID
            c.gridx = 0; c.gridy = 0;
            panel.add(new JLabel("National ID:"), c);
            c.gridx = 1;
            JTextField nationalIdField = new JTextField(15);
            panel.add(nationalIdField, c);

            // 2. Payment Method
            c.gridx = 0; c.gridy = 1;
            panel.add(new JLabel("Payment Method:"), c);
            JPanel methodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            JRadioButton cashButton = new JRadioButton("Cash");
            JRadioButton creditButton = new JRadioButton("Credit", true);
            ButtonGroup methodGroup = new ButtonGroup();
            methodGroup.add(cashButton);
            methodGroup.add(creditButton);
            methodPanel.add(cashButton);
            methodPanel.add(Box.createHorizontalStrut(10));
            methodPanel.add(creditButton);
            c.gridx = 1;
            panel.add(methodPanel, c);

            // 3. Amount 
            c.gridx = 0; c.gridy = 2;
            panel.add(new JLabel("Payment Amount:"), c);
            c.gridx = 1;
            JTextField amountField = new JTextField(String.format("%.2f", amount), 15);
            amountField.setEditable(false);
            panel.add(amountField, c);

            // 4. Card Number (#### #### #### ####)
            c.gridx = 0; c.gridy = 3;
            panel.add(new JLabel("Card Number:"), c);
            c.gridx = 1;
            MaskFormatter cardMask = new MaskFormatter("#### #### #### ####");
            JFormattedTextField cardField = new JFormattedTextField(cardMask);
            panel.add(cardField, c);  // :contentReference[oaicite:1]{index=1}

            // 5. Expiry Date (MM/YY)
            c.gridx = 0; c.gridy = 4;
            panel.add(new JLabel("Expiry (MM/YY):"), c);
            c.gridx = 1;
            MaskFormatter expiryMask = new MaskFormatter("##/##");
            JFormattedTextField expiryField = new JFormattedTextField(expiryMask);
            panel.add(expiryField, c);

            // 6. CVV
            c.gridx = 0; c.gridy = 5;
            panel.add(new JLabel("CVV:"), c);
            c.gridx = 1;
            MaskFormatter cvvMask = new MaskFormatter("###");
            JFormattedTextField cvvField = new JFormattedTextField(cvvMask);
            panel.add(cvvField, c);

            // 7. Name
            c.gridx = 0; c.gridy = 6;
            panel.add(new JLabel("Name:"), c);
            c.gridx = 1;
            JTextField nameField = new JTextField(15);
            panel.add(nameField, c);

            // 8. Phone Number
            c.gridx = 0; c.gridy = 7;
            panel.add(new JLabel("Phone Number:"), c);
            c.gridx = 1;
            JTextField phoneField = new JTextField(15);
            panel.add(phoneField, c);

            // 9. Pay & Back Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            JButton payButton = new JButton("Pay");
            JButton backButton = new JButton("Back to Home");
            buttonPanel.add(payButton);
            buttonPanel.add(backButton);
            c.gridx = 0; c.gridy = 8;
            c.gridwidth = 2;
            panel.add(buttonPanel, c);
            
            payButton.addActionListener(e -> {
              JOptionPane.showMessageDialog(
              this,
               "Your payment has been confirmed!",
               "Payment Successful",
               JOptionPane.INFORMATION_MESSAGE
             );
              new PassengerDashboardFrame(currentUser);
              dispose();
             });

           // Back to Home
            backButton.addActionListener(e -> {
            new PassengerDashboardFrame(currentUser);
            dispose();
            });
            

        } catch (ParseException ex) {
            ex.printStackTrace();
        }


        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panel);
        pack();
        setVisible(true);
    }
    
}
