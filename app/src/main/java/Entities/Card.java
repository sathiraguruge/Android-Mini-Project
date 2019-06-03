package Entities;

import java.io.Serializable;

public class Card implements Serializable{

    private int ID;
    private String name;
    private String cardNo;
    private String type;
    private String expDate;
    private String date;
    private String description;

    public Card() {
    }

    public Card(int ID, String name, String cardNo, String type, String expDate, String date, String description) {
        this.ID = ID;
        this.name = name;
        this.cardNo = cardNo;
        this.type = type;
        this.expDate = expDate;
        this.date = date;
        this.description = description;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

