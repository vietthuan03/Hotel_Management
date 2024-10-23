package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HotelAdapter;
import com.example.myapplication.entity.Hotel;
import com.example.myapplication.viewmodel.HotelViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by Viet Thuan
 */
public class HotelActivity extends AppCompatActivity implements HotelAdapter.ItemClicked {
    HotelViewModel hotelViewModel;
    HotelAdapter hotelAdapter;
    RecyclerView recyclerView;
    FloatingActionButton addHotelFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        // Initialize ViewModel and Adapter
        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        hotelAdapter = new HotelAdapter(this, this); // Provide proper arguments
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.hotelRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(hotelAdapter); // Set adapter once
        // Observe changes in hotel data
        hotelViewModel.getAllHotels().observe(this, hotel -> {
            if (hotel != null && !hotel.isEmpty()) {
                hotelAdapter.setHotelList(hotel); // Only update the data in the adapter
                recyclerView.setAdapter(hotelAdapter);
            }
        });
        // FloatingActionButton to add hotel
        addHotelFab = findViewById(R.id.addHotel);
        addHotelFab.setOnClickListener(view -> addHotel(this));
    }

    public void addHotel(Context context) {
        // Use context to avoid null reference issues
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view1 = getLayoutInflater().inflate(R.layout.activity_add_hotel, null);

        Button addHotelButton = view1.findViewById(R.id.btn_add_hotel);
        // Show the dialog
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hotel newHotel = new Hotel();

                // Get the inputs
                EditText hotelNameEditText = view1.findViewById(R.id.et_hotel_name);
                RatingBar starRatingBar = view1.findViewById(R.id.hotelRatingBar);
                EditText descriptionEditText = view1.findViewById(R.id.et_description);

                // Validate input
                if (hotelNameEditText.getText().toString().trim().isEmpty()) {
                    hotelNameEditText.setError("Tên không được để trống");
                    return;
                }
                if (descriptionEditText.getText().toString().trim().isEmpty()) {
                    descriptionEditText.setError("Mô tả không được để trống");
                    return;
                }

                // Set the values to the Hotel object
                newHotel.setName(hotelNameEditText.getText().toString().trim());
                newHotel.setStarRating(starRatingBar.getRating());
                newHotel.setDescription(descriptionEditText.getText().toString().trim());

                Toast.makeText(context, "Thêm khách sạn thành công!", Toast.LENGTH_SHORT).show();
                // Insert the hotel via the ViewModel
                hotelViewModel.insertHotel(newHotel);
                alertDialog.dismiss();
            }
        });
    }

    public void updateHotel(Hotel hotel) {
        // Similar to addHotel(), but pre-fill the existing values
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view1 = getLayoutInflater().inflate(R.layout.activity_add_hotel, null);

        EditText etHotelName = view1.findViewById(R.id.et_hotel_name);
        RatingBar ratingBar = view1.findViewById(R.id.hotelRatingBar);
        EditText etDescription = view1.findViewById(R.id.et_description);

        // Pre-fill the hotel data
        etHotelName.setText(hotel.getName());
        ratingBar.setRating(hotel.getStarRating());
        etDescription.setText(hotel.getDescription());

        TextView tvTitle = view1.findViewById(R.id.tv_title);
        tvTitle.setText("Chỉnh sửa khách sạn");
        // show dialog
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Set the click listener for the update button
        Button updateHotelButton = view1.findViewById(R.id.btn_add_hotel);
        updateHotelButton.setText("Chỉnh sửa"); // Change button text to "Update"
        updateHotelButton.setOnClickListener(v -> {
            if (etHotelName.getText().toString().trim().isEmpty()) {
                etHotelName.setError("Tên không được để trống");
                return;
            }
            if (etDescription.getText().toString().trim().isEmpty()) {
                etDescription.setError("Mô tả không được để trống");
                return;
            }

            // Update the hotel object
            hotel.setName(etHotelName.getText().toString().trim());
            hotel.setStarRating(ratingBar.getRating());
            hotel.setDescription(etDescription.getText().toString().trim());

            // Update hotel via ViewModel
            hotelViewModel.updateHotel(hotel);
            Toast.makeText(this, "Chỉnh sửa khách sạn thành công!", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });


    }

    @Override
    public void updateClicked(Hotel hotel) {
        Log.d("HotelActivity", "Updating hotel: " + hotel.getName());
        updateHotel(hotel);
    }

    @Override
    public void deleteClicked(Hotel hotel) {
        Toast.makeText(this, "Xóa khách sạn thành công!", Toast.LENGTH_SHORT).show();
        hotelViewModel.deleteHotel(hotel);
    }

    @Override
    public void onItemClicked(int position) {
        // Start RoomActivity when an item is clicked
        Intent intent = new Intent(this, RoomActivity.class);
        startActivity(intent);
    }
}
