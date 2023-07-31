package com.kp.socialnetwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PostImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> imagePaths;

    public PostImageAdapter(Context context, List<String> imagePaths) {
//        super(context, R.layout.grid_item_layout, imagePaths);
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.grid_item_layout, null);
//        }
//
//        ImageView imageView = convertView.findViewById(R.id.gridImageView);
//        String index = imagePaths.get(position);
//        // Load image using Picasso library
//        Picasso.get().load(index).into(imageView);
//        return convertView;
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350,
                    350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.get().load(imagePaths.get(position)).into(imageView);

        return imageView;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
