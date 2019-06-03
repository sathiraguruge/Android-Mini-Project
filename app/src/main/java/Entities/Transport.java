package Entities;

import java.io.Serializable;

public class Transport implements Serializable {

    private int ID;
    private String name;
    private String description;
    private double price;
    private String date;
    private String dateAdded;

    public Transport() {
    }

    public Transport(int ID, String name, String description, double price, String date, String dateAdded) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.dateAdded = dateAdded;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
