/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import oopproject.main;
import javax.swing.SwingUtilities;


public class TravelSystemController {
    
    private static final String URL = "jdbc:mysql://localhost:3306/travel_booking_db";
    private static final String USER = "root";
    private static final String PASS = "fa.8372203";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            createTables(conn);
            insertSampleData(conn);
            conn.close();
            System.out.println("✅ System initialized successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> new main());
    }

    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String usersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "user_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "first_name VARCHAR(100),"
                + "last_name VARCHAR(100),"
                + "email VARCHAR(100) UNIQUE,"
                + "password VARCHAR(100),"
                + "phone VARCHAR(20),"
                + "role VARCHAR(20));";

        String ticketsTable = "CREATE TABLE IF NOT EXISTS tickets ("
                + "ticket_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "booking_date DATE,"
                + "seat_number INT,"
                + "price FLOAT,"
                + "ticket_class VARCHAR(20),"
                + "baggage BOOLEAN DEFAULT FALSE,"
                + "lounge_access BOOLEAN DEFAULT FALSE,"
                + "free_snacks BOOLEAN DEFAULT FALSE)";

        String transactionsTable = "CREATE TABLE IF NOT EXISTS transactions ("
                + "transaction_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "user_id INT,"
                + "ticket_id INT,"
                + "transaction_date DATE,"
                + "type VARCHAR(20),"
                + "FOREIGN KEY (user_id) REFERENCES users(user_id),"
                + "FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id))";

        String bookingsTable = "CREATE TABLE IF NOT EXISTS bookings ("
                + "booking_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "transaction_id INT,"
                + "booking_status VARCHAR(20),"
                + "payment_method VARCHAR(50),"
                + "fare FLOAT,"
                + "booking_reference VARCHAR(100),"
                + "FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id))";

        String cancellationsTable = "CREATE TABLE IF NOT EXISTS cancellations ("
                + "cancellation_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "transaction_id INT,"
                + "reason TEXT,"
                + "refund_amount FLOAT,"
                + "is_refunded BOOLEAN,"
                + "FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id))";

        String trainsTable = "CREATE TABLE IF NOT EXISTS express_trains ("
                + "train_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "train_name VARCHAR(100),"
                + "train_type VARCHAR(50),"
                + "capacity INT,"
                + "occupancy INT,"
                + "max_speed FLOAT)";

        String reviewsTable = "CREATE TABLE IF NOT EXISTS reviews ("
                + "review_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "user_id INT,"
                + "rating INT,"
                + "comment TEXT,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (user_id) REFERENCES users(user_id))";

        stmt.execute(usersTable);
        stmt.execute(ticketsTable);
        stmt.execute(transactionsTable);
        stmt.execute(bookingsTable);
        stmt.execute(cancellationsTable);
        stmt.execute(trainsTable);
        stmt.execute(reviewsTable);

        System.out.println("All tables created or verified.");
    }

    private static void insertSampleData(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        String sampleUser = "INSERT IGNORE INTO users (first_name, last_name, email, password, phone, role) VALUES"
                + "('Fatimah', 'Essa', 'fatimah@gmail.com', 'pass123', '0551234567', 'Passenger'),"
                + "('Ali', 'Admin', 'admin@test.com', 'admin123', '0559876543', 'Admin')";

        String sampleTrain = "INSERT IGNORE INTO express_trains (train_name, train_type, capacity, occupancy, max_speed) VALUES"
                + "('Express One', 'Luxury', 100, 0, 300.0),"
                + "('Metro Fast', 'Standard', 120, 0, 250.0)";

        stmt.executeUpdate(sampleUser);
        stmt.executeUpdate(sampleTrain);

        
    }
}
    

