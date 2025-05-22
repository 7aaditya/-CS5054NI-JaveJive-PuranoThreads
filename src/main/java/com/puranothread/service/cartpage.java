package com.puranothread.service;

public class cartpage {
    private product product;
    private int quantity;
    
    // Default constructor
    public cartpage() {
    }
    
    // Constructor with parameters
    public cartpage(product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters and setters
    public product getproduct() {
        return product;
    }
    
    public void setProduct(product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}