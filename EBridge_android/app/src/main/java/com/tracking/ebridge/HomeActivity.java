package com.tracking.ebridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends Activity implements View.OnClickListener{

    Button adminButton,facultyButton,studentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adminButton= (Button) findViewById(R.id.admin);
        facultyButton= (Button) findViewById(R.id.faculty);
        studentButton= (Button) findViewById(R.id.student);

        adminButton.setOnClickListener(this);
        facultyButton.setOnClickListener(this);
        studentButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

        if(id==R.id.admin)
        {
            Intent in=new Intent(HomeActivity.this,AdminLogin.class);
            startActivity(in);
        }
        else if(id==R.id.faculty)
        {
            Intent in=new Intent(HomeActivity.this,FacultyLogin.class);
            startActivity(in);
        }
        else if(id==R.id.student)
        {
            Intent in=new Intent(HomeActivity.this,StudentLoginActivity.class);
            startActivity(in);
        }
    }
}
