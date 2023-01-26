package com.tracking.ebridge;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.widget.ImageView;

public class ImageDisplayingActivity extends Activity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_image_displaying);

        String getimageString=getIntent().getStringExtra("image");

        byte[] data = Base64.decode(getimageString, 0);
        Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, null);

        image= (ImageView) findViewById(R.id.displayImage);

        image.setImageBitmap(b);

    }

}
