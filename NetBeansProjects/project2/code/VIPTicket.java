/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;
import oopproject.model.Ticket;
import java.util.Date;



public class VIPTicket extends Ticket{
    
    private boolean hasLoungeAccess;
    private boolean hasFreeSnacks;
    
    // Constructor
    public VIPTicket(int ticketID, Date bookingDate, int seatNumber, float price, 
            String ticketClass, boolean hasLoungeAccess, boolean hasFreeSnacks) {
        super(ticketID, bookingDate, seatNumber, price, ticketClass, "Available");
        this.hasLoungeAccess = hasLoungeAccess;
        this.hasFreeSnacks = hasFreeSnacks;
    }
    
    // Default constructor
    public VIPTicket() {
        super();
        this.setTicketClass("VIP");
        this.hasLoungeAccess = true;
        this.hasFreeSnacks = true;
    }
    
    // Override calculate price to include VIP benefits
    @Override
    public float calculatePrice() {
        float basePrice = getPrice();
        float additionalCost = 0;
        
        if (hasLoungeAccess) {
            additionalCost += 75; 
        }
        
        if (hasFreeSnacks) {
            additionalCost += 25; 
        }
        
        return basePrice + additionalCost;
    }
    
    // Getters and Setters
    public boolean getHasLoungeAccess() {
        return hasLoungeAccess;
    }
    
    public void setHasLoungeAccess(boolean hasLoungeAccess) {
        this.hasLoungeAccess = hasLoungeAccess;
    }
    
    public boolean getHasFreeSnacks() {
        return hasFreeSnacks;
    }
    
    public void setHasFreeSnacks(boolean hasFreeSnacks) {
        this.hasFreeSnacks = hasFreeSnacks;
    }
    
    
   
}
