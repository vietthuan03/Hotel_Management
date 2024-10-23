package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.myapplication.entity.Hotel;
import com.example.myapplication.entity.Rooms;
import com.example.myapplication.repository.HotelRepository;
import com.example.myapplication.repository.RoomRepository;

import java.util.List;

/**
 * Created by Viet Thuan
 */
public class RoomViewModel extends AndroidViewModel {
    private final RoomRepository roomRepository;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);

    }

    public LiveData<List<Rooms>> getRoomByHotelId(int hotelId) {
        return roomRepository.getRoomByHotel(hotelId);
    }

    public LiveData<List<Rooms>> getRoomUnderPrice(double price, int hotelId) {
        return roomRepository.getRoomUnderPrice(price, hotelId);
    }
    public void insertRoom(Rooms room) {
        roomRepository.insertRoom(room);
    }

    // Method to update a hotel (on a background thread)
    public void updateRoom(Rooms room) {
        roomRepository.updateRoom(room);
    }

    // Method to delete a hotel (on a background thread)
    public void deleteRoom(Rooms room) {
        roomRepository.deleteRoom(room);
    }
}
