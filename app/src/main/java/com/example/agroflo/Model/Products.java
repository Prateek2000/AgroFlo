package com.example.agroflo.Model;

public class Products
{
    private String amount, item, phone, price;

    public Products()
    {

    }

    public Products(String amount, String item, String phone, String price) {
        this.amount = amount;
        this.item = item;
        this.phone = phone;
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
