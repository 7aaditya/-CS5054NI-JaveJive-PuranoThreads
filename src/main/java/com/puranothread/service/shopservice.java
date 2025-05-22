package com.puranothread.service;

import com.puranothread.model.product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shopservice {

    // Get all products from database
    public List<product> getAllProducts() {
        List<product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Use your existing dbconfig class
            conn = com.puranothread.config.dbconfig.getDbConnection();
            
            String sql = "SELECT * FROM product";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                product p = new product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setDescription(rs.getString("productDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setImageUrl(rs.getString("ImagePath"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setSubCategory(rs.getString("SubCategory"));
                p.setInStock(true); // Default to true since there's no specific field in the database
                p.setGender("Unisex"); // Default to "Unisex" since there's no gender field in the database
                
                products.add(p);
            }
            
            System.out.println("Fetched " + products.size() + " products from database");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return products;
    }
    
    // Get products by category
    public List<product> getProductsByCategory(String category) {
        List<product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = com.puranothread.config.dbconfig.getDbConnection();
            
            String sql = "SELECT * FROM product WHERE ProductCategory = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                product p = new product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setDescription(rs.getString("productDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setImageUrl(rs.getString("ImagePath"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setSubCategory(rs.getString("SubCategory"));
                p.setInStock(true);
                p.setGender("Unisex");
                
                products.add(p);
            }
            
            System.out.println("Fetched " + products.size() + " products for category: " + category);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching products by category: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return products;
    }
    
    // Get products by subcategory
    public List<product> getProductsBySubCategory(String subCategory) {
        List<product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = com.puranothread.config.dbconfig.getDbConnection();
            
            String sql = "SELECT * FROM product WHERE SubCategory = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, subCategory);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                product p = new product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setDescription(rs.getString("productDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setImageUrl(rs.getString("ImagePath"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setSubCategory(rs.getString("SubCategory"));
                p.setInStock(true);
                p.setGender("Unisex");
                
                products.add(p);
            }
            
            System.out.println("Fetched " + products.size() + " products for subcategory: " + subCategory);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching products by subcategory: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return products;
    }
    
    // Get products by gender
    public List<product> getProductsByGender(String gender) {
        List<product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = com.puranothread.config.dbconfig.getDbConnection();
            
            // Assuming you have a gender column in your product table
            // If not, you might need to modify this query or add the column
            String sql = "SELECT * FROM product WHERE gender = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, gender);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                product p = new product();
                p.setId(rs.getInt("ProductID"));
                p.setName(rs.getString("ProductName"));
                p.setDescription(rs.getString("productDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setImageUrl(rs.getString("ImagePath"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setSubCategory(rs.getString("SubCategory"));
                p.setInStock(true);
                p.setGender(rs.getString("gender"));
                
                products.add(p);
            }
            
            System.out.println("Fetched " + products.size() + " products for gender: " + gender);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching products by gender: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return products;
    }
    
    // Get featured product by ID
    public product getFeaturedProduct(int productId) {
        product featuredProduct = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = com.puranothread.config.dbconfig.getDbConnection();
            
            String sql = "SELECT * FROM product WHERE ProductID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                featuredProduct = new product();
                featuredProduct.setId(rs.getInt("ProductID"));
                featuredProduct.setName(rs.getString("ProductName"));
                featuredProduct.setDescription(rs.getString("productDescription"));
                featuredProduct.setPrice(rs.getDouble("ProductPrice"));
                featuredProduct.setImageUrl(rs.getString("ImagePath"));
                featuredProduct.setCategory(rs.getString("ProductCategory"));
                featuredProduct.setSubCategory(rs.getString("SubCategory"));
                featuredProduct.setInStock(true);
                featuredProduct.setGender("Unisex");
                
                System.out.println("Fetched featured product: " + featuredProduct.getName());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching featured product: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return featuredProduct != null ? featuredProduct : getDefaultFeaturedProduct();
    }
    
    // Get default featured product
    public product getDefaultFeaturedProduct() {
        // Create a default product in case no featured product is found
        product defaultProduct = new product();
        defaultProduct.setId(0);
        defaultProduct.setName("Sample Product");
        defaultProduct.setDescription("This is a sample product description. Please select a product from the grid to view details.");
        defaultProduct.setPrice(999.99);
        defaultProduct.setImageUrl("images/ImageIcon.png");
        defaultProduct.setCategory("Sample");
        defaultProduct.setSubCategory("Sample");
        defaultProduct.setInStock(true);
        defaultProduct.setGender("Unisex");
        
        return defaultProduct;
    }
    
    // Add to cart functionality
    public void addToCart(List<Map<String, Object>> cart, int productId, String productName, double productPrice, int quantity) {
        // Check if product already exists in cart
        boolean productExists = false;
        
        for (Map<String, Object> item : cart) {
            if ((int) item.get("id") == productId) {
                // Update quantity
                int currentQuantity = (int) item.get("quantity");
                item.put("quantity", currentQuantity + quantity);
                productExists = true;
                break;
            }
        }
        
        // If product doesn't exist in cart, add it
        if (!productExists) {
            Map<String, Object> newItem = new HashMap<>();
            newItem.put("id", productId);
            newItem.put("name", productName);
            newItem.put("price", productPrice);
            newItem.put("quantity", quantity);
            cart.add(newItem);
        }
    }
    
    // Add to favorites functionality
    public void addToFavorites(List<Map<String, Object>> favorites, int productId, String productName, double productPrice) {
        // Check if product already exists in favorites
        boolean productExists = false;
        
        for (Map<String, Object> item : favorites) {
            if ((int) item.get("id") == productId) {
                productExists = true;
                break;
            }
        }
        
        // If product doesn't exist in favorites, add it
        if (!productExists) {
            Map<String, Object> newItem = new HashMap<>();
            newItem.put("id", productId);
            newItem.put("name", productName);
            newItem.put("price", productPrice);
            favorites.add(newItem);
        }
    }
    
    // Remove from cart functionality
    public void removeFromCart(List<Map<String, Object>> cart, int productId) {
        cart.removeIf(item -> (int) item.get("id") == productId);
    }
    
    // Remove from favorites functionality
    public void removeFromFavorites(List<Map<String, Object>> favorites, int productId) {
        favorites.removeIf(item -> (int) item.get("id") == productId);
    }
}