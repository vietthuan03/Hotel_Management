package com.example.myapplication.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Hotel;

import java.util.List;

/**
 * Created by Viet Thuan
 */
@Dao
public interface HotelDao {
    // Insert a new hotel
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHotel(Hotel hotel);

    // Retrieve all hotels
    @Query("SELECT * FROM hotel_table")
    LiveData<List<Hotel>> getAllHotels();

    // Update an existing hotel
    @Update
    void updateHotel(Hotel hotel);

    // Delete a specific hotel
    @Delete
    void deleteHotel(Hotel hotel);
}
