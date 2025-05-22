// Complete AboutUsController.java with all necessary imports
package com.puranothread.controller; // Update this to match your package structure

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.puranothread.config.dbconfig;

@WebServlet("/aboutus")
public class aboutuscontroller extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    // Your existing doGet method here
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/aboutus.jsp").forward(request, response);
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the review content from the form
        String reviewContent = request.getParameter("reviewContent");
        System.out.println("Received review content: " + reviewContent); // Debug line
        
        if (reviewContent != null && !reviewContent.trim().isEmpty()) {
            try {
                // Create a database connection using your dbconfig
                Connection conn = null;
                PreparedStatement pstmt = null;
                
                try {
                    // Get connection from your dbconfig
                    conn = dbconfig.getDbConnection();
                    
                    // SQL to insert a new review
                    String sql = "INSERT INTO Review (ReviewName, ReviewType, ReviewTime) VALUES (?, ?, ?)";
                    pstmt = conn.prepareStatement(sql);
                    
                    // Set parameters
                    pstmt.setString(1, reviewContent);
                    pstmt.setString(2, "User Feedback"); // You can change this type as needed
                    
                    // Set current time as seconds since epoch
                    int currentTimeSeconds = (int) (System.currentTimeMillis() / 1000);
                    pstmt.setInt(3, currentTimeSeconds);
                    
                    // Execute the insert
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Rows affected by insert: " + rowsAffected); // Debug line
                    
                    if (rowsAffected > 0) {
                        // Set success message
                        request.setAttribute("reviewSuccess", "Thank you for your feedback!");
                        System.out.println("Review saved successfully"); // Debug line
                    } else {
                        // Set error message
                        request.setAttribute("reviewError", "Failed to submit your feedback. Please try again.");
                        System.out.println("Failed to save review"); // Debug line
                    }
                    
                } finally {
                    // Close resources
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                }
                
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("reviewError", "Database error occurred. Please try again later.");
                System.out.println("Database error: " + e.getMessage()); // Debug line
            }
        } else {
            // Set error message for empty review
            request.setAttribute("reviewError", "Please enter your feedback before submitting.");
            System.out.println("Empty review content"); // Debug line
        }
        
        // Forward back to the About Us page
        request.getRequestDispatcher("/WEB-INF/pages/aboutus.jsp").forward(request, response);
    }
}

