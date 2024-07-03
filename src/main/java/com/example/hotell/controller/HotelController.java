package com.example.hotell.controller;


import com.example.hotell.model.Hotel;
import com.example.hotell.model.Room;

import java.util.List;

public class HotelController {
    private Hotel hotel;

    public HotelController(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getHotelName() {
        return hotel.getName();
    }

    public void addRoom(String roomNumber, String type, double price) {
        Room room = new Room(roomNumber, type, price);
        hotel.addRoom(room);
    }

    public List<Room> getRooms() {
        return hotel.getRooms();
    }
}

