package com.example.hotell.model;
import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomNumber;
    private String type;
    private double price;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
    private boolean Paid;

    public Room(String roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

    // Tambahkan getter dan setter untuk atribut baru
    public String getRoomNumber() {
        return roomNumber;
    }
    public String getType() {
        return type;
    }
    public double getPrice() {
        return price;
    }
    public String getGuestName() {
        return guestName;
    }
     public String getCheckInDate() {
        return checkInDate;
    }
     public String getCheckOutDate() {
        return checkOutDate;
    }

    
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public boolean isPaid() {
        return Paid;
    }
    public void setPaid(boolean Paid) {
        this.Paid = Paid;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", guestName='" + guestName + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", isPaid=" + Paid +
                '}';
    }
}
}

