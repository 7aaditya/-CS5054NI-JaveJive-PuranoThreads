package com.puranothread.model;

import java.util.Date;

public class order {
    private int orderId;
    private String orderName;
    private String orderMethod;
    private Date orderDate;
    private int orderTime;
    
    
    private int userId;
    private String userName;
    private int totalPayment; // We'll map this to orderTime for display purposes
    private int itemCount = 1; // Default value
    private String shippingAddress = "Not specified"; // Default value
    private String status = "Processing"; // Default value
    
    // Getters and setters
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public String getOrderName() {
        return orderName;
    }
    
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    
    public String getOrderMethod() {
        return orderMethod;
    }
    
    public void setOrderMethod(String orderMethod) {
        this.orderMethod = orderMethod;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public int getOrderTime() {
        return orderTime;
    }
    
    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
        this.totalPayment = orderTime; // Map orderTime to totalPayment for display
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public int getTotalPayment() {
        return totalPayment;
    }
    
    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}