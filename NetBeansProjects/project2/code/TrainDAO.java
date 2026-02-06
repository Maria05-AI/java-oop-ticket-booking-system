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
import oopproject.model.Train;
import oopproject.model.ExpressTrain;


public class TrainDAO {
    
    private Connection conn;
    
    public TrainDAO() {
        conn = DBConnection.getConnector().fetchConnection();
    }
    
    
    public boolean addTrain(Train train) {
        String sql = "INSERT INTO express_trains (train_name, train_type, capacity, occupancy, max_speed) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, train.getTrainName());
            pstmt.setString(2, train.getTrainType());
            pstmt.setInt(3, train.getCapacity());
            pstmt.setInt(4, train.getOccupancy());
            
            
            if (train instanceof ExpressTrain) {
                pstmt.setFloat(5, ((ExpressTrain) train).getMaxSpeed());
            } else {
                pstmt.setFloat(5, 0); 
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    train.setTrainID(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding train: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean updateTrain(Train train) {
        String sql = "UPDATE express_trains SET train_name = ?, train_type = ?, capacity = ?, occupancy = ?, " +
                     "max_speed = ? WHERE train_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, train.getTrainName());
            pstmt.setString(2, train.getTrainType());
            pstmt.setInt(3, train.getCapacity());
            pstmt.setInt(4, train.getOccupancy());
            
            
            if (train instanceof ExpressTrain) {
                pstmt.setFloat(5, ((ExpressTrain) train).getMaxSpeed());
            } else {
                pstmt.setFloat(5, 0); 
            }
            
            pstmt.setInt(6, train.getTrainID());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating train: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean deleteTrain(int trainId) {
        String sql = "DELETE FROM express_trains WHERE train_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting train: " + e.getMessage());
        }
        return false;
    }
    
    
    public Train getTrainById(int trainId) {
        String sql = "SELECT * FROM express_trains WHERE train_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractTrainFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting train by ID: " + e.getMessage());
        }
        return null;
    }
    
    
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM express_trains";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                trains.add(extractTrainFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all trains: " + e.getMessage());
        }
        return trains;
    }
    
    
    private Train extractTrainFromResultSet(ResultSet rs) throws SQLException {
        float maxSpeed = rs.getFloat("max_speed");
        Train train;
        
        if (maxSpeed > 0) {
            ExpressTrain expressTrain = new ExpressTrain();
            expressTrain.setMaxSpeed(maxSpeed);
            train = expressTrain;
        } else {
            train = new Train();
        }
        
        train.setTrainID(rs.getInt("train_id"));
        train.setTrainName(rs.getString("train_name"));
        train.setTrainType(rs.getString("train_type"));
        train.setCapacity(rs.getInt("capacity"));
        train.updateOccupancy(rs.getInt("occupancy"));
        
        return train;
    }
}
    
    

