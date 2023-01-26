package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tracking.ebridge.models.Result;
import com.tracking.ebridge.models.Student;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegisterActivity extends Activity {

    EditText nameEdit,mobileEdit,mailEdit,departEdit,usernameEdit,passwordEdit;
    Button submitButton;
    TextView errorText;

    ProgressDialog progressDialog;

    String nameString,mobileString,mailString,departString,usernameString,passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        nameEdit= (EditText) findViewById(R.id.name);
        mobileEdit= (EditText) findViewById(R.id.mobile);
        mailEdit= (EditText) findViewById(R.id.mail);
        departEdit= (EditText) findViewById(R.id.depart);
        usernameEdit= (EditText) findViewById(R.id.username);
        passwordEdit= (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById(R.id.error);

        submitButton= (Button) findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameString=nameEdit.getText().toString();
                mobileString=mobileEdit.getText().toString();
                mailString=mailEdit.getText().toString();
                departString=departEdit.getText().toString();
                usernameString=usernameEdit.getText().toString();
                passwordString=passwordEdit.getText().toString();


                if (nameString.length()>0&&mobileString.length()>0&&mailString.length()>0&&departString.length()>0&&usernameString.length()>0&&passwordString.length()>0)
                {
                    ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected()) {
                        Student student = new Student(nameString, mobileString,
                                mailString, departString, usernameString, passwordString);

                        Call<Result> registerStudents= RetrofitClientInstance.getDataService().registerStudents(nameString, mobileString,
                                mailString, departString, usernameString, passwordString);
                        registerStudents.enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                clear();
                                errorText.setTextColor(Color.GREEN);
                                if(response.isSuccessful()){
                                    errorText.setText(response.body().getMessage());
                                }else
                                    errorText.setText(response.errorBody().toString());
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {
                                clear();
                                errorText.setText(t.getMessage());
                            }
                        });
                    }else{
                        clear();
                        Toast.makeText(StudentRegisterActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                        errorText.setText("Please check your internet connection");
                    }
                }
                else
                {
                    clear();
                    Toast.makeText(StudentRegisterActivity.this,"Enter all the fields ",Toast.LENGTH_SHORT).show();
                    errorText.setText("Enter all the fields");
                }

            }
        });
    }

    private void clear(){ errorText.setText("");}
}
