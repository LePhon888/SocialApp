package com.kp.socialnetwork.data;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseHelper {

    private static final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://socialmediaapp-32653.appspot.com");
    private static final StorageReference storageReference;
    private static final StorageReference imageStorageReference;
    private static FirebaseDatabase firebaseDatabase;

    static {

        //Database
        firebaseDatabase = FirebaseDatabase.getInstance("https://socialmediaapp-32653-default-rtdb.asia-southeast1.firebasedatabase.app/");


        storageReference = firebaseStorage.getReference();
        imageStorageReference = storageReference.child("images");
    }

    public static DatabaseReference getDatabaseReferenceByPath(String path) {
        return firebaseDatabase.getReference(path);
    }

    public static StorageReference getImageAvatarStorageRef() {
        return imageStorageReference.child("avatar");
    }

    public static StorageReference getImageCoverStorageRef() {
        return imageStorageReference.child("cover");
    }

    public static void uploadImageToFirebaseCloud(Uri imageUri, StorageReference storageReference, OnImageUploadListener listener) {
        if (imageUri != null && storageReference != null) {
            StorageReference fileRef = storageReference.child(imageUri.getLastPathSegment());

            UploadTask uploadTask = fileRef.putFile(imageUri);

            // upload sucess
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // download sucess
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    if(listener != null) {
                        String imageUrl = uri.toString();
                        listener.onImageUploadSuccess(imageUrl);
                    }
                }).addOnFailureListener(runnable -> {
                    // download fail
                    if (listener != null)
                        listener.onImageUploadFailure(runnable.getMessage());
                });

            }).addOnFailureListener(runnable -> {
                // upload fail
                if (listener != null)
                    listener.onImageUploadFailure(runnable.getMessage());
            });

        } else {
            if (listener != null)
                listener.onImageUploadFailure("StorageReference or Uri is empty!!");
        }
    }

    public interface OnImageUploadListener {
        void onImageUploadSuccess(String imageUrl);
        void onImageUploadFailure(String errorMessage);
    }
}
