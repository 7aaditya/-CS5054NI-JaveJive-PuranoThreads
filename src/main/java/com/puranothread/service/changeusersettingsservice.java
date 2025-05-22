package com.puranothread.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.puranothread.config.dbconfig;
import com.puranothread.model.usermodel;

public class changeusersettingsservice {
    
    /**
     * Fetches user data from the database by user ID
     * 
     * @param userId the ID of the user to fetch
     * @return usermodel containing the user's data
     */
    public usermodel getUserById(int userId) throws SQLException, ClassNotFoundException {
        String selectSQL = "SELECT UserName, UserPhone, UserEmail FROM user WHERE UserID = ?";
        
        try (Connection conn = dbconfig.getDbConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            
            selectStmt.setInt(1, userId);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    usermodel user = new usermodel();
                    user.setUserName(rs.getString("UserName"));
                    user.setUserPhone(rs.getString("UserPhone"));
                    user.setUserEmail(rs.getString("UserEmail"));
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
     * Fetches user data from the database by username
     * 
     * @param username the username of the user to fetch
     * @return usermodel containing the user's data
     */
    public usermodel getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        String selectSQL = "SELECT UserName, UserPhone, UserEmail FROM user WHERE UserName = ?";
        
        try (Connection conn = dbconfig.getDbConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {
            
            selectStmt.setString(1, username);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    usermodel user = new usermodel();
                    user.setUserName(rs.getString("UserName"));
                    user.setUserPhone(rs.getString("UserPhone"));
                    user.setUserEmail(rs.getString("UserEmail"));
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
     * Updates user information in the database
     * 
     * @param user the usermodel containing updated user information
     * @param currentUsername the current username to identify the user to update
     * @return boolean indicating success or failure
     */
    public boolean updateUserSettings(usermodel user, String currentUsername) throws SQLException, ClassNotFoundException {
        String updateSQL = "UPDATE user SET UserName = ?, UserPhone = ?, UserEmail = ? WHERE UserName = ?";
        
        try (Connection conn = dbconfig.getDbConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
            
            updateStmt.setString(1, user.getUserName());
            updateStmt.setString(2, user.getUserPhone());
            updateStmt.setString(3, user.getUserEmail());
            updateStmt.setString(4, currentUsername); // Using current username as identifier
            
            int rowsAffected = updateStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException se) {
            System.err.println("SQL error when updating user: " + se.getMessage());
            se.printStackTrace();
            throw se;
        }
    }
}