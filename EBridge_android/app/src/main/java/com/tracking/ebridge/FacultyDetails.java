package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FacultyDetails extends Activity implements View.OnClickListener{

    TextView nameTextView,mobileTextView,emailTextView,departTextView;
    Button deleteButtonn,editButton;

    JSONParser parser=new JSONParser();
    public String deleteUrl= Context.getUrl + "deletefaculty.php";

    String getusername,getname,getmobile,getemail,getdepartment;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);

        nameTextView= (TextView) findViewById(R.id.facultynamedetails);
        mobileTextView= (TextView) findViewById(R.id.facultymobiledetails);
        emailTextView= (TextView) findViewById(R.id.facultyemaildetails);
        departTextView= (TextView) findViewById(R.id.facultydepartmentdetails);

        deleteButtonn= (Button) findViewById(R.id.delete);
        editButton= (Button) findViewById(R.id.edit);

        getname=getIntent().getStringExtra("sendname");
        getmobile=getIntent().getStringExtra("sendmobile");
        getemail=getIntent().getStringExtra("sendemail");
        getdepartment=getIntent().getStringExtra("senddepartment");
        getusername=getIntent().getStringExtra("sendusername");

        nameTextView.setText("Name:            "+getname);
        mobileTextView.setText("Mobile number: "+getmobile);
        emailTextView.setText("Email address:  "+getemail);
        departTextView.setText("Department:    "+getdepartment);

        deleteButtonn.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faculty_details, menu);
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

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if (id==R.id.delete)
        {
            new DeleteFaculty().execute();
        }
        else if(id==R.id.edit)
        {
            Intent in=new Intent(FacultyDetails.this,EditFacultyActivity.class);
            in.putExtra("USERNAME",getusername);
            in.putExtra("NAME",getname);
            in.putExtra("MOBILE",getmobile);
            in.putExtra("EMAIL",getemail);
            in.putExtra("DEPART",getdepartment);
            startActivity(in);
        }
    }

    public class DeleteFaculty extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(FacultyDetails.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> args=new ArrayList<>();
            args.add(new BasicNameValuePair("username",getusername));

            JSONObject object=parser.makeHttpRequest(deleteUrl,"POST",args);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Intent in=new Intent(FacultyDetails.this,AdminHomeActivity.class);
            startActivity(in);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
