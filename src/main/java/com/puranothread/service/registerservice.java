package com.puranothread.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.puranothread.config.dbconfig;
import com.puranothread.model.usermodel;

/**
 * Service class responsible for handling user registration logic.
 * Interacts with the database to insert new user records.
 */
public class registerservice {

    /**
     * Registers a new user by inserting their information into the database.
     *
     * @param user A usermodel object containing user registration details.
     * @return true if the user is successfully registered; false otherwise.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the database driver class is not found.
     */
    public boolean userRegister(usermodel user) throws SQLException, ClassNotFoundException {
        // SQL query to insert user details into the 'user' table
        String InsertSQL = "INSERT INTO user(UserName, UserPhone, UserEmail, UserPassword) VALUES (?, ?, ?, ?)";

        // Use try-with-resources to auto-close connection and statement
        try (Connection Conn = dbconfig.getDbConnection();
             PreparedStatement insertstmt = Conn.prepareStatement(InsertSQL)) {

            // Set the parameters for the prepared statement from the user model
            insertstmt.setString(1, user.getUserName());
            insertstmt.setString(2, user.getUserPhone());
            insertstmt.setString(3, user.getUserEmail());
            insertstmt.setString(4, user.getUserPassword());

            // Execute the insert operation and check if at least one row was inserted
            int rowsAffected = insertstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registered successfully: " + user.getUserName());
                return true;
            } else {
                System.out.println("User registration failed");
                return false;
            }

        } catch (SQLException se) {
            // Log SQL errors for debugging
            System.err.println("SQL error: " + se.getMessage());
            se.printStackTrace();
            throw se;  // Rethrow to allow higher-level handling
        }
    }
}