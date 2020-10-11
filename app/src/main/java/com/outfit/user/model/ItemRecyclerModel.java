package com.outfit.user.model;

public class ItemRecyclerModel {
    public String itemName, itemImageUrl, price, item_uid, seller_uid, gender, qty;

    public ItemRecyclerModel() {
    }

    public ItemRecyclerModel(String itemName, String itemImageUrl, String price, String item_uid, String seller_uid, String gender, String qty) {
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.price = price;
        this.item_uid = item_uid;
        this.seller_uid = seller_uid;
        this.gender = gender;
        this.qty = qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeller_uid() {
        return seller_uid;
    }

    public void setSeller_uid(String seller_uid) {
        this.seller_uid = seller_uid;
    }

    public String getItem_uid() {
        return item_uid;
    }

    public void setItem_uid(String item_uid) {
        this.item_uid = item_uid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
