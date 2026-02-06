/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    
   private static  DBConnection singleInstance;
    private Connection dbLink;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/travel_booking_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "fa.8372203";

   
    private  DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connection to MySQL established.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    
    public static synchronized  DBConnection getConnector() {
        if (singleInstance == null) {
            singleInstance = new  DBConnection();
        }
        return singleInstance;
    }

    
    public Connection fetchConnection() {
        try {
            if (dbLink == null || dbLink.isClosed()) {
                dbLink = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                System.out.println("Re-established database connection.");
            }
        } catch (SQLException e) {
            System.err.println("Error while validating connection: " + e.getMessage());
        }
        return dbLink;
    }

    
    public void terminateConnection() {
        try {
            if (dbLink != null && !dbLink.isClosed()) {
                dbLink.close();
                System.out.println("MySQL connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Could not close the connection: " + e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        DBConnection connector =  DBConnection.getConnector();
        Connection testConn = connector.fetchConnection();

        if (testConn != null) {
            System.out.println("MySQL is working correctly.");
            connector.terminateConnection();
        } else {
            System.out.println("Connection test failed. Check configuration.");
        }
    }
    
}


