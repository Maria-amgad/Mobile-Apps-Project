package com.example.milestone2.Model;

public class Cart {
    private String Pid, Pname, Price;
    private int Quantity;

    public Cart(){

    }

    public Cart(String pid, String pname, String price, int quantity) {
        Pid = pid;
        Pname = pname;
        Price = price;
        Quantity = quantity;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
