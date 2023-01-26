package com.tracking.ebridge;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tracking.ebridge.models.Result;
import com.tracking.ebridge.models.TimeTable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class AddTimeTable extends Activity implements View.OnClickListener {

    private static final String TAG = "AddTimeTable";
    EditText tsEdit, tdEdit;
    Button tcButton, tsubmitButton;
    ImageView tImageView;

    private static final int CAMERA_REQUEST = 1888,RESULT_LOAD_IMAGE=100;
    ProgressDialog progressDialog;
    JSONParser parser = new JSONParser();
    JSONArray response;

    public String timetableUrl = RetrofitClientInstance.BASE_URL+"addtimetable.php";

    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);

        tsEdit = (EditText) findViewById(R.id.timesubject);
        tdEdit = (EditText) findViewById(R.id.timedescription);
        tcButton = (Button) findViewById(R.id.timechooseimage);
        tImageView = (ImageView) findViewById(R.id.timeimageView);
        tsubmitButton = (Button) findViewById(R.id.timesubmit);

        tcButton.setOnClickListener(this);
        tsubmitButton.setOnClickListener(this);
    }

    public class Timetableadding extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddTimeTable.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String subjectString = tsEdit.getText().toString();
            String descString = tdEdit.getText().toString();
            String imageString = getStringImage(photo);

            try {
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("subject", subjectString));
                args.add(new BasicNameValuePair("description", descString));
                args.add(new BasicNameValuePair("image", imageString));

                JSONObject object = parser.makeHttpRequest(timetableUrl, "POST", args);

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(AddTimeTable.this, "Uploading successfully!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.timechooseimage) {
           final Dialog d = new Dialog(AddTimeTable.this);

            d.setContentView(R.layout.dialog);
            d.setTitle("Choose your option");
            Button camera = (Button) d.findViewById(R.id.camera);
            Button gallery = (Button) d.findViewById(R.id.gallery);

            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    d.dismiss();
                }
            });
            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/jpeg");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            100);

                    d.dismiss();
                }
            });
            d.show();

        } else if (id == R.id.timesubmit) {

            if (photo == null) {
                Toast.makeText(this, "Select a TimeTable", Toast.LENGTH_SHORT).show();
            } else {
                String subjectString = tsEdit.getText().toString();
                String descString = tdEdit.getText().toString();
                String imageString = getStringImage(photo);
                TimeTable timeTable;


                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    Call<Result> addQuestionPaper = RetrofitClientInstance.getDataService().addTimeTable(subjectString, descString, imageString);
                    addQuestionPaper.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess() == 1) {
                                    Toast.makeText(AddTimeTable.this, "Sent successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG, "onResponse: " + response.body().getMessage());
                                    Toast.makeText(AddTimeTable.this, "Could not send timetable, " + response.body().getMessage()
                                            , Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "onResponse: " + response.errorBody().toString());
                                Toast.makeText(AddTimeTable.this, "An error occured. "
                                        + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                            Toast.makeText(AddTimeTable.this, "A permanent error has occured", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AddTimeTable.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: "+data.getData() );
        try {
            photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tImageView.setImageBitmap(photo);
    }
}
