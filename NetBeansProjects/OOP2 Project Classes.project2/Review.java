/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.model;


public class Review {
    
    private int reviewId;
    private int userId;
    private int rating; // 1-5 stars
    private String comment;
    private java.util.Date createdAt;
    
    // Constructor
    public Review(int reviewId, int userId, int rating, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = new java.util.Date();
    }
    
    // Default constructor
    public Review() {
        this.createdAt = new java.util.Date();
    }
    
    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public java.util.Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }
    
}
