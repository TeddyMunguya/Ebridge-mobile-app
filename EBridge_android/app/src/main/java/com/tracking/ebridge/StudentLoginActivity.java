package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracking.ebridge.models.Result;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLoginActivity extends Activity implements View.OnClickListener{

    EditText suserEdit,spasswordEdit;
    Button sloginButton,sregisterButton;

    ProgressDialog progressDialog;

    String userString,passString,resuserString,respassString;
    JSONParser parser=new JSONParser();
    JSONArray response;
    private final static String student_loginurl=RetrofitClientInstance.BASE_URL+"/studentlogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        suserEdit= (EditText) findViewById(R.id.susername);
        spasswordEdit= (EditText) findViewById(R.id.spassword);

        sloginButton= (Button) findViewById(R.id.login);
        sregisterButton= (Button) findViewById(R.id.register);

        sloginButton.setOnClickListener(this);
        sregisterButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if (id==R.id.login)
        {
            userString=suserEdit.getText().toString();
            passString=spasswordEdit.getText().toString();

            ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()==true ) {
                if (userString.length() > 0 && passString.length() > 0) {
                    Call<Result> login = RetrofitClientInstance.getDataService().studentLogin(userString, passString);
                    login.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if(response.isSuccessful()){
                                if(response.body().getSuccess() == 0)
                                    Toast.makeText(StudentLoginActivity.this,
                                            "Login Failed: "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                                else{
                                    com.tracking.ebridge.Context.setUsername(userString);
                                    Intent in=new Intent(StudentLoginActivity.this,StudentHomeActivity.class);
                                    startActivity(in);
                                }
                            } else {
                                Toast.makeText(StudentLoginActivity.this,
                                        "Something happened. Please try again"+response.errorBody(), Toast.LENGTH_LONG).show();
                                System.out.println("STUDENTLOGINACTIVITY: "+ response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(StudentLoginActivity.this,
                                    "Something happened. Please try again later", Toast.LENGTH_SHORT).show();
                            System.out.println("STUDENTLOGINACTIVITY: "+ t);

                        }
                    });
                } else {
                    Toast.makeText(StudentLoginActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(StudentLoginActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
            }
        }
        else if (id==R.id.register)
        {
            Intent in=new Intent(StudentLoginActivity.this,StudentRegisterActivity.class);
            startActivity(in);
        }
    }
}
