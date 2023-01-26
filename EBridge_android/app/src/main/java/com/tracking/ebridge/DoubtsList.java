package com.tracking.ebridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tracking.ebridge.models.Doubt;
import com.tracking.ebridge.models.Result;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoubtsList extends Activity {

    private static final String TAG = "DoubtsList";
    ListView fdoubtListView;

    JSONParser parser = new JSONParser();
    JSONArray response;
    public String getdoubtUrl = RetrofitClientInstance.BASE_URL+"getdoubts.php";

    ArrayList<String> subjectArray = new ArrayList<>();
    ArrayList<String> descriptionArray = new ArrayList<>();
    ArrayList<String> timeArray = new ArrayList<>();
    ArrayList<String> usernameArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts_list);

        fdoubtListView = (ListView) findViewById(R.id.fdoubtslist);

        Call<Result> getDoubts = RetrofitClientInstance.getDataService().getDoubts();
        getDoubts.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()) {
                    if(response.body().getSuccess() == 1){
                        for(Doubt timeTable: response.body().getDoubts()) {
                            subjectArray.add(timeTable.getSubject());
                            descriptionArray.add(timeTable.getDescription());
                            timeArray.add(timeTable.getTime());
                            usernameArray.add(timeTable.getUsername());

                        }
                        fdoubtListView.setAdapter(new MyDoubts());
                    } else {
                        Toast.makeText(DoubtsList.this,
                                "An error occured: "+response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }else{
                    Log.d(TAG, "onResponse: "+response.errorBody().toString());
                    Toast.makeText(DoubtsList.this,
                            "An error occured: "+response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
                Toast.makeText(DoubtsList.this, "An error occured", Toast.LENGTH_LONG).show();
            }
        });

        fdoubtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(DoubtsList.this, Viewdoubt.class);
                in.putExtra("description", descriptionArray.get(position).toString());
                startActivity(in);
            }
        });
    }

    public class MyDoubts extends BaseAdapter
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
            TextView user = convertView.findViewById(R.id.notuser);

            sub.setText(subjectArray.get(position).toString());
            time.setText(timeArray.get(position).toString());
            user.setText(usernameArray.get(position).toString());

            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doubts_list, menu);
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
