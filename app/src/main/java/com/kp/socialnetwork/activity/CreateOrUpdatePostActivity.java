package com.kp.socialnetwork.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kp.socialnetwork.MyApp;
import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.dao.PostDao;
import com.kp.socialnetwork.data.dao.UserDao;
import com.kp.socialnetwork.data.model.Post;
import com.kp.socialnetwork.data.model.User;
import com.kp.socialnetwork.utils.CameraUtils;
import com.kp.socialnetwork.utils.GalleryUtils;
import com.kp.socialnetwork.utils.PermissionUtils;
import com.kp.socialnetwork.utils.ImageSizeValidationUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class CreateOrUpdatePostActivity extends AppCompatActivity {

    private CameraUtils cameraUtils;
    private ActivityResultLauncher<Uri> cameraActivityResultLauncher;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;
    private GalleryUtils galleryUtils;
    private ImageView imagePost;
    private EditText content;
    private CircleImageView avatar;
    private TextView username;

    private Button publishButton;
    private UserDao userDao;
    private PostDao postDao;
    private Uri imagePostUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        cameraUtils = new CameraUtils(this);
        galleryUtils = new GalleryUtils();

        content = findViewById(R.id.content);
        imagePost = findViewById(R.id.imagePost);
        publishButton = findViewById(R.id.publishButton);
        avatar = findViewById(R.id.avatar);
        username = findViewById(R.id.username); // Add this line

        //Get current user id
//        int userId = Integer.parseInt(FirebaseAuth.getInstance().getCurrentUser().getUid());
        int userId = 1;

        //Load info user
        userDao = new UserDao(CreateOrUpdatePostActivity.this);
        userDao.open();
        User u = userDao.getUserById("1gPFtNBZLka3WJLVh04HBHprozj2");
        Picasso.get().load(u.getImageAvatar()).into(avatar);
        username.setText(u.getName());
        userDao.close();


        //Get postId for update post
        int postId = getIntent().getIntExtra("postId", -1);
        //Show post
        if (postId != -1) {
            PostDao postDao = new PostDao(CreateOrUpdatePostActivity.this);
            postDao.open();
            Post currentPost = postDao.getPost(postId);
            postDao.close();

            Picasso.get().load(currentPost.getImage()).into(imagePost);
            content.setText(currentPost.getContent());
        }
        else {

        }



        //Handle publish post (Add or Update)
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Post post = new Post();
                post.setId(postId);
                post.setContent(String.valueOf(content.getText()));
                post.setImage(imagePostUri.toString());
                post.setUser(u);

                //Open PostDao
                postDao = new PostDao(CreateOrUpdatePostActivity.this);
                postDao.open();

                //Check postId to update or create post
                if (postId != -1) {
                    postDao.updatePost(post);
                }
                else {
                    postDao.createPost(post);
                }
                postDao.close();
                onBackPressed();
                finish();
            }
        });

        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cameraUtils.openCamera(cameraActivityResultLauncher);
                galleryUtils.openGallery(galleryActivityResultLauncher);
            }
        });


        // Handle the image pick result
        cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                if (cameraUtils.getCapturedImageUri() != null) {
                    Uri imageUri = cameraUtils.getCapturedImageUri();
                    if (imageUri != null) handleImageResult(imageUri);
                }
            }
        });

        galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) handleImageResult(imageUri);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case PermissionUtils.CAMERA_REQUEST_CODE -> {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    cameraUtils.openCamera(cameraActivityResultLauncher);
                } else {
                    //Permission denied
                    Toast.makeText(this, "Please enable camera and storage permissions", Toast.LENGTH_SHORT).show();
                }
            }

            case PermissionUtils.STORAGE_REQUEST_CODE -> {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted
                    galleryUtils.openGallery(galleryActivityResultLauncher);
                } else {
                    //Permission denied
                    Toast.makeText(this, "Please enable storage permissions", Toast.LENGTH_SHORT).show();
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void showImagePickDialog() {
        // Check if camera and storage permissions are granted
        boolean hasCameraPermission = PermissionUtils.checkCameraPermission(this);
        boolean hasStoragePermission = PermissionUtils.checkStoragePermission(this);

        // Build dialog for choosing camera or gallery
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your image");

        // Set the dialog items based on permissions
        String[] dialogItems;
        if (hasCameraPermission && hasStoragePermission) {
            dialogItems = new String[]{"Camera", "Gallery"};
        } else if (hasCameraPermission) {
            dialogItems = new String[]{"Camera"};
        } else if (hasStoragePermission) {
            dialogItems = new String[]{"Gallery"};
        } else {
            // Both permissions are missing
            PermissionUtils.requestCameraPermission(this);
            PermissionUtils.requestStoragePermission(this);
            return;
        }

        builder.setItems(dialogItems, (dialogInterface, i) -> {
            String selectedItem = dialogItems[i];
            if (selectedItem.equals("Camera")) {
                if (hasCameraPermission) {
                    cameraUtils.openCamera(cameraActivityResultLauncher);
                } else {
                    PermissionUtils.requestCameraPermission(this);
                }
            } else if (selectedItem.equals("Gallery")) {
                if (hasStoragePermission) {
                    galleryUtils.openGallery(galleryActivityResultLauncher);
                } else {
                    PermissionUtils.requestStoragePermission(this);
                }
            }
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void handleImageResult(Uri imageUri) {
            if (ImageSizeValidationUtils.checkImageSize(this, imageUri, ImageSizeValidationUtils.ImageType.COVER)) {
                imagePostUri = imageUri;
                Picasso.get().load(imageUri).into(imagePost); // Set the selected image to the imagePost
            } else {
                Toast.makeText(this, "Image too small, please choose a larger image!!", Toast.LENGTH_SHORT).show();
            }
    }
}

