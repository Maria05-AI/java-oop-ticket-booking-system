/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import java.sql.*;


public class DatabaseInitializer {
    
    
    public static void initializeDatabase() {
        Connection conn = DBConnection.getConnector().fetchConnection();
        try {
            
            createTables(conn);
            
            
            if (isTableEmpty(conn, "users")) {
                insertSampleData(conn);
            }
            
            System.out.println("Database initialization completed successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        
        
        stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "name VARCHAR(100), " +
                 "email VARCHAR(100) UNIQUE, " +
                 "password VARCHAR(100), " +
                 "phone VARCHAR(20), " +
                 "role VARCHAR(20) DEFAULT 'Passenger'" +
                 ")");
    
    
    stmt.execute("CREATE TABLE IF NOT EXISTS tickets (" +
                 "ticket_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "passenger_id INT, " +
                 "type VARCHAR(20), " +
                 "price DECIMAL(8,2), " +
                 "seat_number INT" +
                 ")");
    
    
    stmt.execute("CREATE TABLE IF NOT EXISTS schedules (" +
                 "schedule_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "transportation_type VARCHAR(20), " +
                 "departure_time TIME, " +
                 "arrival_time TIME, " +
                 "departure_point VARCHAR(100), " +
                 "destination VARCHAR(100), " +
                 "available_seats INT" +
                 ")");
    
    
    stmt.execute("CREATE TABLE IF NOT EXISTS reviews (" +
                 "review_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "rating INT, " +
                 "comment TEXT, " +
                 "user_id INT" +
                 ")");
    
    
    stmt.execute("CREATE TABLE IF NOT EXISTS bookings (" +
                 "booking_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "passenger_id INT, " +
                 "ticket_id INT, " +
                 "status VARCHAR(20), " +
                 "booking_date DATE" +
                 ")");
    
    
    stmt.execute("CREATE TABLE IF NOT EXISTS reservations (" +
                 "reservation_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "schedule_id INT, " +
                 "passenger_id INT, " +
                 "status VARCHAR(20)" +
                 ")");
    
   
    stmt.execute("CREATE TABLE IF NOT EXISTS admin_actions (" +
                 "action_id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "admin_id INT, " +
                 "action_description TEXT, " +
                 "action_date TIMESTAMP" +
                 ")");
    
        
        System.out.println("Database tables created successfully.");
    }
    
    private static boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
        rs.next();
        return rs.getInt(1) == 0;
    }
    
    
    private static void insertSampleData(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        
        
        stmt.executeUpdate("INSERT INTO users (name, email, password, phone, role) VALUES " +
                       "('Admin1', 'admin1@example.com', 'adminpass1', '0501234567', 'Admin'), " +
                       "('Admin2', 'admin2@example.com', 'adminpass2', '0509876543', 'Admin'), " +
                       "('Fatimah', 'fatimah@gmail.com', 'pass123', '0554567890', 'Passenger'), " +
                       "('Ali', 'ali@gmail.com', 'pass456', '0559876543', 'Passenger')");
    
    
    stmt.executeUpdate("INSERT INTO tickets (passenger_id, type, price, seat_number) VALUES " +
                       "(3, 'VIP', 150.00, 1), " +
                       "(3, 'Standard', 80.00, 2), " +
                       "(4, 'VIP', 140.00, 5), " +
                       "(4, 'Standard', 75.00, 8)");
    
    
    stmt.executeUpdate("INSERT INTO schedules (transportation_type, departure_time, arrival_time) VALUES " +
                       "('Train', '09:00:00', '11:00:00'), " +
                       "('Bus', '13:00:00', '15:30:00'), " +
                       "('Train', '17:00:00', '19:30:00'), " +
                       "('Bus', '08:00:00', '10:00:00')");
    
    
        System.out.println("Sample data; inserted successfully.");
    }
        
    
    
    
}
