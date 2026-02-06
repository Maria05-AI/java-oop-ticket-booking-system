/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.util;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import oopproject.util.DataExporter;



public class DataManagementFrame extends JFrame {
    
     private JComboBox<String> tableSelector;
    private JButton exportButton;
    private JTextArea statusArea;

    private DataExporter exporter;

    public DataManagementFrame() {
        setTitle("Data Export");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        exporter = new DataExporter();
        initComponents();
        loadTables();

        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tableLabel = new JLabel("Select Table:");
        tableSelector = new JComboBox<>();
        tableSelector.setPreferredSize(new Dimension(250, 25));
        selectionPanel.add(tableLabel);
        selectionPanel.add(tableSelector);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        exportButton = new JButton("Export Selected Table");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) tableSelector.getSelectedItem();
                if (selectedTable != null && !selectedTable.isEmpty()) {
                    boolean success = exporter.exportTableToCSV(selectedTable);
                    if (success) {
                        appendStatus("Table '" + selectedTable + "' exported successfully.");
                    } else {
                        appendStatus("Export of table '" + selectedTable + "' canceled or failed.");
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        DataManagementFrame.this,
                        "Please select a table to export."
                    );
                }
            }
        });
        buttonPanel.add(exportButton);

        
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Status"));

        mainPanel.add(selectionPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadTables() {
        List<String> tables = exporter.getAllTables();
        tableSelector.removeAllItems();
        for (String table : tables) {
            tableSelector.addItem(table);
        }
        if (!tables.isEmpty()) {
            appendStatus("Loaded " + tables.size() + " tables from database.");
        } else {
            appendStatus("No tables found in database.");
        }
    }

    private void appendStatus(String message) {
        statusArea.append(message + "\n");
        statusArea.setCaretPosition(statusArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataManagementFrame();
            }
        });
    }
}
