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
import oopproject.model.Ticket;
import oopproject.model.StandardTicket;
import oopproject.model.VIPTicket;


public class TicketDAO {
    
    private Connection conn;
    
    public TicketDAO() {
        conn = DBConnection.getConnector().fetchConnection();
    }
    
    
    public boolean addTicket(Ticket ticket,  int userId) {
        String sql = "INSERT INTO tickets (passenger_id, type, price, seat_number) " +
                 "VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        pstmt.setInt(1, userId);
        pstmt.setString(2, ticket.getTicketClass());
        pstmt.setFloat(3, ticket.getPrice());
        pstmt.setInt(4, ticket.getSeatNumber());
        
        int affectedRows = pstmt.executeUpdate();
        
        if (affectedRows > 0) {
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticket.setTicketID(generatedKeys.getInt(1));
            }
            return true;
        }
    } catch (SQLException e) {
        System.err.println("Error adding ticket: " + e.getMessage());
    }
    return false;
}
    
    
    public boolean updateTicket(Ticket ticket) {
         String sql = "UPDATE tickets SET passenger_id = ?, type = ?, price = ?, seat_number = ? " +
                 "WHERE ticket_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 0); 
            pstmt.setString(2, ticket.getTicketClass());
            pstmt.setFloat(3, ticket.getPrice());
            pstmt.setInt(4, ticket.getSeatNumber());
            pstmt.setInt(5, ticket.getTicketID());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean deleteTicket(int ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ticket: " + e.getMessage());
        }
        return false;
    }
    
    
    public Ticket getTicketById(int ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractTicketFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting ticket by ID: " + e.getMessage());
        }
        return null;
    }
    
    public Ticket getTicketByBookingId(String bookingReference) {
    String sql = "SELECT t.* FROM tickets t " +
                 "JOIN bookings b ON t.ticket_id = b.ticket_id " +
                 "WHERE b.booking_reference = ?";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, bookingReference);
        
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return extractTicketFromResultSet(rs);
        }
    } catch (SQLException e) {
        System.err.println("Error getting ticket by booking reference: " + e.getMessage());
    }
    return null;
}
    
    
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tickets.add(extractTicketFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all tickets: " + e.getMessage());
        }
        return tickets;
    }
    
    
    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.* FROM tickets t " +
                 "JOIN bookings b ON t.ticket_id = b.ticket_id " +
                 "WHERE b.passenger_id = ?";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, userId);
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            tickets.add(extractTicketFromResultSet(rs));
        }
    } catch (SQLException e) {
        System.err.println("Error getting tickets by user ID: " + e.getMessage());
    }
    return tickets;
    }
    
    
    private Ticket extractTicketFromResultSet(ResultSet rs) throws SQLException {
        String ticketClass = rs.getString("type");
        Ticket ticket;
    
    if ("VIP".equals(ticketClass)) {
        ticket = new VIPTicket(
            rs.getInt("ticket_id"),
            new java.util.Date(), 
            rs.getInt("seat_number"),
            rs.getFloat("price"),
            ticketClass,
            true, 
            true
        );
    } else {
        ticket = new StandardTicket(
            rs.getInt("ticket_id"),
            new java.util.Date(), 
            rs.getInt("seat_number"),
            rs.getFloat("price"),
            ticketClass,
            false 
        );
    }
    
    return ticket;
    }
}
    

