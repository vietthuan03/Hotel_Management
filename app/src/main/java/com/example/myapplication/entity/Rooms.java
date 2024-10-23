package com.example.myapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_table",
        foreignKeys = @ForeignKey(
                entity = Hotel.class,
                parentColumns = "id",
                childColumns = "hotelId",
                onDelete = ForeignKey.CASCADE
        ))
public class Rooms {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "hotelId")
    private int hotelId; // Foreign key reference to Hotel

    @ColumnInfo(name = "roomNumber")
    private String roomNumber;

    @ColumnInfo(name = "roomType")
    private String roomType;

    @ColumnInfo(name = "price")
    private double price;

    // Constructor (for creating new rooms)
    public Rooms(int hotelId) {
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}