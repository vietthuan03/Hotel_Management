package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.HotelActivity;
import com.example.myapplication.activity.RoomActivity;
import com.example.myapplication.entity.Hotel;

import java.util.List;

/**
 * Created by Viet Thuan
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotelList;
    private Context context;
    private ItemClicked itemClicked; // Optional click listener


    public HotelAdapter(Context context, ItemClicked itemClicked) {
        this.context = context;
        this.itemClicked = itemClicked;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
        notifyDataSetChanged(); // Can be optimized for specific changes
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hotel, parent, false);
        return new HotelAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.hotelNameTextView.setText(hotel.getName());
        holder.hotelRatingBar.setRating(hotel.getStarRating()); // Assuming this is the correct method
        holder.hotelDescriptionTextView.setText(hotel.getDescription());
        holder.imageOptions.setOnClickListener(v -> showPopup(v, hotel));
        // Set click listener on the hotelLayout
        holder.hotelLayout.setOnClickListener(v -> {
            // Notify the activity about the click
            int hotelId = hotelList.get(position).getId();
            Intent intent = new Intent(context, RoomActivity.class);
            intent.putExtra("HOTEL_ID", hotelId);
            context.startActivity(intent);
        });
    }

    public void showPopup(View view, final Hotel hotel) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.menu_options);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (itemClicked != null) {
                int id = menuItem.getItemId();
                if (id == R.id.delete) {
                    itemClicked.deleteClicked(hotel);
//                    Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show();
                } else if (id == R.id.update) {
                    itemClicked.updateClicked(hotel);
//                    Toast.makeText(context, "Update", Toast.LENGTH_LONG).show();

                }
            }
            return false;
        });
        popupMenu.show();
    }

    public interface ItemClicked {
        void updateClicked(Hotel hotel);
        void deleteClicked(Hotel hotel);
        void onItemClicked(int position);
    }

    @Override
    public int getItemCount() {
        return (hotelList != null) ? hotelList.size() : 0;
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        public TextView hotelNameTextView;
        public RatingBar hotelRatingBar;
        public TextView hotelDescriptionTextView;
        public ImageView imageOptions;
        public LinearLayout hotelLayout;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTextView = itemView.findViewById(R.id.hotelNameTextView);
            hotelRatingBar = itemView.findViewById(R.id.hotelRatingBar);
            hotelRatingBar.setIsIndicator(true);
            hotelDescriptionTextView = itemView.findViewById(R.id.hotelDescriptionTextView);
            imageOptions = itemView.findViewById(R.id.menuOption);
            hotelLayout = itemView.findViewById(R.id.hotelLayout);
        }
    }
}
