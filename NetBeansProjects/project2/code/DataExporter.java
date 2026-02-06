/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.util;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import oopproject.DBConnection;
import oopproject.DBConnection;


public class DataExporter {
    
    private Connection conn;
    
    public DataExporter() {
        conn = DBConnection.getConnector().fetchConnection();
    }
    
    
    public boolean exportTableToCSV(String tableName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = tableName + "_" + timeStamp + ".csv";
        
        // Show file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        fileChooser.setSelectedFile(new File(fileName));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        
        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return false;
        }
        
        File selectedFile = fileChooser.getSelectedFile();
        
        if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
            selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
        }
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
             FileWriter fw = new FileWriter(selectedFile);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            // Get column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            
            for (int i = 1; i <= columnCount; i++) {
                bw.write(metaData.getColumnName(i));
                if (i < columnCount) {
                    bw.write(",");
                }
            }
            bw.newLine();
            
            
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    // Handle null values
                    if (value == null) {
                        value = "";
                    }
                    
                    else if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    bw.write(value);
                    if (i < columnCount) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
            
            JOptionPane.showMessageDialog(null, 
                    "Table '" + tableName + "' exported successfully to " + selectedFile.getAbsolutePath(), 
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, 
                    "Error exporting data: " + e.getMessage(), 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
    public boolean exportQueryToCSV(String sql, String fileName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fullFileName = fileName + "_" + timeStamp + ".csv";
        
        // Show file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        fileChooser.setSelectedFile(new File(fullFileName));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        
        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return false;
        }
        
        File selectedFile = fileChooser.getSelectedFile();
        
        if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
            selectedFile = new File(selectedFile.getAbsolutePath() + ".csv");
        }
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter fw = new FileWriter(selectedFile);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            
            for (int i = 1; i <= columnCount; i++) {
                bw.write(metaData.getColumnName(i));
                if (i < columnCount) {
                    bw.write(",");
                }
            }
            bw.newLine();
            
            
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    
                    if (value == null) {
                        value = "";
                    }
                    
                    else if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    bw.write(value);
                    if (i < columnCount) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
            
            JOptionPane.showMessageDialog(null, 
                    "Query results exported successfully to " + selectedFile.getAbsolutePath(), 
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, 
                    "Error exporting data: " + e.getMessage(), 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
    public List<String> getAllTables() {
        List<String> tables = new ArrayList<>();
        
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting table list: " + e.getMessage());
        }
        
        return tables;
    }
}
    
    

