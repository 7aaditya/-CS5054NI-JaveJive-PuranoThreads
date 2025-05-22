package com.puranothread.model;

public class favorite {
	private product product;

    public favorite(product product) {
        this.product = product;
    }

    public product getProduct() { return product; }
    public void setProduct(product product) { this.product = product; }
}