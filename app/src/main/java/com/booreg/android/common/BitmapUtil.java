package com.booreg.android.common;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Offers methods for managing bitmaps.
 */

public class BitmapUtil
{
    /**
     * Saves a bitmap to a file with the format an quality specified.
     */

    public static void saveBitmap(Bitmap bitmap, File file, Bitmap.CompressFormat compressFormat, int quality) throws Exception
    {
        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(compressFormat, quality, fileOutputStream);
        }
        finally
        {
            if (fileOutputStream != null) fileOutputStream.close();
        }
    }

    /**
     * Saves a bitmap to a JPG file with 100% quality.
     */

    public static void saveBitmapJPG(Bitmap bitmap, File file) throws Exception
    {
        saveBitmap(bitmap, file, Bitmap.CompressFormat.JPEG, 100);
    }
}
