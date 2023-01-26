package com.tracking.ebridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StudentHomeActivity extends Activity implements View.OnClickListener{

    Button viewtimeButton,viewquestionButton,viewnotificationButton,senddoubtButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        viewtimeButton= (Button) findViewById(R.id.viewtimetable);
        viewquestionButton= (Button) findViewById(R.id.viewquestions);
        viewnotificationButton= (Button) findViewById(R.id.viewnotifications);
        senddoubtButton= (Button) findViewById(R.id.senddoubts);

        viewtimeButton.setOnClickListener(this);
        viewquestionButton.setOnClickListener(this);
        viewnotificationButton.setOnClickListener(this);
        senddoubtButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
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
        if(id==R.id.viewtimetable)
        {
            Intent in=new Intent(StudentHomeActivity.this,Studentdatalist.class);
            in.putExtra("selection",1);
            startActivity(in);
        }
        else if(id==R.id.viewquestions)
        {
            Intent in=new Intent(StudentHomeActivity.this,Studentdatalist.class);
            in.putExtra("selection",2);
            startActivity(in);
        }
        else if(id==R.id.viewnotifications)
        {
            Intent in=new Intent(StudentHomeActivity.this,Studentdatalist.class);
            in.putExtra("selection",3);
            startActivity(in);
        }
        else if(id==R.id.senddoubts)
        {
            Intent in=new Intent(StudentHomeActivity.this,DoubtSending.class);
            startActivity(in);
        }
    }
}
