package com.puranothread.service;

import com.puranothread.config.dbconfig;
import com.puranothread.model.productmodel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class adminunlistservice {
    
    public adminunlistservice() {
        System.out.println("Adminunlist service initialized");
    }
    
    // Method to search products
    public List<productmodel> searchProducts(String category, String searchTerm) throws SQLException, ClassNotFoundException {
        List<productmodel> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            
            // Build SQL query based on search parameters
            StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
            
            if (category != null && !category.isEmpty() && !category.equals("Select Category")) {
                sql.append(" AND ProductCategory = ?");
            }
            
            if (searchTerm != null && !searchTerm.isEmpty()) {
                sql.append(" AND ProductName LIKE ?");
            }
            
            // Add order by clause
            sql.append(" ORDER BY ProductName");
            
            System.out.println("Search SQL: " + sql.toString());
            
            stmt = conn.prepareStatement(sql.toString());
            
            // Set parameters
            int paramIndex = 1;
            
            if (category != null && !category.isEmpty() && !category.equals("Select Category")) {
                stmt.setString(paramIndex++, category);
                System.out.println("Setting category parameter: " + category);
            }
            
            if (searchTerm != null && !searchTerm.isEmpty()) {
                stmt.setString(paramIndex, "%" + searchTerm + "%");
                System.out.println("Setting search term parameter: %" + searchTerm + "%");
            }
            
            rs = stmt.executeQuery();
            
            // Process results
            while (rs.next()) {
                productmodel product = new productmodel();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductCategory(rs.getString("ProductCategory"));
                product.setProductPrice(rs.getInt("ProductPrice"));
                
                // Handle optional fields that might not exist in all databases
                try {
                    product.setSubCategory(rs.getString("SubCategory"));
                } catch (SQLException e) {
                    product.setSubCategory("");
                }
                
                try {
                    product.setproductDescription(rs.getString("productDescription"));
                } catch (SQLException e) {
                    product.setproductDescription("");
                }
                
                try {
                    product.setProductCondition(rs.getString("ProductCondition"));
                } catch (SQLException e) {
                    product.setProductCondition("");
                }
                
                try {
                    product.setImagePath(rs.getString("ImagePath"));
                } catch (SQLException e) {
                    product.setImagePath("");
                }
                
                products.add(product);
                System.out.println("Found product: " + product.getProductName() + " (ID: " + product.getProductID() + ")");
            }
            
            return products;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
    
    // Method to get a product by ID
    public productmodel getProductById(int productId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbconfig.getDbConnection();
            
            // SQL query to get a product by ID
            String sql = "SELECT * FROM product WHERE ProductID = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                productmodel product = new productmodel();
                product.setProductID(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductCategory(rs.getString("ProductCategory"));
                product.setProductPrice(rs.getInt("ProductPrice"));
                
                // Handle optional fields
                try {
                    product.setSubCategory(rs.getString("SubCategory"));
                } catch (SQLException e) {
                    product.setSubCategory("");
                }
                
                try {
                    product.setproductDescription(rs.getString("productDescription"));
                } catch (SQLException e) {
                    product.setproductDescription("");
                }
                
                try {
                    product.setProductCondition(rs.getString("ProductCondition"));
                } catch (SQLException e) {
                    product.setProductCondition("");
                }
                
                try {
                    product.setImagePath(rs.getString("ImagePath"));
                } catch (SQLException e) {
                    product.setImagePath("");
                }
                
                return product;
            }
            
            return null;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
    
    // Method to delete a product
    public boolean deleteProduct(int productId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbconfig.getDbConnection();
            
            // SQL query to delete a product
            String sql = "DELETE FROM product WHERE ProductID = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Delete product - Rows affected: " + rowsAffected);
            
            return rowsAffected > 0;
        } finally {
            closeResources(null, stmt, conn);
        }
    }
    
    // Method to update a product
    public boolean updateProduct(int productId, String productName, int productPrice) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbconfig.getDbConnection();
            
            // SQL query to update a product
            String sql = "UPDATE product SET ProductName = ?, ProductPrice = ? WHERE ProductID = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productName);
            stmt.setInt(2, productPrice);
            stmt.setInt(3, productId);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Update product - Rows affected: " + rowsAffected);
            
            return rowsAffected > 0;
        } finally {
            closeResources(null, stmt, conn);
        }
    }
    
    // Helper method to close database resources
    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}