package com.puranothread.controller;

import com.puranothread.model.productmodel;
import com.puranothread.service.adminunlistservice;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/adminunlist")
public class adminunlistcontroller extends HttpServlet {
    
    private adminunlistservice service;
    
    @Override
    public void init() {
        service = new adminunlistservice();
        System.out.println("Adminunlist controller initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("Adminunlist controller doGet called");
        String action = request.getParameter("action");
        
        if (action != null && action.equals("search")) {
            // Handle search operation
            String category = request.getParameter("category");
            String searchTerm = request.getParameter("searchTerm");
            
            System.out.println("Searching products - Category: " + category + ", Search term: " + searchTerm);
            
            try {
                List<productmodel> products = service.searchProducts(category, searchTerm);
                System.out.println("Found " + products.size() + " products");
                request.getSession().setAttribute("searchResults", products);
                
                // If it's an AJAX request, return JSON
                if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                    response.setContentType("application/json");
                    PrintWriter out = response.getWriter();
                    
                    StringBuilder json = new StringBuilder("[");
                    for (int i = 0; i < products.size(); i++) {
                        productmodel product = products.get(i);
                        json.append("{");
                        json.append("\"id\":").append(product.getProductID()).append(",");
                        json.append("\"name\":\"").append(escapeJson(product.getProductName())).append("\",");
                        json.append("\"price\":").append(product.getProductPrice()).append(",");
                        json.append("\"category\":\"").append(escapeJson(product.getProductCategory())).append("\"");
                        json.append("}");
                        
                        if (i < products.size() - 1) {
                            json.append(",");
                        }
                    }
                    json.append("]");
                    
                    out.print(json.toString());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error searching products: " + e.getMessage());
                request.getSession().setAttribute("error", "Error searching products: " + e.getMessage());
            }
        }
        
        // Forward to the JSP page in WEB-INF directory
        System.out.println("Forwarding to adminunlist.jsp in WEB-INF");
        request.getRequestDispatcher("/WEB-INF/pages/adminunlist.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("Adminunlist controller doPost called");
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/adminunlist");
            return;
        }
        
        // Set response type to JSON for AJAX requests
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String jsonResponse;
        
        switch (action) {
            case "delete":
                // Handle delete operation
                String productIdStr = request.getParameter("productId");
                System.out.println("Deleting product with ID: " + productIdStr);
                
                if (productIdStr != null && !productIdStr.isEmpty()) {
                    try {
                        int productId = Integer.parseInt(productIdStr);
                        boolean success = service.deleteProduct(productId);
                        
                        if (success) {
                            System.out.println("Product deleted successfully");
                            jsonResponse = "{\"success\":true,\"message\":\"Product successfully unlisted!\"}";
                        } else {
                            System.out.println("Failed to delete product");
                            jsonResponse = "{\"success\":false,\"message\":\"Failed to unlist product.\"}";
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid product ID: " + e.getMessage());
                        jsonResponse = "{\"success\":false,\"message\":\"Invalid product ID.\"}";
                    } catch (Exception e) {
                        System.err.println("Error deleting product: " + e.getMessage());
                        jsonResponse = "{\"success\":false,\"message\":\"Error: " + escapeJson(e.getMessage()) + "\"}";
                    }
                } else {
                    System.out.println("No product ID provided for deletion");
                    jsonResponse = "{\"success\":false,\"message\":\"No product selected for unlisting.\"}";
                }
                
                out.print(jsonResponse);
                break;
                
            case "update":
                // Handle update operation
                String updateProductIdStr = request.getParameter("productId");
                String newName = request.getParameter("newName");
                String newPriceStr = request.getParameter("newPrice");
                
                System.out.println("Updating product - ID: " + updateProductIdStr + 
                                  ", New name: " + newName + 
                                  ", New price: " + newPriceStr);
                
                if (updateProductIdStr != null && !updateProductIdStr.isEmpty()) {
                    try {
                        int productId = Integer.parseInt(updateProductIdStr);
                        int newPrice = 0;
                        
                        if (newPriceStr != null && !newPriceStr.isEmpty()) {
                            newPrice = Integer.parseInt(newPriceStr);
                        }
                        
                        boolean success = service.updateProduct(productId, newName, newPrice);
                        
                        if (success) {
                            System.out.println("Product updated successfully");
                            jsonResponse = "{\"success\":true,\"message\":\"Product successfully updated!\"}";
                        } else {
                            System.out.println("Failed to update product");
                            jsonResponse = "{\"success\":false,\"message\":\"Failed to update product.\"}";
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid product ID or price: " + e.getMessage());
                        jsonResponse = "{\"success\":false,\"message\":\"Invalid product ID or price.\"}";
                    } catch (Exception e) {
                        System.err.println("Error updating product: " + e.getMessage());
                        jsonResponse = "{\"success\":false,\"message\":\"Error: " + escapeJson(e.getMessage()) + "\"}";
                    }
                } else {
                    System.out.println("No product ID provided for update");
                    jsonResponse = "{\"success\":false,\"message\":\"No product selected for update.\"}";
                }
                
                out.print(jsonResponse);
                break;
                
            default:
                System.out.println("Unknown action: " + action);
                response.sendRedirect(request.getContextPath() + "/adminunlist");
        }
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