package com.example.milestone2.Model;

public class Products {
    private String Category, Description, Image, Pid, Pname, Price;

    Products(){

    }

    public Products(String category, String description, String image, String pid, String pname, String price) {
        Category = category;
        Description = description;
        Image = image;
        Pid = pid;
        Pname = pname;
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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
}
