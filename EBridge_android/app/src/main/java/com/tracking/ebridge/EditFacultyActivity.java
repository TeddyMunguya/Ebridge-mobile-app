package com.tracking.ebridge;

import static com.tracking.ebridge.R.id.edit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditFacultyActivity extends Activity {

    EditText fnEdit,fmEdit,feEdit,fdEdit;
    Button editButton;
    JSONParser parser=new JSONParser();

    public String editUrl= Context.getUrl + "editfaculty.php";

    String fnameString,fmobileString,femailString,fdepartString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_faculty);

        fnEdit= (EditText) findViewById(R.id.facultyname);
        fmEdit= (EditText) findViewById(R.id.facultymobile);
        feEdit= (EditText) findViewById(R.id.facultyemail);
        fdEdit= (EditText) findViewById(R.id.facultydepartment);

        String name=getIntent().getStringExtra("NAME");
        String mobile=getIntent().getStringExtra("MOBILE");
        String mail=getIntent().getStringExtra("EMAIL");
        String depart=getIntent().getStringExtra("DEPART");

        fnEdit.setText(name);
        fmEdit.setText(mobile);
        feEdit.setText(mail);
        fdEdit.setText(depart);

        editButton= (Button) findViewById(edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fnameString=fnEdit.getText().toString();
                fmobileString=fmEdit.getText().toString();
                femailString=feEdit.getText().toString();
                fdepartString=fdEdit.getText().toString();

                if (fnameString.length()>0&&fmobileString.length()>0&&femailString.length()>0&&fdepartString.length()>0)
                {
                    new FacultyEdit().execute();
                }
                else
                {
                    Toast.makeText(EditFacultyActivity.this,"Fields not be empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class FacultyEdit extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> args=new ArrayList<>();

            String username=getIntent().getStringExtra("USERNAME");

            args.add(new BasicNameValuePair("username",username));
            args.add(new BasicNameValuePair("name",fnameString));
            args.add(new BasicNameValuePair("mobile",fmobileString));
            args.add(new BasicNameValuePair("mail",femailString));
            args.add(new BasicNameValuePair("depart",fdepartString));

            JSONObject object=parser.makeHttpRequest(editUrl,"POST",args);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
