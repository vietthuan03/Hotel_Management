package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.Rooms;

import java.util.List;

/**
 * Created by Viet Thuan
 */
@Dao
public interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoom(Rooms room);

    @Query("select * from room_table where hotelId=:hotelId")
    LiveData<List<Rooms>> getRoomByHotel(int hotelId);

    @Query("select * from room_table where price < :price and hotelId = :hotelId")
    LiveData<List<Rooms>> getRoomUnderPrice(double price, int hotelId);

    @Update
    void updateRoom(Rooms room);
    @Delete
    void deleteRoom(Rooms room);
}
