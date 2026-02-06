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
import oopproject.model.Bus;


public class BusDAO {
    
    private Connection conn;
    
    public BusDAO() {
        conn = DBConnection.getConnector().fetchConnection();
    }
    
    
    public boolean addBus(Bus bus) {
        
        createBusTable();
        
        String sql = "INSERT INTO buses (bus_number, bus_type, capacity, occupancy) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, bus.getBusNumber());
            pstmt.setString(2, bus.getBusType());
            pstmt.setInt(3, bus.getCapacity());
            pstmt.setInt(4, bus.getOccupancy());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bus.setBusId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding bus: " + e.getMessage());
        }
        return false;
    }
    
   
    private void createBusTable() {
        String sql = "CREATE TABLE IF NOT EXISTS buses ("
                + "bus_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "bus_number VARCHAR(50),"
                + "bus_type VARCHAR(50),"
                + "capacity INT,"
                + "occupancy INT)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating buses table: " + e.getMessage());
        }
    }
    
    
    public boolean updateBus(Bus bus) {
        String sql = "UPDATE buses SET bus_number = ?, bus_type = ?, capacity = ?, occupancy = ? WHERE bus_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bus.getBusNumber());
            pstmt.setString(2, bus.getBusType());
            pstmt.setInt(3, bus.getCapacity());
            pstmt.setInt(4, bus.getOccupancy());
            pstmt.setInt(5, bus.getBusId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating bus: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean deleteBus(int busId) {
        String sql = "DELETE FROM buses WHERE bus_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, busId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting bus: " + e.getMessage());
        }
        return false;
    }
    
    
    public Bus getBusById(int busId) {
        String sql = "SELECT * FROM buses WHERE bus_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, busId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractBusFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting bus by ID: " + e.getMessage());
        }
        return null;
    }
    
    
    public List<Bus> getAllBuses() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                buses.add(extractBusFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all buses: " + e.getMessage());
        }
        return buses;
    }
    
    
    public List<Bus> getAvailableBuses() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses WHERE occupancy < capacity";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                buses.add(extractBusFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting available buses: " + e.getMessage());
        }
        return buses;
    }
    
    
    private Bus extractBusFromResultSet(ResultSet rs) throws SQLException {
        Bus bus = new Bus();
        
        bus.setBusId(rs.getInt("bus_id"));
        bus.setBusNumber(rs.getString("bus_number"));
        bus.setBusType(rs.getString("bus_type"));
        bus.setCapacity(rs.getInt("capacity"));
        bus.updateOccupancy(rs.getInt("occupancy"));
        
        return bus;
    }
}
