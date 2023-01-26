package com.tracking.ebridge;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tracking.ebridge.models.QuestionPaper;
import com.tracking.ebridge.models.Result;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class AddQuestionpapers extends Activity implements View.OnClickListener{

    EditText qsEdit,qdEdit;
    Button qcButton,qsubmitButton;
    ImageView qImageView;
    private static final int CAMERA_REQUEST = 1888;

    private static final String TAG = "AddQuestionpapers";

    ProgressDialog progressDialog;
    JSONParser parser=new JSONParser();
    JSONArray response;

    public String questionUrl=RetrofitClientInstance.BASE_URL+"addquestionspapers.php";

    Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questionpapers);

        qsEdit= (EditText) findViewById(R.id.questionsubject);
        qdEdit= (EditText) findViewById(R.id.questiondescription);
        qcButton= (Button) findViewById(R.id.questionchooseimage);
        qImageView= (ImageView) findViewById(R.id.questionimageView);
        qsubmitButton= (Button) findViewById(R.id.questionsubmit);

        qcButton.setOnClickListener(this);
        qsubmitButton.setOnClickListener(this);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.questionchooseimage)
        {
            final Dialog d = new Dialog(AddQuestionpapers.this);

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
        }
        else if(id==R.id.questionsubmit)
        {
            if(photo == null) {
                Toast.makeText(this, "Select question paper to add", Toast.LENGTH_SHORT).show();
            } else {
                String subjectString = qsEdit.getText().toString();
                String descString = qdEdit.getText().toString();
                String imageString = getStringImage(photo);
                QuestionPaper questionPaper;


                Call<Result> addQuestionPaper = RetrofitClientInstance.getDataService().addQuestionPaper(subjectString, descString, imageString);
                addQuestionPaper.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess() == 1) {
                                Toast.makeText(AddQuestionpapers.this, "Sent successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                                Toast.makeText(AddQuestionpapers.this, "Could not send qp, " + response.body().getMessage()
                                        , Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "onResponse: " + response.errorBody().toString());
                            Toast.makeText(AddQuestionpapers.this, "An error occured. "
                                    + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Toast.makeText(AddQuestionpapers.this, "A permanent error has occured", Toast.LENGTH_SHORT).show();
                    }
                });
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
        qImageView.setImageBitmap(photo);
    }
}
