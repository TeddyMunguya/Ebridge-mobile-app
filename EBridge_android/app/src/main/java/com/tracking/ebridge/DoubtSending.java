package com.tracking.ebridge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracking.ebridge.models.Doubt;
import com.tracking.ebridge.models.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoubtSending extends Activity {
    private static final String TAG = "DoubtSending";
    EditText dsubEdit,ddescEdit;
    Button dsubmitButton;

    String dsubString,ddescString;

    ProgressDialog progressDialog;
    JSONParser parser = new JSONParser();
    JSONArray response;
    public String adddoubtUrl = RetrofitClientInstance.BASE_URL+"adddoubt.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_sending);

        dsubEdit= (EditText) findViewById(R.id.doubtsubject);
        ddescEdit= (EditText) findViewById(R.id.doubtdescription);
        dsubmitButton= (Button) findViewById(R.id.doubtsending);

        dsubmitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                dsubString=dsubEdit.getText().toString();
                ddescString=ddescEdit.getText().toString();
                Doubt doubt = new Doubt();
                doubt.setSubject(dsubString);
                doubt.setDescription(ddescString);
                doubt.setTime(LocalDateTime.now().toString());
                doubt.setUsername(Context.getUsername());

                Call<Result> sendDoubt = RetrofitClientInstance.getDataService().sendDoubt(dsubString, ddescString, Context.getUsername());
                sendDoubt.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getSuccess() == 1){
                                Toast.makeText(DoubtSending.this, "Sent successfully", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getSuccess() == 2) {
                                Toast.makeText(DoubtSending.this, "Doubt already exists", Toast.LENGTH_LONG).show();
                            } else {
                                Log.d(TAG, "onResponse: "+response.body().getMessage());
                                Toast.makeText(DoubtSending.this, "Could not send doubt, "+response.body().getMessage()
                                        , Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Log.d(TAG, "onResponse: "+response.errorBody().toString());
                            Toast.makeText(DoubtSending.this, "An error occured. "
                                    +response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Toast.makeText(DoubtSending.this, "A permanent error has occured", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public class Senddoubt extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DoubtSending.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("subject", dsubString));
                args.add(new BasicNameValuePair("description", ddescString));

                JSONObject object = parser.makeHttpRequest(adddoubtUrl, "POST", args);


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(DoubtSending.this, "Notification added successfully", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

}
