package com.example.myapplication.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Viet Thuan
 */
@Entity(tableName = "hotel_table")
public class Hotel {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "startRating")
    private float starRating;
    @ColumnInfo(name = "description")
    private String description;

    public Hotel(int id, String name, float startRating, String description) {
        this.id = id;
        this.name = name;
        this.starRating = startRating;
        this.description = description;
    }

    public Hotel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(float startRating) {
        this.starRating = startRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
