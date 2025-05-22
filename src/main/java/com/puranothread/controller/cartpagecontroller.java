package com.puranothread.controller;

import com.puranothread.config.dbconfig;
import com.puranothread.model.productmodel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class cartpagecontroller
 */
@WebServlet("/cartpage")
public class cartpagecontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cartpagecontroller() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        
        // If cart is null, initialize it
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        
        // If cart has items, fetch product details from database
        if (!cart.isEmpty()) {
            try {
                updateCartItemsFromDatabase(cart);
                System.out.println("Cart items updated from database");
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("Error fetching product details from database: " + e.getMessage());
                e.printStackTrace();
                // Set error message to display to user
                session.setAttribute("message", "Error retrieving product details. Please try again later.");
            }
        }
        
        // Forward the request to the JSP page
        request.getRequestDispatcher("/WEB-INF/pages/cartpage.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if this is a payment processing request
        String action = request.getParameter("action");
        if ("processPayment".equals(action)) {
            processPayment(request, response);
            return;
        }
        
        // Handle item removal if an ID is provided
        String itemId = request.getParameter("id");
        if (itemId != null && !itemId.isEmpty()) {
            HttpSession session = request.getSession();
            List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
            
            if (cart != null) {
                int id = Integer.parseInt(itemId);
                // Remove the item with the matching ID
                cart.removeIf(item -> (int) item.get("id") == id);
                session.setAttribute("cart", cart);
                
                // Set success message
                session.setAttribute("message", "Item removed from cart successfully!");
            }
        }
        
        // Handle add to cart from shop page
        String productId = request.getParameter("productId");
        if (productId != null && !productId.isEmpty()) {
            String productName = request.getParameter("productName");
            String productPrice = request.getParameter("productPrice");
            String quantity = request.getParameter("quantity");
            
            if (productName != null && productPrice != null && quantity != null) {
                HttpSession session = request.getSession();
                List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
                
                if (cart == null) {
                    cart = new ArrayList<>();
                    session.setAttribute("cart", cart);
                }
                
                // Check if product already exists in cart
                int id = Integer.parseInt(productId);
                double price = Double.parseDouble(productPrice);
                int qty = Integer.parseInt(quantity);
                
                boolean found = false;
                for (Map<String, Object> item : cart) {
                    if ((int) item.get("id") == id) {
                        int currentQty = (int) item.get("quantity");
                        item.put("quantity", currentQty + qty);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    Map<String, Object> newItem = new HashMap<>();
                    newItem.put("id", id);
                    newItem.put("name", productName);
                    newItem.put("price", price);
                    newItem.put("quantity", qty);
                    
                    // Try to get product image from database
                    try {
                        String imageUrl = getProductImageUrl(id);
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            newItem.put("imageUrl", imageUrl);
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        System.err.println("Error fetching product image: " + e.getMessage());
                    }
                    
                    cart.add(newItem);
                }
                
                // Set success message
                session.setAttribute("message", "Product added to cart successfully!");
            }
        }
        
        // Redirect back to the cart page
        response.sendRedirect(request.getContextPath() + "/cartpage");
    }
    
    /**
     * Process payment and create order
     */
    private void processPayment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
            session.setAttribute("message", "Your cart is empty. Please add items before checkout.");
            response.sendRedirect(request.getContextPath() + "/cartpage");
            return;
        }
        
        String paymentMethod = request.getParameter("paymentMethod");
        String deliveryLocation = request.getParameter("deliveryLocation");
        
        // Generate order number
        String orderNumber = generateOrderNumber();
        
        // Save order to database
        boolean orderSaved = saveOrder(orderNumber, cart, paymentMethod, deliveryLocation);
        
        // In the processPayment method
        if (orderSaved) {
            // Set success message
            session.setAttribute("message", "Order confirmed! Your order number is " + orderNumber + ". Thank you for shopping with us!");
            
            // Clear the cart
            session.removeAttribute("cart");
            
            // Redirect to cart page which will show the success message
            response.sendRedirect(request.getContextPath() + "/cartpage");
        } else {
            session.setAttribute("message", "Failed to process your order. Please try again.");
            response.sendRedirect(request.getContextPath() + "/cartpage");
        }
    }
    
    /**
     * Generate a unique order number
     */
    private String generateOrderNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return "PT" + dateFormat.format(new Date()) + (int)(Math.random() * 1000);
    }
    
    /**
     * Save order to database
     */
    private boolean saveOrder(String orderNumber, List<Map<String, Object>> cart, String paymentMethod, String deliveryLocation) {
        // This is a simplified version - in a real application, you would save to database
        // For now, we'll just return true to simulate successful order saving
        return true;
    }
    
    /**
     * Gets the image URL for a product
     * 
     * @param productId The product ID
     * @return The image URL or null if not found
     * @throws SQLException If a database error occurs
     * @throws ClassNotFoundException If the database driver is not found
     */
    private String getProductImageUrl(int productId) throws SQLException, ClassNotFoundException {
        try (Connection conn = dbconfig.getDbConnection()) {
            String sql = "SELECT ImagePath FROM product WHERE ProductID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, productId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("ImagePath");
                    }
                }
            }
        }
        
        return null;
    }
    
    /**
     * Updates cart items with the latest product information from the database
     * 
     * @param cart The cart list to update
     * @throws SQLException If a database error occurs
     * @throws ClassNotFoundException If the database driver is not found
     */
    private void updateCartItemsFromDatabase(List<Map<String, Object>> cart) throws SQLException, ClassNotFoundException {
        // Get database connection
        try (Connection conn = dbconfig.getDbConnection()) {
            // For each item in the cart, fetch the latest product details
            for (Map<String, Object> item : cart) {
                int productId = (int) item.get("id");
                
                // Prepare SQL query to get product details
                String sql = "SELECT * FROM product WHERE ProductID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, productId);
                    
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            // Update product details in the cart item
                            item.put("name", rs.getString("ProductName"));
                            item.put("price", rs.getDouble("ProductPrice"));
                            item.put("imageUrl", rs.getString("ImagePath"));
                            
                            // Keep the quantity from the cart
                            if (!item.containsKey("quantity")) {
                                item.put("quantity", 1); // Default quantity if not set
                            }
                        } else {
                            // Product not found in database, mark for removal
                            item.put("toRemove", true);
                        }
                    }
                }
            }
            
            // Remove items marked for removal
            cart.removeIf(item -> item.containsKey("toRemove") && (boolean) item.get("toRemove"));
        }
    }
}
