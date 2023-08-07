package com.kp.socialnetwork.utils;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageSizeValidationUtils {
    private static final int COVER_MIN_WIDTH = 400;
    private static final int COVER_MIN_HEIGHT = 200;

    private static final int AVATAR_MIN_WIDTH = 100;
    private static final int AVATAR_MIN_HEIGHT = 100;

    public enum ImageType {

        AVATAR(AVATAR_MIN_WIDTH, AVATAR_MIN_HEIGHT),
        COVER(COVER_MIN_WIDTH, COVER_MIN_HEIGHT);

        private int minWidth;
        private int minHeight;
        ImageType(int minWidth, int  minHeight) {
            this.minWidth = minWidth;
            this.minHeight = minHeight;
        }

        public int getMinWidth() {
            return minWidth;
        }

        public int minHeight() {
            return minHeight;
        }
    }

    public static boolean checkImageSize(Context context, Uri uri, ImageType imageType) {
        if (imageType != null) {
            int width = 0, height = 0;
            ContentResolver contentResolver = context.getContentResolver();
            String[] projection = {MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT};
            Cursor cursor = contentResolver.query(uri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                width = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH));
                height = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT));
                cursor.close();
            }

            return width >= imageType.getMinWidth() && height >= imageType.minHeight();
        }
        return false;
    }

}