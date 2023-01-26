package com.tracking.ebridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminLogin extends Activity {

    EditText usrEdit,pswEdit;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        usrEdit= (EditText) findViewById(R.id.adminusername);
        pswEdit= (EditText) findViewById(R.id.adminpassword);

        loginButton= (Button) findViewById(R.id.adminlogin);

        loginButton.setOnClickListener(v -> {
            if (usrEdit.getText().toString().equals("admin")&&pswEdit.getText().toString().equals("admin")) {
                Intent in = new Intent(AdminLogin.this,AdminHomeActivity.class);
                startActivity(in);
            }
            else
            {
                Toast.makeText(AdminLogin.this,"Invalid username or password!",Toast.LENGTH_SHORT).show();
            }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_login, menu);
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
