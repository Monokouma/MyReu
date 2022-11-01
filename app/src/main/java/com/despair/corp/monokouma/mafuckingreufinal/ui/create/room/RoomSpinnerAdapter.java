package com.despair.corp.monokouma.mafuckingreufinal.ui.create.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.despair.corp.monokouma.mafuckingreufinal.R;
import com.despair.corp.monokouma.mafuckingreufinal.data.model.Room;
import com.google.android.material.imageview.ShapeableImageView;

public class RoomSpinnerAdapter extends ArrayAdapter<Room> {
    public RoomSpinnerAdapter(@NonNull Context context, Room[] rooms) {
        super(context, R.layout.room_spinner_item, rooms);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    public View getCustomView(int position, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.room_spinner_item, parent, false);

        ShapeableImageView icon = row.findViewById(R.id.create_meeting_city_image);
        TextView label = row.findViewById(R.id.create_meeting_city_name);

        Room room = getItem(position);

        assert room != null;

        icon.setBackgroundResource(room.getColorRes());
        label.setText(room.getStringResName());
        icon.setImageDrawable(AppCompatResources.getDrawable(parent.getContext(), room.getDrawableResIcon()));

        return row;
    }
}
