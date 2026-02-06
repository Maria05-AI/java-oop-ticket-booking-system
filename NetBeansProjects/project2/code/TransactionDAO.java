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
import oopproject.model.Transaction;
import oopproject.model.Booking;
import oopproject.model.Cancellation;


public class TransactionDAO {
    
    private Connection conn;
    
    public TransactionDAO() {
        conn = DBConnection.getConnector().fetchConnection();
    }
    
    
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, ticket_id, transaction_date, type) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getUserID());
            pstmt.setInt(2, transaction.getTicketID());
            pstmt.setDate(3, new java.sql.Date(transaction.getTransactionDate().getTime()));
            pstmt.setString(4, transaction.getType());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    transaction.setTransactionID(generatedKeys.getInt(1));
                    
                    
                    if (transaction instanceof Booking) {
                        return addBookingDetails((Booking) transaction);
                    } else if (transaction instanceof Cancellation) {
                        return addCancellationDetails((Cancellation) transaction);
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }
        return false;
    }
    
    
    private boolean addBookingDetails(Booking booking) {
        String sql = "INSERT INTO bookings (transaction_id, booking_status, payment_method, fare, booking_reference) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getTransactionID());
            pstmt.setString(2, booking.getBookingStatus());
            pstmt.setString(3, booking.getPaymentMethod());
            pstmt.setFloat(4, booking.getFare());
            pstmt.setString(5, booking.getBookingReference());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding booking details: " + e.getMessage());
        }
        return false;
    }
    
    
    private boolean addCancellationDetails(Cancellation cancellation) {
        String sql = "INSERT INTO cancellations (transaction_id, reason, refund_amount, is_refunded) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cancellation.getTransactionID());
            pstmt.setString(2, cancellation.getCancellationReason());
            pstmt.setFloat(3, cancellation.getRefundAmount());
            pstmt.setBoolean(4, cancellation.isIsRefunded());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding cancellation details: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET user_id = ?, ticket_id = ?, transaction_date = ?, type = ? " +
                     "WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transaction.getUserID());
            pstmt.setInt(2, transaction.getTicketID());
            pstmt.setDate(3, new java.sql.Date(transaction.getTransactionDate().getTime()));
            pstmt.setString(4, transaction.getType());
            pstmt.setInt(5, transaction.getTransactionID());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
               
                if (transaction instanceof Booking) {
                    return updateBookingDetails((Booking) transaction);
                } else if (transaction instanceof Cancellation) {
                    return updateCancellationDetails((Cancellation) transaction);
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating transaction: " + e.getMessage());
        }
        return false;
    }
    
    
    private boolean updateBookingDetails(Booking booking) {
        String sql = "UPDATE bookings SET booking_status = ?, payment_method = ?, fare = ?, booking_reference = ? " +
                     "WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getBookingStatus());
            pstmt.setString(2, booking.getPaymentMethod());
            pstmt.setFloat(3, booking.getFare());
            pstmt.setString(4, booking.getBookingReference());
            pstmt.setInt(5, booking.getTransactionID());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking details: " + e.getMessage());
        }
        return false;
    }
    
   
    private boolean updateCancellationDetails(Cancellation cancellation) {
        String sql = "UPDATE cancellations SET reason = ?, refund_amount = ?, is_refunded = ? " +
                     "WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cancellation.getCancellationReason());
            pstmt.setFloat(2, cancellation.getRefundAmount());
            pstmt.setBoolean(3, cancellation.isIsRefunded());
            pstmt.setInt(4, cancellation.getTransactionID());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating cancellation details: " + e.getMessage());
        }
        return false;
    }
    
    
    public boolean deleteTransaction(int transactionId) {
        // First determine the type of transaction
        Transaction transaction = getTransactionById(transactionId);
        if (transaction == null) {
            return false;
        }
        
        
        if (transaction instanceof Booking) {
            deleteBookingDetails(transactionId);
        } else if (transaction instanceof Cancellation) {
            deleteCancellationDetails(transactionId);
        }
        
       
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
        }
        return false;
    }
    
    
    private boolean deleteBookingDetails(int transactionId) {
        String sql = "DELETE FROM bookings WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting booking details: " + e.getMessage());
        }
        return false;
    }
    
    
    private boolean deleteCancellationDetails(int transactionId) {
        String sql = "DELETE FROM cancellations WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting cancellation details: " + e.getMessage());
        }
        return false;
    }
    
    
    public Transaction getTransactionById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String type = rs.getString("type");
                
                if ("Booking".equals(type)) {
                    return getBookingByTransactionId(transactionId);
                } else if ("Cancellation".equals(type)) {
                    return getCancellationByTransactionId(transactionId);
                } else {
                    return extractTransactionFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction by ID: " + e.getMessage());
        }
        return null;
    }
    
    
    public Booking getBookingByTransactionId(int transactionId) {
        String sql = "SELECT t.*, b.status, b.booking_id " +
                 "FROM transactions t " +
                 "JOIN bookings b ON t.transaction_id = b.transaction_id " +
                 "WHERE t.transaction_id = ?";
    
     try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, transactionId);
        
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            Booking booking = new Booking();
            
            booking.setTransactionID(rs.getInt("transaction_id"));
            booking.setUserID(rs.getInt("user_id"));
            booking.setTicketID(rs.getInt("ticket_id"));
            booking.setTransactionDate(rs.getDate("transaction_date"));
            booking.setType(rs.getString("type"));
            
            booking.setBookingStatus(rs.getString("status"));
            
           
            booking.setPaymentMethod("Credit Card");
            booking.setFare(0.0f);
            booking.setBookingReference("BK" + transactionId);
            
            return booking;
        }
            }      catch (SQLException e) {
        System.err.println("Error getting booking by transaction ID: " + e.getMessage());
    }
    return null;
    }
    
    
    private Cancellation getCancellationByTransactionId(int transactionId) {
        String sql = "SELECT t.*, c.reason, c.refund_amount, c.is_refunded " +
                     "FROM transactions t " +
                     "JOIN cancellations c ON t.transaction_id = c.transaction_id " +
                     "WHERE t.transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cancellation cancellation = new Cancellation();
                
                cancellation.setTransactionID(rs.getInt("transaction_id"));
                cancellation.setUserID(rs.getInt("user_id"));
                cancellation.setTicketID(rs.getInt("ticket_id"));
                cancellation.setTransactionDate(rs.getDate("transaction_date"));
                cancellation.setType(rs.getString("type"));
                
                cancellation.setCancellationReason(rs.getString("reason"));
                cancellation.setRefundAmount(rs.getFloat("refund_amount"));
                cancellation.setIsRefunded(rs.getBoolean("is_refunded"));
                
                return cancellation;
            }
        } catch (SQLException e) {
            System.err.println("Error getting cancellation by transaction ID: " + e.getMessage());
        }
        return null;
    }
    
    
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                String type = rs.getString("type");
                
                if ("Booking".equals(type)) {
                    transactions.add(getBookingByTransactionId(transactionId));
                } else if ("Cancellation".equals(type)) {
                    transactions.add(getCancellationByTransactionId(transactionId));
                } else {
                    transactions.add(extractTransactionFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transactions by user ID: " + e.getMessage());
        }
        return transactions;
    }
    
    
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        
        try {
            
            String bookingsSql = "SELECT t.*, b.status " +
                   "FROM transactions t " +
                   "JOIN bookings b ON t.ticket_id = b.ticket_id";
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(bookingsSql)) {
                
                while (rs.next()) {
                    Booking booking = new Booking();
    
                    booking.setTransactionID(rs.getInt("transaction_id"));
                    booking.setUserID(rs.getInt("user_id"));
                    booking.setTicketID(rs.getInt("ticket_id"));
                    booking.setTransactionDate(rs.getDate("transaction_date"));
                    booking.setType(rs.getString("type"));
    
                    booking.setBookingStatus(rs.getString("status"));
    
    
                    booking.setPaymentMethod("N/A");
                    booking.setFare(0.0f);
                    booking.setBookingReference("BK" + booking.getTransactionID());
    
                     transactions.add(booking);
                }
            }
            
            
            String cancellationsSql = "SELECT t.*, c.reason, c.refund_amount, c.is_refunded " +
                                    "FROM transactions t " +
                                    "JOIN cancellations c ON t.transaction_id = c.transaction_id";
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(cancellationsSql)) {
                
                while (rs.next()) {
                    Cancellation cancellation = new Cancellation();
                    
                    cancellation.setTransactionID(rs.getInt("transaction_id"));
                    cancellation.setUserID(rs.getInt("user_id"));
                    cancellation.setTicketID(rs.getInt("ticket_id"));
                    cancellation.setTransactionDate(rs.getDate("transaction_date"));
                    cancellation.setType(rs.getString("type"));
                    
                    cancellation.setCancellationReason(rs.getString("reason"));
                    cancellation.setRefundAmount(rs.getFloat("refund_amount"));
                    cancellation.setIsRefunded(rs.getBoolean("is_refunded"));
                    
                    transactions.add(cancellation);
                }
            }
            
            
            if (transactions.isEmpty()) {
                generateSampleTransactions(transactions);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
            
            generateSampleTransactions(transactions);
        }
        
        return transactions;
    }
    
    
    private void generateSampleTransactions(List<Transaction> transactions) {
        Booking booking1 = new Booking();
        booking1.setTransactionID(1001);
        booking1.setUserID(1);
        booking1.setTicketID(101);
        booking1.setTransactionDate(new java.util.Date());
        booking1.setType("Booking");
        booking1.setBookingStatus("Confirmed");
        booking1.setPaymentMethod("Credit Card");
        booking1.setFare(150.0f);
        booking1.setBookingReference("BK10012023");
        transactions.add(booking1);
        
        
        Booking booking2 = new Booking();
        booking2.setTransactionID(1002);
        booking2.setUserID(2);
        booking2.setTicketID(102);
        booking2.setTransactionDate(new java.util.Date());
        booking2.setType("Booking");
        booking2.setBookingStatus("Pending");
        booking2.setPaymentMethod("PayPal");
        booking2.setFare(120.0f);
        booking2.setBookingReference("BK10022023");
        transactions.add(booking2);
        
        
        Cancellation cancellation = new Cancellation();
        cancellation.setTransactionID(1003);
        cancellation.setUserID(1);
        cancellation.setTicketID(103);
        cancellation.setTransactionDate(new java.util.Date());
        cancellation.setType("Cancellation");
        cancellation.setCancellationReason("Change of plans");
        cancellation.setRefundAmount(90.0f);
        cancellation.setIsRefunded(true);
        transactions.add(cancellation);
    }
    
    
    private Transaction extractTransactionFromResultSet(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        
        transaction.setTransactionID(rs.getInt("transaction_id"));
        transaction.setUserID(rs.getInt("user_id"));
        transaction.setTicketID(rs.getInt("ticket_id"));
        transaction.setTransactionDate(rs.getDate("transaction_date"));
        transaction.setType(rs.getString("type"));
        
        return transaction;
    }
}
    

