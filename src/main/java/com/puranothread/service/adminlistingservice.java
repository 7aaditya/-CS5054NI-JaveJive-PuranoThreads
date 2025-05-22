package com.puranothread.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.puranothread.model.productmodel;
import com.puranothread.config.dbconfig;

public class adminlistingservice {
    
    // Method to get all products
    public List<productmodel> getAllProducts() {
        List<productmodel> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Use your dbconfig class to get a connection
            conn = dbconfig.getDbConnection();
            String sql = "SELECT * FROM Product";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                productmodel product = new productmodel();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductCategory(rs.getString("ProductCategory"));
                product.setProductPrice(rs.getInt("ProductPrice"));
                product.setProductCondition(rs.getString("ProductCondition"));
                product.setSubCategory(rs.getString("SubCategory"));
                product.setproductDescription(rs.getString("productDescription"));
                product.setImagePath(rs.getString("ImagePath")); // Added this line
                
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
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
    
    // Method to add a new product with image path
    public boolean addProduct(productmodel product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = dbconfig.getDbConnection();
            String sql = "INSERT INTO Product (ProductName, ProductCategory, ProductPrice, ProductCondition, SubCategory, productDescription, ImagePath) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductCategory());
            stmt.setInt(3, product.getProductPrice());
            stmt.setString(4, product.getProductCondition());
            stmt.setString(5, product.getSubCategory());
            stmt.setString(6, product.getproductDescription());
            stmt.setString(7, product.getImagePath()); // Added this line

            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }
}