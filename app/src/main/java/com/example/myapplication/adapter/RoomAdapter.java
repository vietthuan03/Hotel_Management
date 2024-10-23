package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication.R;
import com.example.myapplication.activity.RoomActivity;
import com.example.myapplication.entity.Rooms;

import java.util.List;

/**
 * Created by Viet Thuan
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Rooms> roomList;
    private Context context;
    private ItemClicked itemClicked;

    public RoomAdapter(ItemClicked itemClicked) {
        this.itemClicked = itemClicked;
    }


    public void setRoomList(List<Rooms> roomList) {
        this.roomList = roomList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_room, parent, false); // Assuming you have row_room.xml
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Rooms room = roomList.get(position);
        holder.roomNumberTextView.setText(String.valueOf(room.getRoomNumber()));
        holder.roomTypeTextView.setText(room.getRoomType());
        holder.priceTextView.setText(String.valueOf(room.getPrice()));
        holder.imageOptions.setOnClickListener(v -> showPopup(v, room));

    }

    public void showPopup(View view, final Rooms room) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.menu_options); // Assuming you have menu_options.xml
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (itemClicked != null) {
                int id = menuItem.getItemId();
                if (id == R.id.delete) {
                    itemClicked.deleteClicked(room);
                } else if (id == R.id.update) {
                    itemClicked.updateClicked(room);
                }
            }
            return false;
        });
        popupMenu.show();
    }

    public interface ItemClicked {
        void updateClicked(Rooms room);
        void deleteClicked(Rooms room);
        void onItemClicked(int position);
    }

    @Override
    public int getItemCount() {
        return (roomList != null) ? roomList.size() : 0;
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomNumberTextView;
        public TextView roomTypeTextView;
        public TextView priceTextView;
        public ImageView imageOptions;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNumberTextView = itemView.findViewById(R.id.roomNumberTextView);
            roomTypeTextView = itemView.findViewById(R.id.roomTypeTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            imageOptions = itemView.findViewById(R.id.menuOption);
        }
    }
}