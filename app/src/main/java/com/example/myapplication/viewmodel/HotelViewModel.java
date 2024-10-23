package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.entity.Hotel;
import com.example.myapplication.repository.HotelRepository;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Viet Thuan
 */
public class HotelViewModel extends AndroidViewModel {
    private final HotelRepository hotelRepository;
    private final LiveData<List<Hotel>> hotelList;

    public HotelViewModel(@NonNull Application application) {
        super(application);
        hotelRepository = new HotelRepository(application);
        hotelList = hotelRepository.getAllHotels();
    }

    public LiveData<List<Hotel>> getAllHotels() {
        return hotelRepository.getAllHotels();
    }
    // Method to insert a hotel (on a background thread)
    public void insertHotel(Hotel hotel) {
        hotelRepository.insertHotel(hotel);
    }

    // Method to update a hotel (on a background thread)
    public void updateHotel(Hotel hotel) {
        hotelRepository.updateHotel(hotel);
    }

    // Method to delete a hotel (on a background thread)
    public void deleteHotel(Hotel hotel) {
        hotelRepository.deleteHotel(hotel);
    }
}
