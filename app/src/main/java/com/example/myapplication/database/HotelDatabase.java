package com.example.myapplication.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.HotelDao;
import com.example.myapplication.dao.RoomDao;
import com.example.myapplication.entity.Hotel;
import com.example.myapplication.entity.Rooms;


/**
 * Created by Viet Thuan
 */
@Database(entities = {Hotel.class, Rooms.class}, version = 1, exportSchema = false)
public abstract class HotelDatabase extends RoomDatabase {
    public abstract HotelDao hotelDao();
    public abstract RoomDao roomDao();
    private static volatile HotelDatabase INSTANCE;
    public static HotelDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HotelDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HotelDatabase.class, "hotel_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
