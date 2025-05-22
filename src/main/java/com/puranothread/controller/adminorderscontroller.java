package com.puranothread.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.puranothread.model.order;
import com.puranothread.service.adminordersservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/adminorders" })
public class adminorderscontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private adminordersservice orderService;
    
    @Override
    public void init() throws ServletException {
        try {
            orderService = new adminordersservice();
            boolean connected = orderService.testDatabaseConnection();
            System.out.println("Database connection test: " + (connected ? "SUCCESS" : "FAILED"));
        } catch (Exception e) {
            System.err.println("Error initializing AdminOrdersService: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Failed to initialize AdminOrdersService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get the search parameter
            String searchId = request.getParameter("searchId");
            List<order> orders;
            
            if (searchId != null && !searchId.trim().isEmpty()) {
                try {
                    int orderId = Integer.parseInt(searchId);
                    orders = orderService.searchOrdersById(orderId);
                    System.out.println("Searching for order ID: " + orderId + ", found: " + orders.size() + " orders");
                } catch (NumberFormatException e) {
                    // If the search ID is not a valid number, get all orders
                    orders = orderService.getAllOrders();
                    request.setAttribute("errorMessage", "Please enter a valid order ID.");
                    System.out.println("Invalid order ID format, showing all orders");
                }
            } else {
                // If no search parameter, get all orders
                orders = orderService.getAllOrders();
                System.out.println("No search parameter, showing all orders: " + orders.size());
            }
            
            // Set the orders attribute for the JSP
            request.setAttribute("orders", orders);
            
            // Debug: Print out the orders to verify data
            if (orders != null) {
                System.out.println("Setting orders attribute with " + orders.size() + " orders");
                for (order order : orders) {
                    System.out.println("Order ID: " + order.getOrderId() + ", User ID: " + order.getUserId());
                }
            } else {
                System.out.println("Orders list is null");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database error in doGet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error in doGet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
        }
        
        // Make sure this path matches where your JSP file is located
        request.getRequestDispatcher("/WEB-INF/pages/adminorders.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("doPost method called");
        PrintWriter out = response.getWriter();
        
        try {
            String action = request.getParameter("action");
            System.out.println("Action parameter: " + action);
            
            if ("delete".equals(action)) {
                try {
                    String orderIdParam = request.getParameter("orderId");
                    System.out.println("OrderId parameter: " + orderIdParam);
                    
                    if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
                        sendErrorResponse(response, "Order ID is missing");
                        return;
                    }
                    
                    int orderId = Integer.parseInt(orderIdParam);
                    System.out.println("Attempting to delete order with ID: " + orderId);
                    
                    boolean deleted = orderService.deleteOrder(orderId);
                    System.out.println("Delete operation result: " + deleted);
                    
                    response.setContentType("application/json");
                    if (deleted) {
                        out.write("{\"success\": true, \"message\": \"Order deleted successfully.\"}");
                    } else {
                        out.write("{\"success\": false, \"message\": \"Failed to delete order. Order may not exist.\"}");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid order ID format: " + e.getMessage());
                    sendErrorResponse(response, "Invalid order ID format");
                } catch (SQLException e) {
                    System.err.println("SQL Exception: " + e.getMessage());
                    e.printStackTrace();
                    sendErrorResponse(response, "Database error: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + e.getMessage());
                    e.printStackTrace();
                    sendErrorResponse(response, "Database driver not found: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                    sendErrorResponse(response, "Unexpected error: " + e.getMessage());
                }
            } else {
                System.err.println("Invalid action: " + action);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            System.err.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            sendErrorResponse(response, "Server error: " + e.getMessage());
        }
    }
    
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write("{\"success\": false, \"message\": \"" + escapeJson(message) + "\"}");
    }
    
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}