package com.puranothread.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.puranothread.config.dbconfig;

@WebServlet("/adminmessages")
public class adminmessagescontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create a list to store the reviews
        List<Map<String, Object>> reviews = new ArrayList<>();
        
        try {
            // Create a database connection using your dbconfig
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                // Get connection from your dbconfig
                conn = dbconfig.getDbConnection();
                
                // SQL to get all reviews
                String sql = "SELECT * FROM Review ORDER BY ReviewID DESC";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                
                System.out.println("Executing query: " + sql); // Debug line
                
                // Process the result set
                int count = 0;
                while (rs.next()) {
                    count++;
                    Map<String, Object> review = new HashMap<>();
                    review.put("reviewID", rs.getInt("ReviewID"));
                    review.put("reviewName", rs.getString("ReviewName"));
                    review.put("reviewType", rs.getString("ReviewType"));
                    review.put("reviewTime", rs.getInt("ReviewTime"));
                    
                    System.out.println("Found review: ID=" + rs.getInt("ReviewID") + 
                                      ", Name=" + rs.getString("ReviewName")); // Debug line
                    
                    reviews.add(review);
                }
                
                System.out.println("Total reviews found: " + count); // Debug line
                
            } finally {
                // Close resources
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Database error: " + e.getMessage()); // Debug line
            
            // Add some dummy data for testing if database fails
            if (reviews.isEmpty()) {
                System.out.println("Adding dummy data for testing");
                for (int i = 1; i <= 5; i++) {
                    Map<String, Object> review = new HashMap<>();
                    review.put("reviewID", 500 - i);
                    review.put("reviewName", "Test message " + i + ". This is a sample message for testing purposes.");
                    review.put("reviewType", "Feedback");
                    review.put("reviewTime", System.currentTimeMillis() / 1000);
                    reviews.add(review);
                }
            }
        }
        
        // Set the reviews as a request attribute
        request.setAttribute("reviews", reviews);
        System.out.println("Set reviews attribute with size: " + reviews.size()); // Debug line
        
        // Forward to the JSP page
        request.getRequestDispatcher("/WEB-INF/pages/adminmessages.jsp").forward(request, response);
    }
}