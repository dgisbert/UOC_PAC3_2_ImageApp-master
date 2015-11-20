package com.booreg.android.common;

import android.os.Environment;

import java.io.File;

/**
 * Offers methods for saving and loading files.
 */

public class FileUtil
{
    /**
     * Checks if the application has external storage available and readable.
     */

    public static boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();

        return (state != null) && (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    /**
     * Checks if the application has external storage available and readable/writable.
     */

    public static boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();

        return (state != null) && Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Returns the object file representing the specified folder on external storage.
     */

    public static File getExternalStorageFolder(String folder)
    {
        File result = null;

        File root = Environment.getExternalStorageDirectory();

        if (root != null) result = new File(root, folder);

        return result;
    }

    /**
     * Returns the object file representing the specified folder/file on external storage.
     */

    public static File getExternalStorageFile(String folder, String filename)
    {
        File result = null;

        File destinationFolder = getExternalStorageFolder(folder);

        if (destinationFolder != null) result = new File(destinationFolder, filename);

        return result;
    }
}
