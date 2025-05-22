package com.puranothread.controller;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.puranothread.model.productmodel;
import com.puranothread.service.adminlistingservice;
import com.puranothread.util.imageutil;

@WebServlet(asyncSupported = true, urlPatterns = { "/adminlisting" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class adminlistingcontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private adminlistingservice listingService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        listingService = new adminlistingservice();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get all products and add them to the request
        request.setAttribute("products", listingService.getAllProducts());
        request.getRequestDispatcher("/WEB-INF/pages/adminlisting.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Action: " + action);

        try {
            if ("add".equals(action)) {
                // Get form parameters
                String productName = request.getParameter("productName");
                String productCategory = request.getParameter("productCategory");
                int productPrice = Integer.parseInt(request.getParameter("productPrice"));
                String productCondition = request.getParameter("productCondition");
                String subCategory = request.getParameter("subCategory");
                String productDescription = request.getParameter("productDescription");
                
                // Handle file upload
                Part filePart = request.getPart("productImage");
                String imagePath = null;
                
                if (filePart != null && filePart.getSize() > 0) {
                    // Get the application's real path
                    String applicationPath = request.getServletContext().getRealPath("");
                    
                    // Save the image and get its path
                    imagePath = imageutil.saveImage(filePart, applicationPath);
                }
                
                // Create product object
                productmodel product = new productmodel(productName, productCategory, productPrice, 
                                             productCondition, subCategory, productDescription);
                product.setImagePath(imagePath);
                
                // Save product to database
                boolean added = listingService.addProduct(product);
                
                if (added) {
                    request.setAttribute("successMessage", "Product added successfully!");
                } else {
                    request.setAttribute("errorMessage", "Failed to add product.");
                }
                
                // Redirect to listing page
                response.sendRedirect(request.getContextPath() + "/adminlisting");
            } else {
                System.out.println("Invalid action, redirect to listing page");
                response.sendRedirect(request.getContextPath() + "/adminlisting");
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format (e.g., non-numeric price)
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/error.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            
            // Set error message and forward to error page
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}