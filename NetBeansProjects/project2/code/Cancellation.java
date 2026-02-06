/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;

import oopproject.model.Transaction;


public class Cancellation extends Transaction {
    
     private String cancellationReason;
    private float refundAmount;
    private boolean isRefunded;
    
    // Constructor
    public Cancellation(int transactionID, int userID, int ticketID, java.util.Date transactionDate, 
            String type, String cancellationReason, float refundAmount, boolean isRefunded) {
        super(transactionID, userID, ticketID, transactionDate, type);
        this.cancellationReason = cancellationReason;
        this.refundAmount = refundAmount;
        this.isRefunded = isRefunded;
    }
    
    // Default constructor
    public Cancellation() {
        super();
        this.setType("Cancellation");
        this.isRefunded = false;
    }
    
    // Process refund method
    public void processRefund() {
        this.isRefunded = true;
        System.out.println("Refund processed: " + this.refundAmount);
    }
    
    // Getters and Setters
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
    
    public float getRefundAmount() {
        return refundAmount;
    }
    
    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public boolean isIsRefunded() {
        return isRefunded;
    }
    
    public void setIsRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }
}
    

