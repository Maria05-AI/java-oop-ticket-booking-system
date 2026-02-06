/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;

import oopproject.model.Train;



public class ExpressTrain extends Train {
    
    private float maxSpeed;
    
    // Constructor
    public ExpressTrain(int trainID, String trainName, String trainType, int capacity, float maxSpeed) {
        super(trainID, trainName, trainType, capacity);
        this.maxSpeed = maxSpeed;
    }
    
    // Default constructor
    public ExpressTrain() {
        super();
    }
    
    // Getters and Setters
    public float getMaxSpeed() {
        return maxSpeed;
    }
    
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
    

