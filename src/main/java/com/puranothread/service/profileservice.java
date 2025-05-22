package com.puranothread.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.puranothread.config.dbconfig;
import com.puranothread.model.usermodel;

public class profileservice {
    
    /**
     * Fetches user data from the database by username
     * 
     * @param username the username of the user to fetch
     * @return usermodel containing the user's data
     */
    public usermodel getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        String selectSQL = "SELECT UserName, UserPhone, UserEmail, UserPassword FROM user WHERE UserName = ?";
        
        try (Connection conn = dbconfig.getDbConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            
            selectStmt.setString(1, username);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    usermodel user = new usermodel();
                    user.setUserName(rs.getString("UserName"));
                    user.setUserPhone(rs.getString("UserPhone"));
                    user.setUserEmail(rs.getString("UserEmail"));
                    user.setUserPassword(rs.getString("UserPassword"));
                    return user;
                }
            }
        } catch (SQLException se) {
            System.err.println("SQL error when fetching user: " + se.getMessage());
            se.printStackTrace();
            throw se;
        }
        
        return null;
    }
    
    /**
     * Fetches user data from the database by user ID
     * 
     * @param userId the ID of the user to fetch
     * @return usermodel containing the user's data
     */
    public usermodel getUserById(int userId) throws SQLException, ClassNotFoundException {
        String selectSQL = "SELECT UserName, UserPhone, UserEmail, UserPassword FROM user WHERE UserID = ?";
        
        try (Connection conn = dbconfig.getDbConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            
            selectStmt.setInt(1, userId);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    usermodel user = new usermodel();
                    user.setUserName(rs.getString("UserName"));
                    user.setUserPhone(rs.getString("UserPhone"));
                    user.setUserEmail(rs.getString("UserEmail"));
                    user.setUserPassword(rs.getString("UserPassword"));
                    return user;
                }
            }
        } catch (SQLException se) {
            System.err.println("SQL error when fetching user: " + se.getMessage());
            se.printStackTrace();
            throw se;
        }
        
        return null;
    }
}