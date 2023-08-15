package com.er.aa.menu;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.er.aa.R;
import com.er.aa.smsreceiver.SmsActivity;
import com.er.aa.smsreceiver.SmsReceiver;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Item> itemList;
    private Context context;

    public MyAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void updateData(List<Item> newItemList) {
        itemList = newItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemImageView.setImageResource(item.getImageResId());
        holder.itemTextView.setText(item.getTitle());

        // Set a click listener for the icon
        holder.itemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();

                if (clickedPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                // Perform actions based on the clicked icon's position
                if (clickedPosition == 0) {
                    String te = "yay";
                    Toast.makeText(v.getContext(), "Clicked on the first icon", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), SmsActivity.class);
                    v.getContext().startActivity(intent);
                    // Icon at position 0 was clicked
                    // Perform action for the first icon
                } else if (clickedPosition == 1) {
                    Toast.makeText(v.getContext(), "Clicked on the 2 icon", Toast.LENGTH_SHORT).show();
                    // Icon at position 1 was clicked
                    // Perform action for the second icon
                } else if (clickedPosition == 2) {
                    Toast.makeText(v.getContext(), "Clicked on the 3 icon", Toast.LENGTH_SHORT).show();
                    // Icon at position 2 was clicked
                    // Perform action for the third icon
                } else if (clickedPosition == 3) {
                    Toast.makeText(v.getContext(), "Clicked on the 4 icon", Toast.LENGTH_SHORT).show();
                    // Icon at position 3 was clicked
                    // Perform action for the fourth icon
                }
                // Add more conditions for other icons as needed
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemTextView = itemView.findViewById(R.id.itemTextView);
        }
    }
}

