package com.renhen.optical_shop.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.renhen.optical_shop.R;
import com.renhen.optical_shop.data.OpticalShopContract;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ItemGoodsAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private int layout;
    private List<Integer> states;
    private Cursor cursor;

    public ItemGoodsAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects, Cursor cursor) {
        super(context, resource, objects);
        this.states = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView label_attribute = (TextView) view.findViewById(R.id.label_attribute);
        TextView label_value = (TextView) view.findViewById(R.id.label_value);

        Cursor new_cursor = cursor;
        new_cursor.moveToPosition(position);
        label_attribute.setText(new_cursor.getString(new_cursor.getColumnIndex("attribute")));
        label_value.setText(new_cursor.getString(new_cursor.getColumnIndex("value")));

        return view;
    }


}
