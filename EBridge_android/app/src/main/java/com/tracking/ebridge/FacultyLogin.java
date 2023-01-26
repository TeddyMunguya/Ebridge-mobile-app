package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracking.ebridge.models.Credential;
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


public class FacultyLogin extends Activity {

    EditText fusrEdit,fpswEdit;
    Button floginButton;
    String userString,passwordString;

    ProgressDialog progressDialog;
    JSONParser parser=new JSONParser();
    JSONArray response;
    public String addUrl=RetrofitClientInstance.BASE_URL+"/facultylogin.php";
    String responseUser,responsePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        fusrEdit= (EditText) findViewById(R.id.fusername);
        fpswEdit= (EditText) findViewById(R.id.fpassword);

        floginButton= (Button) findViewById(R.id.flogin);
        floginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userString=fusrEdit.getText().toString();
                passwordString=fpswEdit.getText().toString();
                ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()==true ) {
                    Credential credential = new Credential();
                    credential.setPassword(passwordString);
                    credential.setUsername(userString);
                    Call<Result> login = RetrofitClientInstance.getDataService().facultyLogin(userString, passwordString);
                    login.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if(response.isSuccessful()){
                                if(response.body().getSuccess() == 0)
                                    Toast.makeText(FacultyLogin.this,
                                            "Login Failed: "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                                else{
                                    com.tracking.ebridge.Context.setUsername(userString);
                                    SharedPreferences share= PreferenceManager.getDefaultSharedPreferences(FacultyLogin.this);
                                    SharedPreferences.Editor editor=share.edit();
                                    editor.putString("FUSERNAME",responseUser);
                                    editor.apply();
                                    Intent in = new Intent(FacultyLogin.this, FacultyHomeActivity.class);
                                    startActivity(in);
                                }
                            } else {
                                Toast.makeText(FacultyLogin.this,
                                        "Something happened. Please try again"+response.errorBody(), Toast.LENGTH_SHORT).show();
                                System.out.println("FACULTYLOGINACTIVITY: "+ response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(FacultyLogin.this,
                                    "Something happened. Please try again later", Toast.LENGTH_SHORT).show();
                            System.out.println("FACULTYLOGINACTIVITY: "+ t);

                        }
                    });

                }else{
                    Toast.makeText(FacultyLogin.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public class Login extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("username", userString));

                JSONObject object = parser.makeHttpRequest(addUrl, "POST", args);

                response=object.getJSONArray("facultylogin");

                for (int i=0;i<response.length();i++)
                {
                    JSONObject c=response.getJSONObject(i);
                    responseUser=c.getString("username");
                    responsePass=c.getString("password");

                }

            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(responseUser == null)
                Toast.makeText(FacultyLogin.this, "An error occured", Toast.LENGTH_SHORT).show();

            else
                if(responseUser.equals(userString)&&responsePass.equals(passwordString)) {
                Intent in = new Intent(FacultyLogin.this, FacultyHomeActivity.class);
                startActivity(in);

                SharedPreferences share= PreferenceManager.getDefaultSharedPreferences(FacultyLogin.this);
                SharedPreferences.Editor editor=share.edit();
                editor.putString("FUSERNAME",responseUser);
                editor.commit();
            }
            else
            {
                Toast.makeText(FacultyLogin.this,"Invalid username or password!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faculty_login, menu);
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
