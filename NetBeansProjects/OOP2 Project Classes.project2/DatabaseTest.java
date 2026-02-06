/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseTest {
    
    
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnector().fetchConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                
                // Test a simple query
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
                rs.next();
                int userCount = rs.getInt(1);
                System.out.println("Number of users in database: " + userCount);
                
            } else {
                System.out.println("Failed to connect to database!");
            }
        } catch (SQLException e) {
            System.err.println("Error testing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
}
