package com.puranothread.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.puranothread.config.dbconfig;
import com.puranothread.model.usermodel;
import com.puranothread.util.passwordutil;

public class loginservice {

    private Connection dbConn;
    private boolean isConnectionError = false;

    public loginservice() {
        try {
            dbConn = dbconfig.getDbConnection();
            isConnectionError = (dbConn == null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            isConnectionError = true;
        }
    }

    public Boolean loginUser(usermodel loginModel) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        String query = "SELECT UserName, UserPassword FROM user WHERE username = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, loginModel.getUserName());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return validatePassword(result, loginModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return false;
    }

    private boolean validatePassword(ResultSet result, usermodel userModel) throws SQLException {
        String dbUsername = result.getString("UserName");
        String dbPassword = result.getString("UserPassword");

        // Hash the password the user entered during login
        String inputHashedPassword = passwordutil.hashPassword(userModel.getUserPassword());

        // Compare the stored hashed password with the newly hashed input
        boolean isValid = dbUsername.equals(userModel.getUserName()) &&
                          dbPassword.equals(inputHashedPassword);

        return isValid;
    }


}