package com.tracking.ebridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FacultyHomeActivity extends Activity implements View.OnClickListener{

    Button timetableButton,questionsButton,notificationsButton,doubtsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);

        timetableButton= (Button) findViewById(R.id.timetable);
        questionsButton= (Button) findViewById(R.id.questionpapers);
        notificationsButton= (Button) findViewById(R.id.notification);
        doubtsButton= (Button) findViewById(R.id.viewdoubts);

        timetableButton.setOnClickListener(this);
        questionsButton.setOnClickListener(this);
        notificationsButton.setOnClickListener(this);
        doubtsButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_faculty_home, menu);
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
        if(id==R.id.timetable)
        {
            Intent in=new Intent(FacultyHomeActivity.this,AddTimeTable.class);
            startActivity(in);
        }
        else if(id==R.id.questionpapers)
        {
            Intent in=new Intent(FacultyHomeActivity.this,AddQuestionpapers.class);
            startActivity(in);
        }
        else if(id==R.id.notification)
        {
            Intent in=new Intent(FacultyHomeActivity.this,AddNotification.class);
            startActivity(in);
        }
        else if(id==R.id.viewdoubts)
        {
            Intent in=new Intent(FacultyHomeActivity.this,DoubtsList.class);
            startActivity(in);
        }
    }
}
