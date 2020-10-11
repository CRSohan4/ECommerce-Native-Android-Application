package com.outfit.user.model;

public class SellerRecyclerModel {
    public String showroom, address, status, seller_uid;

    public SellerRecyclerModel() {
    }

    public SellerRecyclerModel(String showroom, String address, String status, String seller_uid) {
        this.showroom = showroom;
        this.address = address;
        this.status = status;
        this.seller_uid = seller_uid;
    }

    public String getSeller_uid() {
        return seller_uid;
    }

    public void setSeller_uid(String seller_uid) {
        this.seller_uid = seller_uid;
    }


    public String getShowroom() {
        return showroom;
    }

    public void setShowroom(String showroom) {
        this.showroom = showroom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
