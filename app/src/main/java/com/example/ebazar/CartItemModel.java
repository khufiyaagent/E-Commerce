package com.example.ebazar;

public class CartItemModel {
    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT=1;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    /////cart item

    private int productImage;
    private String productTitle;
    private int freeCoupons;
    private String productPrice;
    private String cutPrice;
    private int productQuantity;
    private int offersApplied;
    private int couponApplied;

    public CartItemModel(int type, int productImage, String productTitle, int freeCoupons, String productPrice, String cutPrice, int productQuantity, int offersApplied, int couponApplied) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupons = freeCoupons;
        this.productPrice = productPrice;
        this.cutPrice = cutPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.couponApplied = couponApplied;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(int freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(String cutPrice) {
        this.cutPrice = cutPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }

    public int getCouponApplied() {
        return couponApplied;
    }

    public void setCouponApplied(int couponApplied) {
        this.couponApplied = couponApplied;
    }
    /////cart item

    //////cart total item
    private String numberOfItem;
    private String totalItemsPrice;
    private String deliveryPrice;
    private String totalAmount;
    private String savedPrice;

    public CartItemModel(int type, String numberOfItem, String totalItemsPrice, String deliveryPrice, String totalAmount, String savedPrice) {
        this.type = type;
        this.numberOfItem = numberOfItem;
        this.totalItemsPrice = totalItemsPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalAmount = totalAmount;
        this.savedPrice = savedPrice;
    }

    public String getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(String numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public String getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(String totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSavedPrice() {
        return savedPrice;
    }

    public void setSavedPrice(String savedPrice) {
        this.savedPrice = savedPrice;
    }


    //////cart total item
}
