package com.puranothread.model;

public class usermodel {
	
	private int UserID;
	private String UserName;
	private String UserAddress;
	private String UserPhone;
	private String UserEmail;
	private String UserRole;
	private String UserPassword;
	private boolean termsAccepted;
	
	public usermodel() {
    }
//model for registration
	 public usermodel(String UserName, String UserPhone, String UserEmail, String UserPassword) {
	        this.UserName = UserName;
	        this.UserPhone = UserPhone;
	        this.UserEmail = UserEmail;
	        this.UserPassword = UserPassword;
	    }
	 //model for login
	 public usermodel(String UserName, String UserPassword) {
	        this.UserName = UserName;
	        this.UserPassword = UserPassword;
	    }



public int getUserID(){
	return UserID;
}

public String getUserName() {
	return UserName;
}


public String getUserEmail() {
	return UserEmail;
}

public String getUserPhone() {
	return UserPhone ;
}
public String getUserPassword() {
	return UserPassword;
}

public void setUserID(int UserID) {
	this.UserID = UserID;
}
public void setUserName(String UserName) {
	this.UserName = UserName;
}
public void setUserPhone(String UserPhone) {
	this.UserPhone = UserPhone;
}
public void setUserEmail(String UserEmail) {
	this.UserEmail = UserEmail;
}
public void setUserRole(String UserRole) {
	this.UserRole = UserRole;
}

public void setUserPassword(String UserPassword) {
	this.UserPassword = UserPassword;
}

}