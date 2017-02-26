package com.anvob.canvasdraw.screen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.anvob.canvasdraw.R;
import com.anvob.canvasdraw.common.Slide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SlideListActivity extends AppCompatActivity implements SlideListAdapter.ItemListener {

    private final static int PICK_IMAGE = 1;
    private final static int EDIT_IMAGE = 2;
    public static final String PHOTO_MIME_TYPE = "image/*";
    private String mCurrentPhotoPath;
    SlideListAdapter mAdapter;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.slides);
        initRecyclerView(mRecyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //photoPickerIntent.setType("image/*");
                //startActivityForResult(photoPickerIntent, PICK_IMAGE);
                Uri uriWhereToStore = null;
                try {
                    uriWhereToStore = Uri.fromFile(createImageFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                pickImageIntent.setType("image/*");
                pickImageIntent.putExtra("crop", "true");
                pickImageIntent.putExtra("outputX", 200);
                pickImageIntent.putExtra("outputY", 200);
                pickImageIntent.putExtra("aspectX", 1);
                pickImageIntent.putExtra("aspectY", 1);
                pickImageIntent.putExtra("scale", true);
                pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriWhereToStore);
                pickImageIntent.putExtra("outputFormat",

                        Bitmap.CompressFormat.JPEG.toString());
                startActivityForResult(pickImageIntent, PICK_IMAGE);
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = /*"file:" + */image.getAbsolutePath();
        return image;
    }

    private void initRecyclerView(@NonNull RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        mAdapter = new SlideListAdapter(new ArrayList<>());
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show:
                startActivity(new Intent(this,MainActivity.class));
                return true;
            default:
                // Not one of ours. Perform default menu processing
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case PICK_IMAGE:
            {
                if (resultCode == RESULT_OK)
                {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        selectedImage = Bitmap.createScaledBitmap(selectedImage,mRecyclerView.getWidth(),mRecyclerView.getWidth(),true);
                        mAdapter.addElement(new Slide(selectedImage,50));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case EDIT_IMAGE:
            {
                if (resultCode == RESULT_OK)
                {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        selectedImage = Bitmap.createScaledBitmap(selectedImage,mRecyclerView.getWidth(),mRecyclerView.getWidth(),true);
                        //final Intent intent = new Intent(Intent.ACTION_EDIT);
                        //intent.setDataAndType(imageUri, PHOTO_MIME_TYPE);
                        //startActivity(Intent.createChooser(intent, getString(R.string.app_name)));
                        //imageView.setImageBitmap(selectedImage);
                        mAdapter.addElement(new Slide(selectedImage,50));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onItemClick(int position, View view) {

    }
}
