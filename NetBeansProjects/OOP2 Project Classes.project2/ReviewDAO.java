/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oopproject.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oopproject.DBConnection;
import oopproject.DBConnection;
import oopproject.model.Review;


public class ReviewDAO {
    
    private Connection conn;
    
    public ReviewDAO() {
        conn = DBConnection.getConnector().fetchConnection();
    }
    
   
    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, rating, comment) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, review.getUserId());
            pstmt.setInt(2, review.getRating());
            pstmt.setString(3, review.getComment());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    review.setReviewId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding review: " + e.getMessage());
        }
        return false;
    }
    
   
    public boolean updateReview(Review review) {
        String sql = "UPDATE reviews SET rating = ?, comment = ? WHERE review_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, review.getRating());
            pstmt.setString(2, review.getComment());
            pstmt.setInt(3, review.getReviewId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating review: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting review: " + e.getMessage());
        }
        return false;
    }
    
    
    public Review getReviewById(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractReviewFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting review by ID: " + e.getMessage());
        }
        return null;
    }
    
    
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                reviews.add(extractReviewFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting reviews by user ID: " + e.getMessage());
        }
        return reviews;
    }
    
    
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews ORDER BY created_at DESC";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                reviews.add(extractReviewFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all reviews: " + e.getMessage());
        }
        return reviews;
    }
    
    
    private Review extractReviewFromResultSet(ResultSet rs) throws SQLException {
        Review review = new Review();
        
        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setCreatedAt(rs.getTimestamp("created_at"));
        
        return review;
    }
}
    
