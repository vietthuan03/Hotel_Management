package com.example.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.myapplication.R;
import com.example.myapplication.adapter.RoomAdapter;
import com.example.myapplication.entity.Rooms;
import com.example.myapplication.viewmodel.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by Viet Thuan
 */
public class RoomActivity extends AppCompatActivity implements RoomAdapter.ItemClicked {
    RoomViewModel roomViewModel;
    RoomAdapter roomAdapter;
    RecyclerView recyclerView;
    FloatingActionButton addRoomFab;
    private int hotelId;
    private AutoCompleteTextView edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list); // Set the content view

        // Initialize ViewModel and Adapter
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        roomAdapter = new RoomAdapter(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.roomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(roomAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        hotelId = getIntent().getIntExtra("HOTEL_ID", -1);
        roomViewModel.getRoomByHotelId(hotelId).observe(this, rooms -> {
            roomAdapter.setRoomList(rooms);
        });

        // FloatingActionButton to add room
        addRoomFab = findViewById(R.id.addRoom);
        addRoomFab.setOnClickListener(view -> addRoom(this));

        edtSearch = findViewById(R.id.edt_search);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used in this case
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                if (!searchText.isEmpty()) {
                    try {
                        double price = Double.parseDouble(searchText);
                        roomViewModel.getRoomUnderPrice(price, hotelId).observe(RoomActivity.this, rooms -> {
                            roomAdapter.setRoomList(rooms);
                        });
                    } catch (NumberFormatException e) {
                        // Handle invalid input (e.g., show an error message)
                        Toast.makeText(RoomActivity.this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If search text is empty, show all rooms (or handle as needed)
                    roomViewModel.getRoomByHotelId(hotelId).observe(RoomActivity.this, rooms -> {
                        roomAdapter.setRoomList(rooms);
                    });
                }
            }
        });
    }

    private void addRoom(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view1 = getLayoutInflater().inflate(R.layout.activity_add_room, null);
        Button addRoomButton = view1.findViewById(R.id.btn_add_room);
        // Show the dialog
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rooms newRoom = new Rooms(hotelId);
                // Get the inputs
                EditText roomNumberEditText = view1.findViewById(R.id.et_room_number);
                EditText roomTypeEditText = view1.findViewById(R.id.et_room_type);
                EditText priceEditText = view1.findViewById(R.id.et_price);
                if (roomNumberEditText.getText().toString().trim().isEmpty()) {
                    roomNumberEditText.setError("Số phòng không được để trống");
                    return;
                }
                if (roomTypeEditText.getText().toString().trim().isEmpty()) {
                    roomTypeEditText.setError("Loại phòng không được để trống");
                    return;
                }
                if (priceEditText.getText().toString().trim().isEmpty()) {
                    priceEditText.setError("Giá không được để trống");
                    return;
                }
                newRoom.setRoomNumber(roomNumberEditText.getText().toString().trim());
                newRoom.setRoomType(roomTypeEditText.getText().toString().trim());
                newRoom.setPrice(Double.parseDouble(priceEditText.getText().toString().trim()));
                Toast.makeText(context, "Thêm phòng thành công!", Toast.LENGTH_SHORT).show();
                roomViewModel.insertRoom(newRoom);
                alertDialog.dismiss();
            }
        });
    }
    public void updateRoom(Rooms room) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view1 = getLayoutInflater().inflate(R.layout.activity_add_room, null); // Inflate room layout

        EditText etRoomNumber = view1.findViewById(R.id.et_room_number);
        EditText etRoomType = view1.findViewById(R.id.et_room_type);
        EditText etPrice = view1.findViewById(R.id.et_price);

        // Pre-fill the room data
        etRoomNumber.setText(String.valueOf(room.getRoomNumber()));
        etRoomType.setText(room.getRoomType());
        etPrice.setText(String.valueOf(room.getPrice()));

        TextView tvTitle = view1.findViewById(R.id.tv_title);
        tvTitle.setText("Chỉnh sửa phòng");

        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button updateRoomButton = view1.findViewById(R.id.btn_add_room); // Update button ID
        updateRoomButton.setText("Chỉnh sửa");
        updateRoomButton.setOnClickListener(v -> {

            if (etRoomNumber.getText().toString().trim().isEmpty()) {
                etRoomNumber.setError("Số phòng không được để trống");
                return;
            }
            if (etRoomType.getText().toString().trim().isEmpty()) {
                etRoomType.setError("Loại phòng không được để trống");
                return;
            }
            if (etPrice.getText().toString().trim().isEmpty()) {
                etPrice.setError("Giá không được để trống");
                return;
            }
            room.setHotelId(hotelId);
            // Update the room object
            room.setRoomNumber(etRoomNumber.getText().toString().trim());
            room.setRoomType(etRoomType.getText().toString().trim());
            room.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));

            // elUpdate room via ViewMod
            roomViewModel.updateRoom(room); // Assuming you have a RoomViewModel
            Toast.makeText(this, "Chỉnh sửa phòng thành công!", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });
    }
    @Override
    public void updateClicked(Rooms room) {
        updateRoom(room);
    }

    @Override
    public void deleteClicked(Rooms room) {
        Toast.makeText(this, "Xóa phòng thành công!", Toast.LENGTH_SHORT).show();
        roomViewModel.deleteRoom(room);
    }

    @Override
    public void onItemClicked(int position) {

    }
}
