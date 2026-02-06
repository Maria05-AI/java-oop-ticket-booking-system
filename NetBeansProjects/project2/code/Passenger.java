/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;
import oopproject.model.User;
import java.util.ArrayList;
import java.util.List;


public class Passenger extends User{
    
    private List<Ticket> travelHistory;
    
    public Passenger() {
        super();
        setRole("Passenger");
        this.travelHistory = new ArrayList<>();
    }
    
    public Passenger(int userID, String firstName, String lastName, String email, String password, String phone) {
        super(userID, firstName, lastName, email, password, phone, "Passenger");
        this.travelHistory = new ArrayList<>();
    }
    
    public Passenger(int userID, String firstName, String lastName, String email, String password, String phone, String role) {
        super(userID, firstName, lastName, email, password, phone, role);
        this.travelHistory = new ArrayList<>();
    }
    
    
    // Passenger-specific methods
    public void searchTransportation() {
        System.out.println("Searching for transportation");
    }
    
    public void bookTicket() {
        System.out.println("Booking a ticket");
    }
    
    public void cancelBooking() {
        System.out.println("Cancelling a booking");
    }
    
    public void writeReview() {
        System.out.println("Writing a review");
    }
    
    public void viewBookingHistory() {
        System.out.println("Viewing booking history");
    }
    
    public float getTotalSpent() {
        float total = 0;
        for (Ticket ticket : travelHistory) {
            total += ticket.getPrice();
        }
        return total;
    }
    
    // Getters and Setters
    public List<Ticket> getTravelHistory() {
        return travelHistory;
    }
    
    public void setTravelHistory(List<Ticket> travelHistory) { 
        this.travelHistory = travelHistory; 
    }
    
    // Add to travel history
    public void addToTravelHistory(Ticket ticket) { 
        if (this.travelHistory == null) this.travelHistory = new ArrayList<>();
        this.travelHistory.add(ticket);
    }
    
    
}
    
    
    
    

