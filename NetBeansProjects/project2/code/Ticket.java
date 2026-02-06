/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;
import java.util.Date;


public class Ticket {
    
    private int ticketID;
    private Date bookingDate;
    private int seatNumber;
    private float price;
    private String ticketClass;
    private String status;
    
    // Constructors
    public Ticket() {
        this.bookingDate = new Date();
        this.status = "Available";
    }
    
     public Ticket(int ticketID, Date bookingDate, int seatNumber, float price, String ticketClass, String status) {
        this.ticketID = ticketID;
        this.bookingDate = bookingDate;
        this.seatNumber = seatNumber;
        this.price = price;
        this.ticketClass = ticketClass;
        this.status = "Available";
    }
    
    // Getters and Setters
    public int getTicketID() { return ticketID; }
    public void setTicketID(int ticketID) { this.ticketID = ticketID; }
    
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    
    public String getTicketClass() { return ticketClass; }
    public void setTicketClass(String ticketClass) { this.ticketClass = ticketClass; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Calculate price method 
    public float calculatePrice() {
        return price;
    }
    
    
}
    
