package com.fosanzdev.listacompra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utils {

    public static Bitmap base64ToBitmap(String b64Image) {
        byte[] decodedString = Base64.decode(b64Image, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
