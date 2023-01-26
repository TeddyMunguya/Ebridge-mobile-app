package com.tracking.ebridge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tracking.ebridge.models.Notification;
import com.tracking.ebridge.models.QuestionPaper;
import com.tracking.ebridge.models.Result;
import com.tracking.ebridge.models.TimeTable;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Studentdatalist extends Activity {
    private static final String TAG = "Studentdatalist";

    ListView dataListView;

    public static ArrayList<String> subjectArray = new ArrayList<>();
    public static ArrayList<String> descritionArray = new ArrayList<>();
    public static ArrayList<String> usernameArray = new ArrayList<>();
    public static ArrayList<String> timeArray = new ArrayList<>();
    public static ArrayList<String> imageArray = new ArrayList<>();



    ProgressDialog progressDialog;
    JSONParser parser = new JSONParser();
    JSONArray response;
    public String gettimetableUrl = RetrofitClientInstance.BASE_URL+"gettimetable.php";
    public String getquestionsUrl = RetrofitClientInstance.BASE_URL+"getquestionpapers.php";
    public String getnotificationUrl = RetrofitClientInstance.BASE_URL+"getnotification.php";

    int getInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdatalist);

        dataListView = (ListView) findViewById(R.id.datalist);

        getInt = getIntent().getIntExtra("selection", 0);


        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(Studentdatalist.this, StudentdataViewing.class);
                in.putExtra("viewdata", getInt);
                in.putExtra("position", position);
                startActivity(in);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        subjectArray.clear();
        descritionArray.clear();

        imageArray.clear();

        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            if (getInt == 1) {
                Call<Result> getTT = RetrofitClientInstance.getDataService().getTimeTable();
                getTT.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getSuccess() == 1){
                                for(TimeTable timeTable: response.body().getTimeTableList()) {
                                    subjectArray.add(timeTable.getSubject());
                                    descritionArray.add(timeTable.getDescription());
                                    imageArray.add(timeTable.getImage());
                                }
                                ArrayAdapter<String> facultyAdapter = new ArrayAdapter<String>(Studentdatalist.this, android.R.layout.simple_list_item_1, subjectArray);
                                dataListView.setAdapter(facultyAdapter);
                            } else {
                                Toast.makeText(Studentdatalist.this,
                                        "An error occured: "+response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }else{
                            Log.d(TAG, "onResponse: "+response.errorBody().toString());
                            Toast.makeText(Studentdatalist.this,
                                    "An error occured: "+response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, "onFailure: ",t );
                        Toast.makeText(Studentdatalist.this, "An error occured", Toast.LENGTH_LONG).show();
                    }
                });
            } else if (getInt == 2) {
                Call<Result> getTT = RetrofitClientInstance.getDataService().getQuestionPapers();
                getTT.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getSuccess() == 1){
                                for(QuestionPaper timeTable: response.body().getQuestionPapers()) {
                                    subjectArray.add(timeTable.getSubject());
                                    descritionArray.add(timeTable.getDescription());
                                    imageArray.add(timeTable.getImage());
                                }
                                ArrayAdapter<String> facultyAdapter = new ArrayAdapter<String>(Studentdatalist.this, android.R.layout.simple_list_item_1, subjectArray);
                                dataListView.setAdapter(facultyAdapter);
                            } else {
                                Toast.makeText(Studentdatalist.this,
                                        "An error occured: "+response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }else{
                            Log.d(TAG, "onResponse: "+response.errorBody().toString());
                            Toast.makeText(Studentdatalist.this,
                                    "An error occured: "+response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, "onFailure: ",t );
                        Toast.makeText(Studentdatalist.this, "An error occured", Toast.LENGTH_LONG).show();
                    }
                });
            } else if (getInt == 3) {
                Call<Result> getTT = RetrofitClientInstance.getDataService().getNotifications();
                getTT.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getSuccess() == 1){
                                for(Notification timeTable: response.body().getNotifications()) {
                                    subjectArray.add(timeTable.getSubject());
                                    descritionArray.add(timeTable.getDescription());
                                    usernameArray.add(timeTable.getUsername());
                                    timeArray.add(timeTable.getTime());
                                }
                                dataListView.setAdapter(new MyNotifications());
                            } else {
                                Toast.makeText(Studentdatalist.this,
                                        "An error occured: "+response.body().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }else{
                            Log.d(TAG, "onResponse: "+response.errorBody().toString());
                            Toast.makeText(Studentdatalist.this,
                                    "An error occured: "+response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e(TAG, "onFailure: ",t );
                        Toast.makeText(Studentdatalist.this, "An error occured", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }else{
            Toast.makeText(Studentdatalist.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyNotifications extends BaseAdapter
    {
        @Override
        public int getCount() {
            return subjectArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.notificaton,null);
            TextView sub= (TextView) convertView.findViewById(R.id.notsub);
            TextView time= (TextView) convertView.findViewById(R.id.nottime);

            sub.setText(subjectArray.get(position).toString());
            time.setText(timeArray.get(position).toString());

            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_studentdatalist, menu);
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
