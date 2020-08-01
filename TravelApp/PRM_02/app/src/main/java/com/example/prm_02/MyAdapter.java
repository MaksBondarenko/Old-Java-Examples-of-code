package com.example.prm_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<Photo> photoList = new ArrayList<Photo>();

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public int getCount() {
        if(photoList != null)
            return photoList.size();
        return 0;
    }

    public void addItem(Photo photo) {
        photoList.add(photo);
    }

    @Override
    public Photo getItem(int position) {
        if(photoList != null)
            return photoList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(photoList != null)
            return photoList.get(position).hashCode();
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Photo photo = getItem(position);
        imageView.setImageBitmap(photo.getBitmap());
        return imageView;
    }
}
