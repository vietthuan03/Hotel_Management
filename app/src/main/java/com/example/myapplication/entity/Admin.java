package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Viet Thuan
 */
@Entity(tableName = "admin")
public class Admin {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String password;
}
