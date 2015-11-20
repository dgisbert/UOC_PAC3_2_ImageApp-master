package edu.uoc.android.imageapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.booreg.android.common.BitmapUtil;
import com.booreg.android.common.DialogUtil;
import com.booreg.android.common.FileUtil;
import com.booreg.android.common.ToastUtil;

import java.io.File;

/**
 * Manages loading image from storage or image gallery and creating or deleting image file on storage.
 */

public class ImageController
{
    // Folder & filename values

    private static final String FOLDER   = "UOCImageApp";
    private static final String FILENAME = "imageapp.jpg";

    private boolean imageShown = false;

    private Context   context;
    private ImageView imageView;
    private TextView  textView;

    //*****************************************************************************************************************
    // Private inner classes
    //*****************************************************************************************************************

    private class AcceptDeleteListener implements DialogInterface.OnClickListener
    {
        @Override public void onClick(DialogInterface dialog, int which)
        {
            deleteImageFile();
            imageView.setImageBitmap(null);
            textView.setVisibility(View.VISIBLE);
        }
    }

    //*****************************************************************************************************************
    // Private section
    //*****************************************************************************************************************

    /**
     * Returns a File object with the current stored image. This file may exists or not.
     */

    private File getImageFile()
    {
        return FileUtil.getExternalStorageFile(FOLDER, FILENAME);
    }

    /**
     * Deletes the current image file on folder/filename specified at class level constants FOLDER/FILENAME
     */

    private void deleteImageFile()
    {
        // Even if there is no image selected on the screen it tries to delete the file

        if (FileUtil.isExternalStorageWritable())
        {
            String fileAbsolutePath = "";

            try
            {
                File file = getImageFile();

                if (file != null)
                {
                    fileAbsolutePath = file.getAbsolutePath();

                    if (file.exists())
                    {
                        file.delete();
                        ToastUtil.showLongToast(context, R.string.TXT00005, fileAbsolutePath);
                    }

                    imageShown = false;
                }
                else ToastUtil.showLongToast(context, R.string.TXT00002);
            }
            catch (Exception e)
            {
                ToastUtil.showLongToast(context, R.string.TXT00001, fileAbsolutePath);
            }
        }
        else ToastUtil.showLongToast(context, R.string.TXT00002);
    }

    //*****************************************************************************************************************
    // Public section
    //*****************************************************************************************************************

    /**
     * Loads image
     */

    public void loadImage(Intent data)
    {
        try
        {
            if (data != null)
            {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor

                Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                String fileAbsolutePath = null;

                // Move to first row

                if (cursor != null)
                {
                    try
                    {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        fileAbsolutePath = cursor.getString(columnIndex);
                    }
                    finally
                    {
                        cursor.close();
                    }
                }

                loadImageFromFile(fileAbsolutePath);
            }
            else
            {
                textView.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e)
        {
            ToastUtil.showLongToast(context, R.string.TXT00004);
        }
    }

    /**
     * Saves the current image to a file on folder/filename specified at class level constants FOLDER/FILENAME
     */

    public void saveBitmap()
    {
        if (!imageShown) ToastUtil.showLongToast(context, R.string.TXT00010);
        else
        {
            if (FileUtil.isExternalStorageWritable())
            {
                String fileAbsolutePath = "";

                try
                {
                    File file = getImageFile();

                    if (file != null)
                    {
                        file.getParentFile().mkdirs();

                        fileAbsolutePath = file.getAbsolutePath();

                        if (file.exists()) file.delete();

                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        BitmapUtil.saveBitmapJPG(bitmap, file);
                        ToastUtil.showLongToast(context, R.string.TXT00003, fileAbsolutePath);
                    }
                    else ToastUtil.showLongToast(context, R.string.TXT00002);
                }
                catch (Exception e)
                {
                    ToastUtil.showLongToast(context, R.string.TXT00001, fileAbsolutePath);
                }
            }
            else ToastUtil.showLongToast(context, R.string.TXT00002);
        }
    }

    /**
     * Deletes the current image file on external storage asking confirmation to the user
     */

    public void deleteImageFileWithConfirmation()
    {
        if (!imageShown) ToastUtil.showLongToast(context, R.string.TXT00010);
        else DialogUtil.showOKCancelDialog(context, R.string.TXT00009, R.string.TXT00006, R.string.TXT00007, R.string.TXT00008, new AcceptDeleteListener());
    }

    /**
     * Loads an image from the specified file and hides text on view.
     */

    public void loadImageFromFile(String fileAbsolutePath)
    {
        if (fileAbsolutePath != null)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(fileAbsolutePath);

            imageView.setImageBitmap(bitmap);
            textView.setVisibility(View.INVISIBLE);
            imageShown = true;
        }
        else
        {
            textView.setVisibility(View.VISIBLE);
            imageShown = false;
        }
    }

    /**
     * Loads the image for the first time the application runs, if the file exists on the external storage .
     */

    public void loadImageFromExternalStorage()
    {
        String fileAbsolutePath = null;

        File file = getImageFile();

        if (file != null && file.exists()) fileAbsolutePath = file.getAbsolutePath();

        loadImageFromFile(fileAbsolutePath);
    }

    //*****************************************************************************************************************
    // Constructors
    //*****************************************************************************************************************

    /**
     * Creates the object with the given information
     */

    public ImageController(Context context, ImageView imageView, TextView textView)
    {
        this.context   = context;
        this.imageView = imageView;
        this.textView  = textView;
    }
}
