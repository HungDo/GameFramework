package com.app.GameFramework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

/**
 * User: shubbard
 */
public class GameImageManager {
    private static HashMap<Integer, Bitmap> imageMap = null;
    private static Context managerContext = null;

    public static void initiate(Context context) {
        if (managerContext == null) {
            managerContext = context;
        }

        if (imageMap == null) {
            imageMap = new HashMap<Integer, Bitmap>();
        }
    }

    public static Bitmap getBitmap(int id) {
        if (!imageMap.containsKey(id)) {
            Bitmap bitmap = BitmapFactory.decodeStream(managerContext.getResources().openRawResource(id));

            imageMap.put(id, bitmap);
        }

        return imageMap.get(id);
    }
}
