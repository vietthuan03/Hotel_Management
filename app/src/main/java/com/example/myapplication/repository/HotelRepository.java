package com.example.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.myapplication.dao.HotelDao;
import com.example.myapplication.database.HotelDatabase;
import com.example.myapplication.entity.Hotel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Viet Thuan
 */
public class HotelRepository {
    private final HotelDao hotelDao;
    private final LiveData<List<Hotel>> hotelList;
    private final HotelDatabase hotelDatabase;

    public HotelRepository(Application application) {
        hotelDatabase = HotelDatabase.getDatabase(application);
        hotelDao = hotelDatabase.hotelDao();
        hotelList = hotelDao.getAllHotels();
    }

    public LiveData<List<Hotel>> getAllHotels() {
        return hotelDao.getAllHotels();
    }


    public void insertHotel(final Hotel hotel) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hotelDatabase.hotelDao().insertHotel(hotel);
                } catch (Exception e) {
                    Log.e("RoomRepository", "Error inserting room", e);
                }
            }
        });
    }
    public void deleteHotel(final Hotel hotel) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hotelDatabase.hotelDao().deleteHotel(hotel);
                } catch (Exception e) {
                    Log.e("RoomRepository", "Error delete room", e);
                }
            }
        });
    }
    public void updateHotel(final Hotel hotel) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    hotelDatabase.hotelDao().updateHotel(hotel);
                } catch (Exception e) {
                    Log.e("RoomRepository", "Error inserting room", e);
                }
            }
        });
    }
}

