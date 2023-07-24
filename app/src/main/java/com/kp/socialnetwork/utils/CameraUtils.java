package com.kp.socialnetwork.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;

public class CameraUtils {

    private ActivityResultLauncher<Uri> cameraActivityResultLauncher;
    private Uri imageUri;
    private Context context;

    public CameraUtils(Context context) {
        this.context = context;
    }

    public void openCamera(ActivityResultLauncher<Uri> launcher) {
        cameraActivityResultLauncher = launcher;

        // Provide metadata for the image
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "My Picture");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "My Description");

        // Insert image to the media store
        imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        // Create an intent to open the camera app
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        // Launch the camera activity with the specified launcher
        cameraActivityResultLauncher.launch(imageUri);
    }

    // Method to get the captured image's URI
    public Uri getCapturedImageUri() {
        return imageUri;
    }

}
