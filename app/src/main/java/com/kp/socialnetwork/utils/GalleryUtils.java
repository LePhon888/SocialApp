package com.kp.socialnetwork.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class GalleryUtils {

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher;

    // Method to open the gallery and select an image
    public void openGallery(ActivityResultLauncher<Intent> launcher) {
        galleryActivityResultLauncher = launcher;

        // Create an intent to open the gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        // Launch the gallery activity with the specified launcher
        galleryActivityResultLauncher.launch(galleryIntent);
    }

    // Method to handle the gallery result and get the selected image's URI
    public Uri getImageUri(Intent data) {
        return data.getData();
    }
}
