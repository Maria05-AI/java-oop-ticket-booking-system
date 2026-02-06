/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;


public class Train {
    
    private int trainID;
    private String trainName;
    private String trainType;
    private int capacity;
    private int occupancy;
    
    // Constructor
    public Train(int trainID, String trainName, String trainType, int capacity) {
        this.trainID = trainID;
        this.trainName = trainName;
        this.trainType = trainType;
        this.capacity = capacity;
        this.occupancy = 0; 
    }
    
    // Default constructor
    public Train() {
        this.occupancy = 0;
    }
    
    // Get occupancy method
    public int getOccupancy() {
        return occupancy;
    }
    
    // Check if train is available
    public boolean isAvailable() {
        return occupancy < capacity;
    }
    
    // Getters and Setters
    public int getTrainID() {
        return trainID;
    }
    
    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }
    
    public String getTrainName() {
        return trainName;
    }
    
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    
    public String getTrainType() {
        return trainType;
    }
    
    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    // Method to update occupancy
    public void updateOccupancy(int newCount) {
        this.occupancy = newCount;
    }
    
}
