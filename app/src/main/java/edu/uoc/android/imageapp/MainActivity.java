package edu.uoc.android.imageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Manager class of Main Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int RESULT_IMAGE_GALLERY = 0;

    // TAG logs

    private final String TAG = this.getClass().getSimpleName();

    // Views

    private Button mButtonOpenImage;
    private ImageView mImageView;
    private TextView mTextView;

    private ImageController imageController;

    //*****************************************************************************************************************
    // Private section
    //*****************************************************************************************************************

    /**
     * Initializes visual components
     */

    private void initializeVisualComponents()
    {
        mButtonOpenImage = (Button) findViewById(R.id.button);
        mImageView       = (ImageView) findViewById(R.id.imageView);
        mTextView        = (TextView) findViewById(R.id.textView);
    }

    //*****************************************************************************************************************
    // Protected section
    //*****************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVisualComponents();

        Log.d(TAG, "Starting activity main");

        // Set listeners

        mButtonOpenImage.setOnClickListener(this);

        // Load image, if exists.

        imageController = new ImageController(this, mImageView, mTextView);

        Log.d(TAG, "Loading image from external storage");

        imageController.loadImageFromExternalStorage();
    }

    /**
     * Gets the image from gallery application
     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case RESULT_IMAGE_GALLERY: Log.d(TAG, "Loading image from gallery");
                                           imageController.loadImage(data);
                                           break;
            }
        }
    }

    //*****************************************************************************************************************
    // Public section
    //*****************************************************************************************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.save: Log.d(TAG, "Saving image to external storage");
                            imageController.saveBitmap();
                            break;

            case R.id.delete: Log.d(TAG, "Deleting image from the screen and from external storage");
                              imageController.deleteImageFileWithConfirmation();
                              break;

            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mButtonOpenImage)
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, RESULT_IMAGE_GALLERY);
        }
    }
}
