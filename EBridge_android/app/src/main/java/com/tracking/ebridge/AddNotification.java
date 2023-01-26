package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracking.ebridge.models.Notification;
import com.tracking.ebridge.models.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNotification extends Activity {

    private static final String TAG = "AddNotification";
    EditText nsEdit, ndEdit;
    Button nsubmitButton;
    String subjectString, descriptionString;

    ProgressDialog progressDialog;
    JSONParser parser = new JSONParser();
    JSONArray response;
    public String addUrl = RetrofitClientInstance.BASE_URL+"addnotification.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        nsEdit = (EditText) findViewById(R.id.notificationsubject);
        ndEdit = (EditText) findViewById(R.id.notificationdesc);

        nsubmitButton = (Button) findViewById(R.id.notificationsubmit);
        nsubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subjectString = nsEdit.getText().toString();
                descriptionString = ndEdit.getText().toString();

                Notification notification = new Notification();
                notification.setSubject(subjectString);
                notification.setDescription(descriptionString);
                notification.setUsername(Context.getUsername());

                Call<Result> sendDoubt = RetrofitClientInstance.getDataService().addNotification(subjectString, descriptionString, Context.getUsername());
                sendDoubt.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getSuccess() == 1){
                                Toast.makeText(AddNotification.this, "Sent successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d(TAG, "onResponse: "+response.body().getMessage());
                                Toast.makeText(AddNotification.this, "Could not send notification, "+response.body().getMessage()
                                        , Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Log.d(TAG, "onResponse: "+response.errorBody().toString());
                            Toast.makeText(AddNotification.this, "An error occured. "
                                    +response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Toast.makeText(AddNotification.this, "A permanent error has occured", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public class Notificationadding extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddNotification.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            SharedPreferences share= PreferenceManager.getDefaultSharedPreferences(AddNotification.this);
            String userString=share.getString("FUSERNAME",null);
            try {
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("subject", subjectString));
                args.add(new BasicNameValuePair("description", descriptionString));
                args.add(new BasicNameValuePair("username", userString));

                JSONObject object = parser.makeHttpRequest(addUrl, "POST", args);


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(AddNotification.this, "Notification added successfully", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
