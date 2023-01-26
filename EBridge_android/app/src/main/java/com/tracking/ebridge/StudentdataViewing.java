package com.tracking.ebridge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class StudentdataViewing extends Activity {

    TextView dvusername,dvsubject,dvdesc,dvtime;
    ImageView dvimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdata_viewing);

        int dataInt=getIntent().getIntExtra("viewdata",0);
        final int positionInt=getIntent().getIntExtra("position",0);

        dvusername= (TextView) findViewById(R.id.username);
        dvtime= (TextView) findViewById(R.id.time);
        dvsubject= (TextView) findViewById(R.id.dataviewingsubject);
        dvdesc= (TextView) findViewById(R.id.dataviewingdesc);
        dvimage= (ImageView) findViewById(R.id.dataviewingimage);
        dvusername.setVisibility(View.INVISIBLE);
        dvtime.setVisibility(View.INVISIBLE);
        if(dataInt==1)
        {

            dvsubject.setText("Subject:   " + Studentdatalist.subjectArray.get(positionInt).toString());
            dvdesc.setText("Description:  "+Studentdatalist.descritionArray.get(positionInt).toString());

            byte[] data = Base64.decode(Studentdatalist.imageArray.get(positionInt).toString(), 0);
            Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, null);

            dvimage.setImageBitmap(b);
            dvimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(StudentdataViewing.this,ImageDisplayingActivity.class);
                    in.putExtra("image",Studentdatalist.imageArray.get(positionInt).toString());
                    startActivity(in);
                }
            });

        }
        else if(dataInt==2)
        {
            dvsubject.setText("Subject:   " + Studentdatalist.subjectArray.get(positionInt).toString());
            dvdesc.setText("Description:  "+Studentdatalist.descritionArray.get(positionInt).toString());

            byte[] data = Base64.decode(Studentdatalist.imageArray.get(positionInt).toString(), 0);
            Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, null);

            dvimage.setImageBitmap(b);
            dvimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(StudentdataViewing.this, ImageDisplayingActivity.class);
                    in.putExtra("image", Studentdatalist.imageArray.get(positionInt).toString());
                    startActivity(in);
                }
            });
        }
        else if(dataInt==3)
        {
            dvusername.setVisibility(View.VISIBLE);
            dvtime.setVisibility(View.VISIBLE);
            dvimage.setVisibility(View.INVISIBLE);

            dvusername.setText(Studentdatalist.usernameArray.get(positionInt).toString());
            dvtime.setText(Studentdatalist.timeArray.get(positionInt).toString());
            dvsubject.setText("Subject: " +Studentdatalist.subjectArray.get(positionInt).toString());
            dvdesc.setText("Description:"+Studentdatalist.descritionArray.get(positionInt).toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_studentdata_viewing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
