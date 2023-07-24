package com.kp.socialnetwork.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.FirebaseHelper;
import com.kp.socialnetwork.utils.CameraUtils;
import com.kp.socialnetwork.utils.GalleryUtils;
import com.kp.socialnetwork.utils.PermissionUtils;
import com.squareup.picasso.Picasso;

public class SimplePostCreate extends AppCompatActivity {

    ImageView imageView;
    Button btnSubmit;

    private CameraUtils cameraUtils;
    private GalleryUtils galleryUtils;
    private ActivityResultLauncher<Uri> cameraActivityResultLauncher;
    private ActivityResultLauncher<Intent> galleryAcitvityResultLauncher;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_post_create);

        imageView = findViewById(R.id.imageView);
        btnSubmit = findViewById(R.id.btnSubmit);

        cameraUtils = new CameraUtils(this);
        galleryUtils = new GalleryUtils();

        cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                if (cameraUtils.getCapturedImageUri() != null) {
                    imageUri = cameraUtils.getCapturedImageUri();
                    if (imageUri != null)
                     Picasso.get().load(imageUri).into(imageView);
                }
            }
        });

        FirebaseHelper.uploadImageToFirebaseCloud(imageUri, FirebaseHelper.getImageCoverStorageRef(),
                new FirebaseHelper.OnImageUploadListener() {
                    @Override
                    public void onImageUploadSuccess(String imageUrl) {
                        Log.d("TÉT CAMERA - PARSE URI TO STRING", imageUrl);
                    }

                    @Override
                    public void onImageUploadFailure(String errorMessage) {

                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

       if (requestCode == PermissionUtils.CAMERA_REQUEST_CODE) {
           if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
               cameraUtils.openCamera(cameraActivityResultLauncher);
           }
       }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}