package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class AdminHomeActivity extends Activity {

    Button addfacultyButton;
    ListView fListView;

    ProgressDialog progressDialog;
    JSONParser parser = new JSONParser();
    JSONArray response;
    public String getUrl = Context.getUrl + "facultyregister.php";

    ArrayList<String> facultynameArray = new ArrayList<>();
    ArrayList<String> facultymobileArray = new ArrayList<>();
    ArrayList<String> facultyemailArray = new ArrayList<>();
    ArrayList<String> facultydepartArray = new ArrayList<>();
    ArrayList<String> facultyusernameArrray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addfacultyButton = (Button) findViewById(R.id.addfaculty);
        fListView = (ListView) findViewById(R.id.facultylist);

        addfacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AdminHomeActivity.this, AddFacultyActivity.class);
                startActivity(in);
            }
        });

        fListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(AdminHomeActivity.this, FacultyDetails.class);
                in.putExtra("sendname", facultynameArray.get(position).toString());
                in.putExtra("sendmobile", facultymobileArray.get(position).toString());
                in.putExtra("sendemail", facultyemailArray.get(position).toString());
                in.putExtra("senddepartment", facultydepartArray.get(position).toString());
                in.putExtra("sendusername", facultyusernameArrray.get(position).toString());
                startActivity(in);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        facultynameArray.clear();
        facultymobileArray.clear();
        facultyemailArray.clear();
        facultydepartArray.clear();
        facultyusernameArrray.clear();

//       new Getfaculty().execute();
    }

    public class Getfaculty extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AdminHomeActivity.this);
            progressDialog.setMessage("Please wait....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                List<NameValuePair> args = new ArrayList<>();

                JSONObject object = parser.makeHttpRequest(getUrl, "GET", args);
                response = object.getJSONArray("facultylist");

                for (int i = 0; i < response.length(); i++) {
                    JSONObject c = response.getJSONObject(i);

                    String fname = c.getString("name");
                    String fmobile = c.getString("phone");
                    String femail = c.getString("mail");
                    String fdepartment = c.getString("department");
                    String fusername = c.getString("username");


                    facultynameArray.add(fname);
                    facultymobileArray.add(fmobile);
                    facultyemailArray.add(femail);
                    facultydepartArray.add(fdepartment);
                    facultyusernameArrray.add(fusername);

                }

            } catch (Exception e) {

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayAdapter<String> facultyAdapter = new ArrayAdapter<String>(AdminHomeActivity.this, android.R.layout.simple_list_item_1, facultynameArray);
            fListView.setAdapter(facultyAdapter);
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
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
