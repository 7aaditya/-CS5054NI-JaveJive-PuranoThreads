package com.puranothread.model;

public class productmodel {
    private int productID;
    private String productName;
    private String productCategory;
    private int productPrice;
    private String productCondition;
    private String subCategory;
    private String productDescription;
    private String imagePath; 
    
    // Default constructor
    public productmodel() {
    }
    
    // Constructor with fields (without ID and imagePath)
    public productmodel(String productName, String productCategory, int productPrice, 
                      String productCondition, String subCategory, String productDescription) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productCondition = productCondition;
        this.subCategory = subCategory;
        this.productDescription = productDescription;
    }
    
    // Constructor with all fields including ID and imagePath
    public productmodel(int productID, String productName, String productCategory, int productPrice, 
                      String productCondition, String subCategory, String productDescription, String imagePath) {
        this.productID = productID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productCondition = productCondition;
        this.subCategory = subCategory;
        this.productDescription = productDescription;
        this.imagePath = imagePath;
    }
    
    // Getters and setters
    public int getProductID() {
        return productID;
    }
    
    public void setProductID(int productID) {
        this.productID = productID;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductCategory() {
        return productCategory;
    }
    
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    
    public int getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    
    public String getProductCondition() {
        return productCondition;
    }
    
    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }
    
    public String getSubCategory() {
        return subCategory;
    }
    
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    
    public String getproductDescription() {
        return productDescription;
    }
    
    public void setproductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productPrice=" + productPrice +
                ", productCondition='" + productCondition + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}