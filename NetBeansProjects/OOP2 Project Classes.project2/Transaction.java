/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;
import java.util.Date;


public class Transaction {
    
    private int transactionID;
    private int userID;
    private int ticketID;
    private Date transactionDate;
    private String type;
    
    // Constructor
    public Transaction(int transactionID, int userID, int ticketID, Date transactionDate, String type) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.ticketID = ticketID;
        this.transactionDate = transactionDate;
        this.type = type;
    }
    
    // Default constructor
    public Transaction() {
        this.transactionDate = new Date();
    }
    
    // Process transaction method
    public void processTransaction() {
        System.out.println("Processing transaction: " + transactionID);
    }
    
    // View transaction details
    public void viewTransactionDetails() {
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("User ID: " + userID);
        System.out.println("Ticket ID: " + ticketID);
        System.out.println("Date: " + transactionDate);
        System.out.println("Type: " + type);
    }
    
    // Getters and Setters
    public int getTransactionID() {
        return transactionID;
    }
    
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public int getTicketID() {
        return ticketID;
    }
    
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
    
    public Date getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
    
