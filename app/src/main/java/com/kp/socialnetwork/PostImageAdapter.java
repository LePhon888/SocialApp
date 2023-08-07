package com.kp.socialnetwork;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PostImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> imagePaths;

    public PostImageAdapter(Context context, List<String> imagePaths) {
//        super(context, R.layout.grid_item_layout, imagePaths);
        this.context = context;
        this.setImagePaths(imagePaths);
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
        Glide.with(context).load(getImagePaths().get(position)).placeholder(R.color.purple_200).into(imageView);
//        Picasso.get().load(getImagePaths().get(position)).into(imageView);

        return imageView;
    }

    @Override
    public int getCount() {
        return getImagePaths().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
        this.notifyDataSetChanged();
    }
}
