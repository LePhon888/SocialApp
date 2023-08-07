package com.kp.socialnetwork;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kp.socialnetwork.activity.CreateOrUpdatePostActivity;
import com.kp.socialnetwork.data.dao.PostDao;
import com.kp.socialnetwork.data.dao.UserDao;
import com.kp.socialnetwork.data.model.Post;
import com.kp.socialnetwork.data.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Post> postList;
    private UserDao userDao;
    //Retrive userId from PostListActivity
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

        CircleImageView avatar = convertView.findViewById(R.id.avatar);
        TextView username = convertView.findViewById(R.id.username);
        TextView content = convertView.findViewById(R.id.content);
        ImageView imagePost = convertView.findViewById(R.id.imagePost);

        //Get current user id
//        int userId = Integer.parseInt(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String userId = "1gPFtNBZLka3WJLVh04HBHprozj2";

        //Load info user
        userDao = new UserDao(context);
        userDao.open();
        User u = userDao.getUserById(userId);
        Picasso.get().load(u.getImageAvatar()).into(avatar);
        username.setText(u.getName());
        userDao.close();

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
                PostDao postDao = new PostDao(context);
                postDao.open();
                postDao.deletePost(post.getId());
                Toast.makeText(context, "This post is deleted !", Toast.LENGTH_SHORT).show();
                postDao.close();
            }

            private void onUpdateClicked(Post post) {
                Intent intent = new Intent(context, CreateOrUpdatePostActivity.class);
                intent.putExtra("postId", post.getId());
                context.startActivity(intent);
            }
        }).show();
    }
}
