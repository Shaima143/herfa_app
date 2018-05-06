package com.herfa.android.herfa;

public class MarketDetails {
    private int product_image;
    private int product_name;

    public MarketDetails() {
    }

    public MarketDetails(int product_image, int product_name) {
        this.product_image = product_image;
        this.product_name = product_name;
    }

    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public int getProduct_name() {
        return product_name;
    }

    public void setProduct_name(int product_name) {
        this.product_name = product_name;
    }
}
