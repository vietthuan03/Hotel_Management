package com.example.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.myapplication.dao.RoomDao;
import com.example.myapplication.database.HotelDatabase;
import com.example.myapplication.entity.Rooms;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Viet Thuan
 */
public class RoomRepository {
    private final RoomDao roomDao;
    private final HotelDatabase hotelDatabase; // Assuming HotelDatabase contains both HotelDao and RoomDao

    public RoomRepository(Application application) {
        hotelDatabase = HotelDatabase.getDatabase(application);
        roomDao = hotelDatabase.roomDao(); // Get RoomDao instance
    }


    public LiveData<List<Rooms>> getRoomByHotel(int hotelId) {
        return roomDao.getRoomByHotel(hotelId);
    }
    public LiveData<List<Rooms>> getRoomUnderPrice(double price, int hotelId) {
        return roomDao.getRoomUnderPrice(price, hotelId);
    }

    public void insertRoom(final Rooms room) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hotelDatabase.roomDao().insertRoom(room);
                } catch (Exception e) {
                    Log.e("RoomRepository", "Error inserting room", e);
                }
            }
        });
    }

    public void deleteRoom(final Rooms room) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hotelDatabase.roomDao().deleteRoom(room);
                } catch (Exception e) {
                    Log.e("RoomRepository", "Error delete room", e);
                }
            }
        });
    }

    public void updateRoom(final Rooms room) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hotelDatabase.roomDao().updateRoom(room);
                } catch (Exception e) {
                    Log.e("RoomRepository", "Error update room", e);
                }
            }
        });
    }
}
