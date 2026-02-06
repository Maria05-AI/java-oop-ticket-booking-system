/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;
import oopproject.model.Ticket;
import java.util.Date;

/**
 *
 * @author fatim
 */
public class StandardTicket extends Ticket{
    
    private boolean hasBaggageAllowance;
    
    // Constructor
    public StandardTicket(int ticketID, Date bookingDate, int seatNumber, float price, 
            String ticketClass, boolean hasBaggageAllowance) {
        super(ticketID, bookingDate, seatNumber, price, ticketClass, "Available");
        this.hasBaggageAllowance = hasBaggageAllowance;
    }
    
    // Default constructor
    public StandardTicket() {
        super();
        this.setTicketClass("Standard");
    }
    
    // Override calculate price to include baggage allowance
    @Override
    public float calculatePrice() {
        float basePrice = getPrice();
        if (hasBaggageAllowance) {
            return basePrice + 50; // Additional fee for baggage
        }
        return basePrice;
    }
    
    // Getters and Setters
    public boolean getHasBaggageAllowance() {
        return hasBaggageAllowance;
    }
    
    public void setHasBaggageAllowance(boolean hasBaggageAllowance) {
        this.hasBaggageAllowance = hasBaggageAllowance;
    }
    
}
