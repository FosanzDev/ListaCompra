package com.fosanzdev.listacompra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static Bitmap byteArrayToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Bitmap webpToBitmap(int resourceId, Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        bitmap.compress(Bitmap.CompressFormat.WEBP, 1, byteArrayOutputStream);
        return bitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        byte[] image = null;
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 1, byteArrayOutputStream);
            image = byteArrayOutputStream.toByteArray();
        }
        return image;
    }
}
