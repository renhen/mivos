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

public class GoodsAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private int layout;
    private List<Integer> states;
    private Cursor cursor;
    private AssetManager manager;

    public GoodsAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects, Cursor cursor, AssetManager manager) {
        super(context, resource, objects);
        this.states = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
        this.manager = manager;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView label_id = (TextView) view.findViewById(R.id.label_idGoods);
        TextView label_title = (TextView) view.findViewById(R.id.label_titleGoods);
        TextView label_description = (TextView) view.findViewById(R.id.label_descriptionGoods);
        ImageView view_photo = (ImageView) view.findViewById(R.id.imageView_Goods);

        Cursor new_cursor = cursor;
        new_cursor.moveToPosition(position);
        loadImage(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.GoodsEntry.COLUMN_PHOTO_PATH)),view_photo);
        label_title.setText(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.GoodsEntry.COLUMN_TITLE)));
        label_description.setText(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.GoodsEntry.COLUMN_DESCRIPTION)));
        label_id.setText(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.GoodsEntry._ID)));

        view.setId(Integer.parseInt(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.GoodsEntry._ID))));
        return view;
    }

    private void loadImage(String path, ImageView view){
        InputStream inputStream = null;
        try{
            inputStream = manager.open(path);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            view.setImageDrawable(drawable);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
