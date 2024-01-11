package com.fosanzdev.listacompra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static Bitmap byteArrayToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] webpToByteArray(int resourceId, Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        bitmap.compress(Bitmap.CompressFormat.WEBP, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
