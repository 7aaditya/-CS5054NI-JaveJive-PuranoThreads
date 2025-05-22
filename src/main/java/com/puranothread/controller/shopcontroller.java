package com.puranothread.controller;

import com.puranothread.service.shopservice;
import com.puranothread.model.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/shop", "/shop/*"})
public class shopcontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final shopservice shopService;

    public shopcontroller() {
        this.shopService = new shopservice();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null) {
            // Get all products from database
            List<product> allProducts = shopService.getAllProducts();
            request.setAttribute("products", allProducts);
            
            // Get featured product (default or from parameter)
            String featuredIdParam = request.getParameter("featuredId");
            product featuredProduct;
            
            if (featuredIdParam != null && !featuredIdParam.isEmpty()) {
                try {
                    int featuredId = Integer.parseInt(featuredIdParam);
                    featuredProduct = shopService.getFeaturedProduct(featuredId);
                } catch (NumberFormatException e) {
                    featuredProduct = shopService.getDefaultFeaturedProduct();
                }
            } else if (!allProducts.isEmpty()) {
                // Use first product as featured if available
                featuredProduct = allProducts.get(0);
            } else {
                featuredProduct = shopService.getDefaultFeaturedProduct();
            }
            
            request.setAttribute("featuredProduct", featuredProduct);
            
            // Display shop page
            request.getRequestDispatcher("/WEB-INF/pages/shop.jsp").forward(request, response);
            return;
        }
        
        switch (action) {
            case "/cartpage":
                viewCart(request, response);
                break;
            case "/favoritespage":
                viewFavorites(request, response);
                break;
            case "/category":
                String category = request.getParameter("name");
                if (category != null && !category.isEmpty()) {
                    List<product> categoryProducts = shopService.getProductsByCategory(category);
                    request.setAttribute("products", categoryProducts);
                    request.setAttribute("categoryName", category);
                    
                    // Get featured product (first in category or default)
                    product featuredProduct = !categoryProducts.isEmpty() ? 
                            categoryProducts.get(0) : shopService.getDefaultFeaturedProduct();
                    request.setAttribute("featuredProduct", featuredProduct);
                }
                request.getRequestDispatcher("/WEB-INF/pages/shop.jsp").forward(request, response);
                break;
            case "/subcategory":
                String subcategory = request.getParameter("name");
                if (subcategory != null && !subcategory.isEmpty()) {
                    List<product> subcategoryProducts = shopService.getProductsBySubCategory(subcategory);
                    request.setAttribute("products", subcategoryProducts);
                    request.setAttribute("subcategoryName", subcategory);
                    
                    // Get featured product (first in subcategory or default)
                    product featuredProduct = !subcategoryProducts.isEmpty() ? 
                            subcategoryProducts.get(0) : shopService.getDefaultFeaturedProduct();
                    request.setAttribute("featuredProduct", featuredProduct);
                }
                request.getRequestDispatcher("/WEB-INF/pages/shop.jsp").forward(request, response);
                break;
            case "/gender":
                String gender = request.getParameter("type");
                if (gender != null && !gender.isEmpty()) {
                    List<product> genderProducts = shopService.getProductsByGender(gender);
                    request.setAttribute("products", genderProducts);
                    request.setAttribute("genderType", gender);
                    
                    // Get featured product (first in gender or default)
                    product featuredProduct = !genderProducts.isEmpty() ? 
                            genderProducts.get(0) : shopService.getDefaultFeaturedProduct();
                    request.setAttribute("featuredProduct", featuredProduct);
                }
                request.getRequestDispatcher("/WEB-INF/pages/shop.jsp").forward(request, response);
                break;
            case "/product":
                String productId = request.getParameter("id");
                if (productId != null && !productId.isEmpty()) {
                    try {
                        int id = Integer.parseInt(productId);
                        product selectedProduct = shopService.getFeaturedProduct(id);
                        request.setAttribute("featuredProduct", selectedProduct);
                        
                        // Get all products for the grid
                        List<product> allProducts = shopService.getAllProducts();
                        request.setAttribute("products", allProducts);
                    } catch (NumberFormatException e) {
                        // If ID is invalid, get default featured product
                        product defaultProduct = shopService.getDefaultFeaturedProduct();
                        request.setAttribute("featuredProduct", defaultProduct);
                    }
                }
                request.getRequestDispatcher("/WEB-INF/pages/shop.jsp").forward(request, response);
                break;
            default:
                // Get all products and default featured product
                List<product> allProducts = shopService.getAllProducts();
                request.setAttribute("products", allProducts);
                
                product defaultProduct = shopService.getDefaultFeaturedProduct();
                request.setAttribute("featuredProduct", defaultProduct);
                
                request.getRequestDispatcher("/WEB-INF/pages/shop.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/shop");
            return;
        }
        
        switch (action) {
            case "/cartpage":
                addToCart(request, response);
                break;
            case "/favoritespage":
                addToFavorites(request, response);
                break;
            case "/removeFromCart":
                removeFromCart(request, response);
                break;
            case "/removeFromFavorites":
                removeFromFavorites(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/shop");
                break;
        }
    }
    
    private void addToCart(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        
        shopService.addToCart(cart, productId, productName, productPrice, quantity);
        
        // Set a success message
        session.setAttribute("message", "Product added to cart successfully!");
        
        // Debug: Print cart contents to console
        System.out.println("Cart contents after adding item:");
        for (Map<String, Object> item : cart) {
            System.out.println("Item ID: " + item.get("id") + ", Name: " + item.get("name") + ", Price: " + item.get("price") + ", Quantity: " + item.get("quantity"));
        }
        
        // Redirect back to the shop page
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/shop");
        }
    }
    
    private void addToFavorites(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        
        HttpSession session = request.getSession();
        List<Map<String, Object>> favorites = (List<Map<String, Object>>) session.getAttribute("favorites");
        
        if (favorites == null) {
            favorites = new ArrayList<>();
            session.setAttribute("favorites", favorites);
        }
        
        shopService.addToFavorites(favorites, productId, productName, productPrice);
        
        // Set a success message
        session.setAttribute("message", "Product added to favorites successfully!");
        
        // Redirect back to the shop page
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/shop");
        }
    }
    
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        
        if (cart != null) {
            // Debug: Print cart contents before removal
            System.out.println("Cart contents before removal:");
            for (Map<String, Object> item : cart) {
                System.out.println("Item ID: " + item.get("id") + ", Name: " + item.get("name"));
            }
            
            shopService.removeFromCart(cart, productId);
            session.setAttribute("cart", cart);
            
            // Debug: Print cart contents after removal
            System.out.println("Cart contents after removal:");
            for (Map<String, Object> item : cart) {
                System.out.println("Item ID: " + item.get("id") + ", Name: " + item.get("name"));
            }
            
            session.setAttribute("message", "Product removed from cart successfully!");
        }
        
        // Redirect back to the cart page
        response.sendRedirect(request.getContextPath() + "cartpage");
    }
    
    private void removeFromFavorites(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        HttpSession session = request.getSession();
        List<Map<String, Object>> favorites = (List<Map<String, Object>>) session.getAttribute("favorites");
        
        if (favorites != null) {
            shopService.removeFromFavorites(favorites, productId);
            session.setAttribute("favorites", favorites);
            session.setAttribute("message", "Product removed from favorites successfully!");
        }
        
        // Redirect back to the favorites page
        response.sendRedirect(request.getContextPath() + "/favoritespage");
    }
    
    private void viewCart(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the cart from the session
        HttpSession session = request.getSession();
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        
        // If cart is null, initialize it
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        
        // Debug: Print cart contents
        System.out.println("Cart contents in viewCart:");
        for (Map<String, Object> item : cart) {
            System.out.println("Item ID: " + item.get("id") + ", Name: " + item.get("name") + ", Price: " + item.get("price") + ", Quantity: " + item.get("quantity"));
        }
        
        // Forward to cart page
        request.getRequestDispatcher("/WEB-INF/pages/cartpage.jsp").forward(request, response);
    }
    
    private void viewFavorites(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward to favorites page
        request.getRequestDispatcher("/WEB-INF/pages/favoritespage.jsp").forward(request, response);
    }
}