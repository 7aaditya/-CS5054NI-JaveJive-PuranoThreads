package com.puranothread.service;

import com.puranothread.model.order;
import com.puranothread.config.dbconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class adminordersservice {
    
    /**
     * Test the database connection
     * @return true if connection is successful, false otherwise
     * @throws SQLException if a database error occurs
     * @throws ClassNotFoundException if the database driver is not found
     */
    public boolean testDatabaseConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = dbconfig.getDbConnection();
            return conn != null;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    /**
     * Get all orders from the database
     * @return List of Order objects
     * @throws SQLException if a database error occurs
     * @throws ClassNotFoundException if the database driver is not found
     */
    public List<order> getAllOrders() throws SQLException, ClassNotFoundException {
        List<order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            
            // SQL query to get all orders, ordered by date (newest first)
            String sql = "SELECT o.OrderID, o.OrderName, o.OrderMethod, o.OrderDate, o.OrderTime, " +
                         "u.UserID, u.UserName " +
                         "FROM `Order` o " +
                         "LEFT JOIN UserOrder uo ON o.OrderID = uo.OrderID " +
                         "LEFT JOIN User u ON uo.UserID = u.UserID " +
                         "ORDER BY o.OrderDate DESC";
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            // Process results
            while (rs.next()) {
                order order = mapResultSetToOrder(rs);
                orders.add(order);
            }
            
            return orders;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
    
    /**
     * Search for orders by ID
     * @param orderId the order ID to search for
     * @return List of matching Order objects
     * @throws SQLException if a database error occurs
     * @throws ClassNotFoundException if the database driver is not found
     */
    public List<order> searchOrdersById(int orderId) throws SQLException, ClassNotFoundException {
        List<order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            
            // SQL query to search for an order by ID
            String sql = "SELECT o.OrderID, o.OrderName, o.OrderMethod, o.OrderDate, o.OrderTime, " +
                         "u.UserID, u.UserName " +
                         "FROM `Order` o " +
                         "LEFT JOIN UserOrder uo ON o.OrderID = uo.OrderID " +
                         "LEFT JOIN User u ON uo.UserID = u.UserID " +
                         "WHERE o.OrderID = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            
            rs = stmt.executeQuery();
            
            // Process results
            while (rs.next()) {
                order order = mapResultSetToOrder(rs);
                orders.add(order);
            }
            
            return orders;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
    
    /**
     * Delete an order from the database
     * @param orderId the ID of the order to delete
     * @return true if deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     * @throws ClassNotFoundException if the database driver is not found
     */
    public boolean deleteOrder(int orderId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbconfig.getDbConnection();
            conn.setAutoCommit(false);
            
            try {
                // Delete from UserOrderProductReview
                deleteFromTable(conn, "UserOrderProductReview", orderId);
                
                // Delete from UserOrderProduct
                deleteFromTable(conn, "UserOrderProduct", orderId);
                
                // Delete from UserOrderShipment
                deleteFromTable(conn, "UserOrderShipment", orderId);
                
                // Delete from UserOrderPayment
                deleteFromTable(conn, "UserOrderPayment", orderId);
                
                // Delete from UserOrder
                deleteFromTable(conn, "UserOrder", orderId);
                
                // Finally delete from Order table
                String sql = "DELETE FROM `Order` WHERE OrderID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, orderId);
                int rowsAffected = stmt.executeUpdate();
                
                conn.commit();
                return rowsAffected > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    // Log the exception
                    e.printStackTrace();
                }
            }
            closeResources(null, stmt, conn);
        }
    }
    
    /**
     * Helper method to delete records from a specific table
     * @param conn the database connection
     * @param tableName the name of the table
     * @param orderId the order ID
     * @throws SQLException if a database error occurs
     */
    private void deleteFromTable(Connection conn, String tableName, int orderId) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE OrderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // If the table doesn't exist or there's no data to delete, continue
            System.out.println("Note: Could not delete from " + tableName + ": " + e.getMessage());
        }
    }
    
    /**
     * Helper method to map ResultSet to Order object
     * @param rs the ResultSet containing order data
     * @return an Order object
     * @throws SQLException if a database error occurs
     */
    private order mapResultSetToOrder(ResultSet rs) throws SQLException {
        order order = new order();
        
        order.setOrderId(rs.getInt("OrderID"));
        order.setOrderName(rs.getString("OrderName"));
        order.setOrderMethod(rs.getString("OrderMethod"));
        order.setOrderDate(rs.getDate("OrderDate"));
        
        // Handle orderTime which might be stored as a string in the database
        try {
            order.setOrderTime(rs.getInt("OrderTime"));
        } catch (SQLException e) {
            // If OrderTime is a string, try to parse it
            String orderTimeStr = rs.getString("OrderTime");
            if (orderTimeStr != null && !orderTimeStr.isEmpty()) {
                try {
                    // Try to parse as integer (e.g., "1200" for 12:00)
                    order.setOrderTime(Integer.parseInt(orderTimeStr.replaceAll("[^0-9]", "")));
                } catch (NumberFormatException ex) {
                    // If parsing fails, set a default value
                    order.setOrderTime(0);
                }
            } else {
                order.setOrderTime(0);
            }
        }
        
        // These fields might be nullable, so check before setting
        try {
            int userId = rs.getInt("UserID");
            if (!rs.wasNull()) {
                order.setUserId(userId);
            }
        } catch (SQLException e) {
            // If userId column doesn't exist or is null
            order.setUserId(0);
        }
        
        try {
            String userName = rs.getString("UserName");
            if (userName != null) {
                order.setUserName(userName);
            }
        } catch (SQLException e) {
            // If userName column doesn't exist or is null
            order.setUserName("");
        }
        
        // Set default values for other fields
        order.setItemCount(getItemCountForOrder(order.getOrderId()));
        order.setShippingAddress(getShippingAddressForOrder(order.getOrderId()));
        order.setStatus(getOrderStatus(order.getOrderId()));
        
        return order;
    }
    
    /**
     * Get the number of items in an order
     * @param orderId the order ID
     * @return the number of items
     */
    private int getItemCountForOrder(int orderId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            String sql = "SELECT COUNT(*) FROM UserOrderProduct WHERE OrderID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, conn);
        }
        
        return 1; // Default to 1 if there's an error
    }
    
    /**
     * Get the shipping address for an order
     * @param orderId the order ID
     * @return the shipping address
     */
    private String getShippingAddressForOrder(int orderId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            String sql = "SELECT s.ShipmentLocation FROM Shipment s " +
                         "JOIN UserOrderShipment uos ON s.ShipmentID = uos.ShipmentID " +
                         "WHERE uos.OrderID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("ShipmentLocation");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, conn);
        }
        
        return "Not specified"; // Default value
    }
    
    /**
     * Get the status of an order
     * @param orderId the order ID
     * @return the order status
     */
    private String getOrderStatus(int orderId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            String sql = "SELECT s.ShipmentStatus FROM Shipment s " +
                         "JOIN UserOrderShipment uos ON s.ShipmentID = uos.ShipmentID " +
                         "WHERE uos.OrderID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("ShipmentStatus");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log the exception
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, conn);
        }
        
        return "Processing"; // Default value
    }
    
    /**
     * Helper method to close database resources
     * @param rs the ResultSet to close
     * @param stmt the PreparedStatement to close
     * @param conn the Connection to close
     */
    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
