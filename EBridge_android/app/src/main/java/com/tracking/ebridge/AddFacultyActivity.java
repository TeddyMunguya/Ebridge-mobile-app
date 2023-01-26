package com.tracking.ebridge;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tracking.ebridge.models.Faculty;
import com.tracking.ebridge.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddFacultyActivity extends Activity {

    EditText nameEdit, mobileEdit, emailEdit, departEdit, usernameEdit, passwordEdit;
    Button addButton;

    String nameString,mobileString,emailString,departString,usernameString,passwordString;
    JSONParser parser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        nameEdit = (EditText) findViewById(R.id.facultyname);
        mobileEdit = (EditText) findViewById(R.id.facultymobile);
        emailEdit = (EditText) findViewById(R.id.facultyemail);
        departEdit = (EditText) findViewById(R.id.facultydepartment);
        usernameEdit = (EditText) findViewById(R.id.facultyusername);
        passwordEdit = (EditText) findViewById(R.id.facultypassword);

        addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameString=nameEdit.getText().toString();
                mobileString=mobileEdit.getText().toString();
                emailString=emailEdit.getText().toString();
                departString=departEdit.getText().toString();
                usernameString=usernameEdit.getText().toString();
                passwordString=passwordEdit.getText().toString();
                ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
                Faculty faculty = new Faculty(nameString,mobileString,emailString,
                        departString,usernameString,passwordString);
                if(networkInfo != null && networkInfo.isConnected()==true ) {
                    Call<Result> registerFaculty = RetrofitClientInstance.getDataService().registerFaculty(nameString,mobileString,emailString,
                            departString,usernameString,passwordString);
                    registerFaculty.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Log.e("ADDFACULTY", response.body().getMessage());
                            if(response.isSuccessful())
                                Toast.makeText(AddFacultyActivity.this,
                                        "Registration successful", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(AddFacultyActivity.this, "Error: " +
                                        response.errorBody().toString(), Toast.LENGTH_LONG).show();
                                System.out.println(response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(AddFacultyActivity.this,
                                    "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
                            t.printStackTrace();
                        }
                    });

                }else{
                    Toast.makeText(AddFacultyActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_faculty, menu);
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
