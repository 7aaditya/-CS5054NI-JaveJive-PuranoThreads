package com.puranothread.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.puranothread.model.order;
import com.puranothread.config.dbconfig; // Note: lowercase 'c' in dbconfig

/**
 * Servlet implementation class admindashboardcontroller
 */
@WebServlet("/admindashboard")
public class admindashboardcontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public admindashboardcontroller() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get recent orders (with highest IDs)
            List<order> recentOrders = getRecentOrders(5);
            
            // Set the orders as an attribute to be accessed in the JSP
            request.setAttribute("recentOrders", recentOrders);
            
            // Forward to the JSP
            request.getRequestDispatcher("/WEB-INF/pages/admindashboard.jsp").forward(request, response);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error in doGet: " + e.getMessage());
            e.printStackTrace();
            
            // Set error message and forward to error page or back to dashboard
            request.setAttribute("errorMessage", "Failed to load dashboard data: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/admindashboard.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Get recent orders sorted by highest OrderID
     */
    private List<order> getRecentOrders(int limit) {
        List<order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Get connection from dbconfig - FIXED: using correct method name and class name
            System.out.println("Attempting to get database connection...");
            conn = dbconfig.getDbConnection(); // Changed from dbConfig.getConnection() to dbconfig.getDbConnection()
            System.out.println("Connection successful: " + (conn != null));
            
            if (conn == null) {
                System.err.println("Database connection is null. Check your dbconfig class.");
                return orders; // Return empty list if connection fails
            }
            
            // Simplified SQL query - removed alias to avoid potential issues
            String sql = "SELECT OrderID, OrderName, OrderMethod, OrderDate, OrderTime " +
                         "FROM `order` " +
                         "ORDER BY OrderID DESC " +
                         "LIMIT ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            
            System.out.println("Executing SQL query: " + sql);
            rs = pstmt.executeQuery();
            System.out.println("Query executed successfully");
            
            int count = 0;
            while (rs.next()) {
                count++;
                order orderObj = new order();
                orderObj.setOrderId(rs.getInt("OrderID"));
                orderObj.setOrderName(rs.getString("OrderName"));
                orderObj.setOrderMethod(rs.getString("OrderMethod"));
                orderObj.setOrderDate(rs.getDate("OrderDate"));
                orderObj.setOrderTime(rs.getInt("OrderTime"));
                
                // Set status based on OrderMethod or other logic
                if ("khalti".equalsIgnoreCase(orderObj.getOrderMethod())) {
                    orderObj.setStatus("Paid");
                } else if ("Online".equalsIgnoreCase(orderObj.getOrderMethod())) {
                    orderObj.setStatus("Processing");
                } else if ("InStore".equalsIgnoreCase(orderObj.getOrderMethod())) {
                    orderObj.setStatus("Completed");
                } else {
                    orderObj.setStatus("Pending");
                }
                
                // Set totalPayment from orderTime
                orderObj.setTotalPayment(orderObj.getOrderTime());
                
                orders.add(orderObj);
            }
            System.out.println("Retrieved " + count + " orders from database");
            
        } catch (SQLException e) {
            System.err.println("SQL Error in getRecentOrders: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General Error in getRecentOrders: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return orders;
    }
}