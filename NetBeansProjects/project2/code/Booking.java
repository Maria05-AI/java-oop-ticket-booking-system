/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;

import oopproject.model.Transaction;


public class Booking extends Transaction{
    
    private String bookingStatus;
    private String paymentMethod;
    private float fare;
    private String bookingReference;
    
    // Constructor that takes parent class fields too
    public Booking(int transactionID, int userID, int ticketID, java.util.Date transactionDate, 
            String type, String bookingStatus, String paymentMethod, float fare, String bookingReference) {
        super(transactionID, userID, ticketID, transactionDate, type);
        this.bookingStatus = bookingStatus;
        this.paymentMethod = paymentMethod;
        this.fare = fare;
        this.bookingReference = bookingReference;
    }
    
    // Default constructor
    public Booking() {
        super();
        this.setType("Booking");
    }
    
    // Confirm booking method
    public void confirmBooking() {
        this.bookingStatus = "Confirmed";
        System.out.println("Booking confirmed: " + this.getBookingReference());
    }
    
    // Getters and Setters
    public String getBookingStatus() {
        return bookingStatus;
    }
    
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public float getFare() {
        return fare;
    }
    
    public void setFare(float fare) {
        this.fare = fare;
    }
    
    public String getBookingReference() {
        return bookingReference;
    }
    
    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }
    
    // Get status method
    public String getStatus() {
        return bookingStatus;
    }
}
    

