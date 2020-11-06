package com.example.ebazar;

public class RewardModel {
    private String coupon_title;
    private String coupon_validity;
    private String coupon_body;

    public RewardModel(String coupon_title, String coupon_validity, String coupon_body) {
        this.coupon_title = coupon_title;
        this.coupon_validity = coupon_validity;
        this.coupon_body = coupon_body;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public String getCoupon_validity() {
        return coupon_validity;
    }

    public void setCoupon_validity(String coupon_validity) {
        this.coupon_validity = coupon_validity;
    }

    public String getCoupon_body() {
        return coupon_body;
    }

    public void setCoupon_body(String coupon_body) {
        this.coupon_body = coupon_body;
    }
}
