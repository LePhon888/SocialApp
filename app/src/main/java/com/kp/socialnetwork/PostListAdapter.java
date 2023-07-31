package com.kp.socialnetwork;
import com.bumptech.glide.Glide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kp.socialnetwork.data.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Post> postList;

//    private OnOptionClickListener optionClickListener;

    public PostListAdapter(Context context, int layout, List<Post> postList) {
        this.context = context;
        this.layout = layout;
        this.postList = postList;
//        this.optionClickListener = optionClickListener;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        Post post = postList.get(position);

        ImageView avatar = convertView.findViewById(R.id.avatar);
        TextView name = convertView.findViewById(R.id.username);
        TextView content = convertView.findViewById(R.id.content);
        ImageView imagePost = convertView.findViewById(R.id.imagePost);

        content.setText(post.getContent());
        Picasso.get().load(post.getImage()).into(imagePost);

        ImageView optionIcon = convertView.findViewById(R.id.optionIcon);
        optionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog(post);
            }
        });

        return convertView;


    }

    private void showOptionDialog(Post post) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("").setItems(new CharSequence[]{"Edit this post", "Delete this post"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: 
                        onUpdateClicked(post);
                        break;
                    case 1:
                        onDeleteClicked(post);                        
                        break;
                }
            }

            private void onDeleteClicked(Post post) {
            }

            private void onUpdateClicked(Post post) {
            }
        }).show();
    }
}
