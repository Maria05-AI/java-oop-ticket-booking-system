/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;
import oopproject.model.User;
import java.util.ArrayList;
import java.util.List;



public class Admin extends User{
    
    private String role;
    
    // Constructor
    public Admin(int userID, String firstName, String lastName, String email, String password, String phone) {
        super(userID, firstName, lastName, email, password, phone, "Admin");
    } 
    
    
    // Constructor with 7 parameters (with role)
    public Admin(int userID, String firstName, String lastName, String email, String password, String phone, String role) {
        super(userID, firstName, lastName, email, password, phone, role);
    }
    
    // Default constructor
    public Admin() {
        this.role = "Admin";
    }
    
    // Admin-specific methods
    public void manageUsers() {
        System.out.println("Managing users");
    }
    
    public void manageBookings() {
        System.out.println("Managing bookings");
    }
    
    public void handleReviews() {
        System.out.println("Handling reviews");
    }
    
    public void generateBookingReport() {
        System.out.println("Generating booking report");
    }
    
    // Getters and Setters
    public String getRole() {
        return role;
    }
}
    

