/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;

public class Bus {
    
    private int busId;
    private String busNumber;
    private String busType;
    private int capacity;
    private int occupancy;
    
    // Constructor
    public Bus(int busId, String busNumber, String busType, int capacity) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.occupancy = 0;
    }
    
    // Default constructor
    public Bus() {
        this.occupancy = 0;
    }
    
    // Check if bus is available
    public boolean isAvailable() {
        return occupancy < capacity;
    }
    
    // Getters and Setters
    public int getBusId() {
        return busId;
    }
    
    public void setBusId(int busId) {
        this.busId = busId;
    }
    
    public String getBusNumber() {
        return busNumber;
    }
    
    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
    
    public String getBusType() {
        return busType;
    }
    
    public void setBusType(String busType) {
        this.busType = busType;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getOccupancy() {
        return occupancy;
    }
    
    public void updateOccupancy(int newCount) {
        this.occupancy = newCount;
    }
    
}
